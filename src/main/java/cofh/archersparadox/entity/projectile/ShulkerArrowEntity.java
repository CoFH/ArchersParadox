package cofh.archersparadox.entity.projectile;

import cofh.lib.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

import static cofh.archersparadox.init.APReferences.SHULKER_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.SHULKER_ARROW_ITEM;

public class ShulkerArrowEntity extends AbstractArrowEntity {

    private static final DataParameter<Integer> TARGET = EntityDataManager.defineId(ShulkerArrowEntity.class, DataSerializers.INT);
    private static final int ID_NO_TARGET = -1;

    private static final float MAX_VELOCITY = 3.0F;
    private static final double SEEK_DISTANCE = 5.0;
    private static final double SEEK_FACTOR = 0.2;
    private static final double SEEK_ANGLE = Math.PI / 6.0;
    private static final double SEEK_THRESHOLD = 0.5;

    public static int effectDuration = 100;

    public ShulkerArrowEntity(EntityType<? extends ShulkerArrowEntity> entityIn, World worldIn) {

        super(entityIn, worldIn);
    }

    public ShulkerArrowEntity(World worldIn, LivingEntity shooter) {

        super(SHULKER_ARROW_ENTITY, shooter, worldIn);
    }

    public ShulkerArrowEntity(World worldIn, double x, double y, double z) {

        super(SHULKER_ARROW_ENTITY, x, y, z, worldIn);
    }

    @Override
    protected void defineSynchedData() {

        super.defineSynchedData();
        this.getEntityData().define(TARGET, ID_NO_TARGET);
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(SHULKER_ARROW_ITEM);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult raytraceResultIn) {

        super.onHitEntity(raytraceResultIn);

        Entity entity = raytraceResultIn.getEntity();
        if (!entity.isInvulnerable() && entity instanceof LivingEntity && effectDuration > 0) {
            LivingEntity living = (LivingEntity) entity;
            living.addEffect(new EffectInstance(Effects.LEVITATION, effectDuration));
        }
    }

    @Override
    public void setSecondsOnFire(int seconds) {

    }

    @Override
    public void tick() {

        if (!inGround) {
            if (Utils.isServerWorld(level)) {
                updateTarget();
            }
            Entity target = getTarget();
            if (target != null) {
                Vector3d targetVec = getVectorToTarget(target).scale(SEEK_FACTOR);
                Vector3d courseVec = getDeltaMovement();

                double courseLen = courseVec.length();
                double targetLen = targetVec.length();
                double totalLen = Math.sqrt(courseLen * courseLen + targetLen * targetLen);
                double dotProduct = courseVec.dot(targetVec) / (courseLen * targetLen);

                if (dotProduct > SEEK_THRESHOLD) {
                    Vector3d newMotion = (courseVec.scale(courseLen / totalLen).add(targetVec.scale(targetLen / totalLen))).normalize().scale(MAX_VELOCITY);
                    this.setDeltaMovement(newMotion.x, newMotion.y + 0.05F, newMotion.z);
                } else if (Utils.isServerWorld(level)) {
                    setTarget(null);
                }
                if (Utils.isClientWorld(level)) {
                    Vector3d vec3d = this.getDeltaMovement().scale(0.25D);
                    double d1 = vec3d.x;
                    double d2 = vec3d.y;
                    double d0 = vec3d.z;
                    this.level.addParticle(ParticleTypes.END_ROD, this.getX() + d1, this.getY() + d2, this.getZ() + d0, -d1, -d2 + 0.2D, -d0);
                }
            }
        }
        super.tick();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // region HELPERS
    private void updateTarget() {

        if (isInWater() || isInLava()) {
            setTarget(null);
            return;
        }
        Entity target = getTarget();
        if (target != null && !target.isAlive()) {
            setTarget(target = null);
        }
        if (target == null) {
            AxisAlignedBB positionBB = new AxisAlignedBB(getX(), getY(), getZ(), getX(), getY(), getZ());
            AxisAlignedBB targetBB = positionBB;

            Vector3d courseVec = getDeltaMovement().scale(SEEK_DISTANCE).yRot((float) SEEK_ANGLE);
            targetBB = targetBB.minmax(positionBB.move(courseVec));

            courseVec = getDeltaMovement().scale(SEEK_DISTANCE).yRot((float) -SEEK_ANGLE);
            targetBB = targetBB.minmax(positionBB.move(courseVec));
            targetBB = targetBB.inflate(0, SEEK_DISTANCE * 0.5, 0);

            double closestDot = -1.0;
            Entity closestTarget = null;

            for (LivingEntity living : this.level.getEntitiesOfClass(LivingEntity.class, targetBB)) {
                if (living instanceof PlayerEntity) {
                    continue;
                }
                Vector3d motionVec = getDeltaMovement().normalize();
                Vector3d targetVec = getVectorToTarget(living).normalize();

                double dot = motionVec.dot(targetVec);
                if (dot > Math.max(closestDot, SEEK_THRESHOLD)) {
                    closestDot = dot;
                    closestTarget = living;
                }
            }
            if (closestTarget != null) {
                setTarget(closestTarget);
            }
        }
    }

    private Vector3d getVectorToTarget(Entity target) {

        return new Vector3d(target.getX() - this.getX(), (target.getY() + (double) target.getEyeHeight()) - this.getY(), target.getZ() - this.getZ());
    }

    @Nullable
    private Entity getTarget() {

        return level.getEntity(entityData.get(TARGET));
    }

    private void setTarget(@Nullable Entity e) {

        entityData.set(TARGET, e == null ? ID_NO_TARGET : e.getId());
    }
    // endregion
}
