package cofh.archersparadox.entity.projectile;

import cofh.lib.item.impl.ArrowItemCoFH;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.SPORE_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.SPORE_ARROW_ITEM;
import static cofh.lib.util.constants.NBTTags.TAG_ARROW_DATA;

public class SporeArrow extends AbstractArrow {

    private static final int CLOUD_DURATION = 20;

    public static float defaultDamage = 0.5F;
    public static int effectRadius = 4;
    public static int effectGrowCount = 4;
    public static boolean transform = true;

    public boolean discharged;

    public SporeArrow(EntityType<? extends SporeArrow> entityIn, Level worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
    }

    public SporeArrow(Level worldIn, LivingEntity shooter) {

        super(SPORE_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = defaultDamage;
    }

    public SporeArrow(Level worldIn, double x, double y, double z) {

        super(SPORE_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = defaultDamage;
    }

    @Override
    protected ItemStack getPickupItem() {

        return discharged ? new ItemStack(Items.ARROW) : new ItemStack(SPORE_ARROW_ITEM);
    }

    @Override
    protected void onHit(HitResult raytraceResultIn) {

        super.onHit(raytraceResultIn);

        if (Utils.isServerWorld(level) && !discharged && !isInWater() && effectRadius > 0) {
            AreaUtils.transformMycelium(this, level, this.blockPosition(), effectRadius);
            AreaUtils.growMushrooms(this, level, this.blockPosition(), effectRadius, effectGrowCount);
            makeAreaOfEffectCloud();
            discharged = true;
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult raytraceResultIn) {

        super.onHitEntity(raytraceResultIn);

        Entity entity = raytraceResultIn.getEntity();
        if (!entity.isInvulnerable() && entity instanceof Cow) {
            entity.discard();
            MushroomCow cow = EntityType.MOOSHROOM.create(entity.level);
            cow.setPos(entity.position());
            cow.setXRot(entity.getXRot());
            cow.setYRot(entity.getYRot());
            entity.level.addFreshEntity(cow);

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

    }

    @Override
    public void setPierceLevel(byte level) {

    }

    @Override
    public void tick() {

        super.tick();

        if (!this.inGround || this.isNoPhysics()) {
            if (Utils.isClientWorld(level) && !isInWater()) {
                Vec3 vec3d = this.getDeltaMovement();
                double d1 = vec3d.x;
                double d2 = vec3d.y;
                double d0 = vec3d.z;
                this.level.addParticle(ParticleTypes.MYCELIUM, this.getX() + d1 * 0.25D, this.getY() + d2 * 0.25D, this.getZ() + d0 * 0.25D, -d1, -d2 + 0.2D, -d0);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {

        super.addAdditionalSaveData(compound);
        compound.putBoolean(TAG_ARROW_DATA, discharged);

    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {

        super.readAdditionalSaveData(compound);
        discharged = compound.getBoolean(TAG_ARROW_DATA);
    }

    @Override
    public Packet<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloud cloud = new AreaEffectCloud(level, getX(), getY(), getZ());
        cloud.setRadius(1);
        cloud.setParticle(ParticleTypes.MYCELIUM);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((effectRadius - cloud.getRadius()) / (float) cloud.getDuration());

        level.addFreshEntity(cloud);
    }

    // region FACTORY
    public static final ArrowItemCoFH.IArrowFactory<AbstractArrow> FACTORY = new ArrowItemCoFH.IArrowFactory<>() {

        @Override
        public AbstractArrow createArrow(Level world, LivingEntity living) {

            return new SporeArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new SporeArrow(world, posX, posY, posZ);
        }
    };
    // endregion
}
