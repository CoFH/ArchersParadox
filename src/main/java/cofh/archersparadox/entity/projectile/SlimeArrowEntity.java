package cofh.archersparadox.entity.projectile;

import cofh.lib.util.Utils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
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

import static cofh.archersparadox.init.APReferences.SLIME_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.SLIME_ARROW_ITEM;
import static cofh.lib.util.constants.NBTTags.TAG_ARROW_DATA;

public class SlimeArrowEntity extends AbstractArrowEntity {

    private static float MAX_VELOCITY = 3.0F;
    private static float MIN_VELOCITY = 0.5F;

    public static float baseDamage = 0.5F;
    public static int baseBounces = 4;
    public static int baseKnockback = 4;
    public static boolean knockbackBoost = true;

    private int curBounces = 0;
    private int maxBounces = baseBounces;

    public SlimeArrowEntity(EntityType<? extends SlimeArrowEntity> entityIn, World worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = baseDamage;
        setKnockback(0);
    }

    public SlimeArrowEntity(World worldIn, LivingEntity shooter) {

        super(SLIME_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = baseDamage;
        setKnockback(0);
    }

    public SlimeArrowEntity(World worldIn, double x, double y, double z) {

        super(SLIME_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = baseDamage;
        setKnockback(0);
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(SLIME_ARROW_ITEM);
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {

        if (raytraceResultIn.getType() != RayTraceResult.Type.MISS) {
            this.setSoundEvent(SoundEvents.SLIME_BLOCK_HIT);

            if (raytraceResultIn.getType() == RayTraceResult.Type.ENTITY) {
                this.onHitEntity((EntityRayTraceResult) raytraceResultIn);
            } else if (raytraceResultIn.getType() == RayTraceResult.Type.BLOCK) {
                Vector3d motion = getDeltaMovement();
                if (motion.lengthSqr() < MIN_VELOCITY || isInWater() || curBounces >= maxBounces) {
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
                ++curBounces;
                --knockback;
            }
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

        super.setKnockback(baseKnockback + knockbackStrengthIn);
        if (knockbackBoost) {
            this.maxBounces = baseBounces + this.knockback;
        }
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
                this.level.addParticle(ParticleTypes.ITEM_SLIME, this.getX() + d1 * 0.25D, this.getY() + d2 * 0.25D, this.getZ() + d0 * 0.25D, -d1, -d2 + 0.2D, -d0);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {

        super.addAdditionalSaveData(compound);
        compound.putInt(TAG_ARROW_DATA, curBounces);

    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {

        super.readAdditionalSaveData(compound);
        curBounces = compound.getInt(TAG_ARROW_DATA);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
