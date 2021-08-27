package cofh.archersparadox.entity.projectile;

import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.GLOWSTONE_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.GLOWSTONE_ARROW_ITEM;
import static cofh.lib.util.constants.NBTTags.TAG_ARROW_DATA;

public class GlowstoneArrowEntity extends AbstractArrowEntity {

    private static final int CLOUD_DURATION = 20;

    public static float baseDamage = 0.5F;
    public static int effectRadius = 4;

    public boolean discharged;

    public GlowstoneArrowEntity(EntityType<? extends GlowstoneArrowEntity> entityIn, World worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = baseDamage;
    }

    public GlowstoneArrowEntity(World worldIn, LivingEntity shooter) {

        super(GLOWSTONE_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = baseDamage;
    }

    public GlowstoneArrowEntity(World worldIn, double x, double y, double z) {

        super(GLOWSTONE_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = baseDamage;
    }

    @Override
    protected ItemStack getPickupItem() {

        return discharged ? new ItemStack(Items.ARROW) : new ItemStack(GLOWSTONE_ARROW_ITEM);
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {

        super.onHit(raytraceResultIn);

        if (Utils.isServerWorld(level) && !discharged && !isInWater() && effectRadius > 0) {
            AreaUtils.transformGlowAir(this, level, this.blockPosition(), effectRadius);
            // makeAreaOfEffectCloud();
            discharged = true;
        }

        //        if (!discharged && raytraceResultIn.getType() != RayTraceResult.Type.MISS) {
        //            if (effectRadius > 0 && !isInWater()) {
        //                if (Utils.isServerWorld(world)) {
        //                    AreaUtils.transformGlowAir(this, world, this.getPosition(), effectRadius);
        //                    // makeAreaOfEffectCloud();
        //                }
        //                discharged = true;
        //            }
        //        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult raytraceResultIn) {

        super.onHitEntity(raytraceResultIn);

        // TODO: Mob transforms go here, if any.
        //        Entity entity = raytraceResultIn.getEntity();
        //        if (!entity.isInvulnerable() && entity instanceof CowEntity) {
        //
        //        }
    }

    @Override
    public void setSecondsOnFire(int seconds) {

    }

    @Override
    public void setCritArrow(boolean critical) {

    }

    @Override
    public void setKnockback(int knockbackStrengthIn) {

    }

    @Override
    public void setPierceLevel(byte level) {

    }

    @Override
    public void tick() {

        super.tick();

        if (!this.inGround || this.isNoPhysics()) {
            if (Utils.isClientWorld(level) && !isInWater()) {
                Vector3d vec3d = this.getDeltaMovement();
                double d1 = vec3d.x;
                double d2 = vec3d.y;
                double d0 = vec3d.z;
                this.level.addParticle(ParticleTypes.INSTANT_EFFECT, this.getX() + d1 * 0.25D, this.getY() + d2 * 0.25D, this.getZ() + d0 * 0.25D, -d1, -d2 + 0.2D, -d0);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {

        super.addAdditionalSaveData(compound);
        compound.putBoolean(TAG_ARROW_DATA, discharged);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {

        super.readAdditionalSaveData(compound);
        discharged = compound.getBoolean(TAG_ARROW_DATA);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(level, getX(), getY(), getZ());
        cloud.setRadius(1);
        cloud.setParticle(ParticleTypes.INSTANT_EFFECT);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((effectRadius - cloud.getRadius()) / (float) cloud.getDuration());

        level.addFreshEntity(cloud);
    }

}
