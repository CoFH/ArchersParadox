package cofh.archersparadox.entity.projectile;

import cofh.core.config.IBaseConfig;
import cofh.lib.item.ArrowItemCoFH;
import cofh.lib.util.Utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static cofh.archersparadox.init.APEntities.SHULKER_ARROW;
import static cofh.archersparadox.init.APItems.SHULKER_ARROW_ITEM;

public class ShulkerArrow extends AbstractArrow {

    private static final EntityDataAccessor<Integer> TARGET = SynchedEntityData.defineId(ShulkerArrow.class, EntityDataSerializers.INT);
    private static final int ID_NO_TARGET = -1;

    private static final float MAX_VELOCITY = 3.0F;
    private static final double SEEK_DISTANCE = 5.0;
    private static final double SEEK_FACTOR = 0.2;
    private static final double SEEK_ANGLE = Math.PI / 6.0;
    private static final double SEEK_THRESHOLD = 0.5;

    public static float defaultDamage = 2.5F;
    public static int effectDuration = 100;

    public ShulkerArrow(EntityType<? extends ShulkerArrow> entityIn, Level worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
    }

    public ShulkerArrow(Level worldIn, LivingEntity shooter) {

        super(SHULKER_ARROW.get(), shooter, worldIn);
        this.baseDamage = defaultDamage;
    }

    public ShulkerArrow(Level worldIn, double x, double y, double z) {

        super(SHULKER_ARROW.get(), x, y, z, worldIn);
        this.baseDamage = defaultDamage;
    }

    @Override
    protected void defineSynchedData() {

        super.defineSynchedData();
        this.getEntityData().define(TARGET, ID_NO_TARGET);
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(SHULKER_ARROW_ITEM.get());
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {

        if (effectDuration > 0) {
            living.addEffect(new MobEffectInstance(MobEffects.LEVITATION, effectDuration));
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
                Vec3 targetVec = getVectorToTarget(target).scale(SEEK_FACTOR);
                Vec3 courseVec = getDeltaMovement();

                double courseLen = courseVec.length();
                double targetLen = targetVec.length();
                double totalLen = Math.sqrt(courseLen * courseLen + targetLen * targetLen);
                double dotProduct = courseVec.dot(targetVec) / (courseLen * targetLen);

                if (dotProduct > SEEK_THRESHOLD) {
                    Vec3 newMotion = (courseVec.scale(courseLen / totalLen).add(targetVec.scale(targetLen / totalLen))).normalize().scale(MAX_VELOCITY);
                    this.setDeltaMovement(newMotion.x, newMotion.y + 0.05F, newMotion.z);
                } else if (Utils.isServerWorld(level)) {
                    setTarget(null);
                }
                if (Utils.isClientWorld(level)) {
                    Vec3 vec3d = this.getDeltaMovement().scale(0.25D);
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {

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
            AABB positionBB = new AABB(getX(), getY(), getZ(), getX(), getY(), getZ());
            AABB targetBB = positionBB;

            Vec3 courseVec = getDeltaMovement().scale(SEEK_DISTANCE).yRot((float) SEEK_ANGLE);
            targetBB = targetBB.minmax(positionBB.move(courseVec));

            courseVec = getDeltaMovement().scale(SEEK_DISTANCE).yRot((float) -SEEK_ANGLE);
            targetBB = targetBB.minmax(positionBB.move(courseVec));
            targetBB = targetBB.inflate(0, SEEK_DISTANCE * 0.5, 0);

            double closestDot = -1.0;
            Entity closestTarget = null;

            for (LivingEntity living : this.level.getEntitiesOfClass(LivingEntity.class, targetBB)) {
                if (living instanceof Player) {
                    continue;
                }
                Vec3 motionVec = getDeltaMovement().normalize();
                Vec3 targetVec = getVectorToTarget(living).normalize();

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

    private Vec3 getVectorToTarget(Entity target) {

        return new Vec3(target.getX() - this.getX(), (target.getY() + (double) target.getEyeHeight()) - this.getY(), target.getZ() - this.getZ());
    }

    @Nullable
    private Entity getTarget() {

        return level.getEntity(entityData.get(TARGET));
    }

    private void setTarget(@Nullable Entity e) {

        entityData.set(TARGET, e == null ? ID_NO_TARGET : e.getId());
    }
    // endregion

    // region FACTORY
    public static final ArrowItemCoFH.IArrowFactory<AbstractArrow> FACTORY = new ArrowItemCoFH.IArrowFactory<>() {

        @Override
        public AbstractArrow createArrow(Level world, LivingEntity living) {

            return new ShulkerArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new ShulkerArrow(world, posX, posY, posZ);
        }
    };
    // endregion

    // region CONFIG
    public static final IBaseConfig CONFIG = new IBaseConfig() {

        @Override
        public void apply(ForgeConfigSpec.Builder builder) {

            String name = "Shulker Arrow";

            builder.push(name);
            cfgDamage = builder
                    .comment("Adjust this to set the damage for the " + name + ". Vanilla Arrow value is 2.0.")
                    .defineInRange("Damage", defaultDamage, 0.0, 16.0);
            cfgDuration = builder
                    .comment("Adjust this to set the effect duration (Levitation) for the " + name + ". (In ticks; there are 20 ticks per second).")
                    .defineInRange("Effect Duration", effectDuration, 20, 1200);
            builder.pop();
        }

        @Override
        public void refresh() {

            defaultDamage = cfgDamage.get().floatValue();
            effectDuration = cfgDuration.get();

        }

        private Supplier<Double> cfgDamage;
        private Supplier<Integer> cfgDuration;
    };
    // endregion
}
