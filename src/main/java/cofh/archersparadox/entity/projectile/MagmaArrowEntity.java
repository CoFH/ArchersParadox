package cofh.archersparadox.entity.projectile;

import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.MAGMA_ARROW_ENTITY;
import static cofh.lib.util.constants.NBTTags.TAG_ARROW_DATA;

public class MagmaArrowEntity extends AbstractArrowEntity {

    private static float DAMAGE = 0.5F;
    private static final int DURATION = 5;
    private static int KNOCKBACK = 3;
    private static int KNOCKBACK_FACTOR = 1;
    private static float MAX_VELOCITY = 3.0F;
    private static float MIN_VELOCITY = 0.5F;
    private static final int RADIUS = 2;

    private int bounces = 0;
    private int maxBounces = KNOCKBACK;

    public MagmaArrowEntity(EntityType<? extends MagmaArrowEntity> entityIn, World worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = DAMAGE;
        this.knockback = KNOCKBACK;
    }

    public MagmaArrowEntity(World worldIn, LivingEntity shooter) {

        super(MAGMA_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = DAMAGE;
        this.knockback = KNOCKBACK;
    }

    public MagmaArrowEntity(World worldIn, double x, double y, double z) {

        super(MAGMA_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = DAMAGE;
        this.knockback = KNOCKBACK;
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(Items.ARROW);
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {

        if (raytraceResultIn.getType() != RayTraceResult.Type.MISS) {
            this.setSoundEvent(SoundEvents.MAGMA_CUBE_SQUISH);
            AreaUtils.igniteNearbyEntities(this, level, this.blockPosition(), RADIUS, DURATION);
            AreaUtils.igniteNearbyGround(this, level, this.blockPosition(), RADIUS, 0.1);

            if (raytraceResultIn.getType() == RayTraceResult.Type.ENTITY) {
                this.onHitEntity((EntityRayTraceResult) raytraceResultIn);
            } else if (raytraceResultIn.getType() == RayTraceResult.Type.BLOCK) {
                Vector3d motion = getDeltaMovement();
                if (motion.lengthSqr() < MIN_VELOCITY || isInWater() || bounces >= maxBounces) {
                    super.onHit(raytraceResultIn);
                    return;
                }
                BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceResultIn;
                switch (blockraytraceresult.getDirection()) {
                    case DOWN:
                    case UP:
                        this.setDeltaMovement(motion.x, motion.y * -1, motion.z);
                        break;
                    case NORTH:
                    case SOUTH:
                        this.setDeltaMovement(motion.x, motion.y, motion.z * -1);
                        break;
                    case WEST:
                    case EAST:
                        this.setDeltaMovement(motion.x * -1, motion.y, motion.z);
                        break;
                }
                float f = MathHelper.sqrt(getHorizontalDistanceSqr(motion));
                this.yRot = (float) (MathHelper.atan2(motion.x, motion.z) * (double) (180F / (float) Math.PI));
                this.xRot = (float) (MathHelper.atan2(motion.y, f) * (double) (180F / (float) Math.PI));
                this.yRotO = this.yRot;
                this.xRotO = this.xRot;
                ++bounces;
                --knockback;
            }
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult raytraceResultIn) {

        super.onHitEntity(raytraceResultIn);

        Entity entity = raytraceResultIn.getEntity();
        if (!isInWater() && !(entity instanceof EndermanEntity)) {
            entity.setSecondsOnFire(DURATION);
        }
    }

    @Override
    public void setSecondsOnFire(int seconds) {

    }

    @Override
    public void setCritArrow(boolean critical) {

    }

    @Override
    public void setKnockback(int knockbackStrengthIn) {

        super.setKnockback(KNOCKBACK + knockbackStrengthIn * KNOCKBACK_FACTOR);
        this.maxBounces = this.knockback;
    }

    @Override
    public void setPierceLevel(byte level) {

    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {

        super.shoot(x, y, z, Math.min(velocity, MAX_VELOCITY), inaccuracy);
    }

    @Override
    public void tick() {

        super.tick();

        if (!this.inGround || this.isNoPhysics()) {
            if (Utils.isClientWorld(level)) {
                Vector3d vec3d = this.getDeltaMovement();
                double d1 = vec3d.x;
                double d2 = vec3d.y;
                double d0 = vec3d.z;
                this.level.addParticle(ParticleTypes.LAVA, this.getX() + d1 * 0.25D, this.getY() + d2 * 0.25D, this.getZ() + d0 * 0.25D, -d1, -d2 + 0.2D, -d0);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {

        super.addAdditionalSaveData(compound);
        compound.putInt(TAG_ARROW_DATA, bounces);

    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {

        super.readAdditionalSaveData(compound);
        bounces = compound.getInt(TAG_ARROW_DATA);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
