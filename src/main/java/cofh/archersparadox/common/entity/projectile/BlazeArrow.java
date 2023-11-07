package cofh.archersparadox.common.entity.projectile;

import cofh.core.common.config.IBaseConfig;
import cofh.core.util.AreaUtils;
import cofh.lib.common.item.ArrowItemCoFH;
import cofh.lib.util.Utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

import static cofh.archersparadox.init.registries.ModEntities.BLAZE_ARROW;
import static cofh.archersparadox.init.registries.ModItems.BLAZE_ARROW_ITEM;
import static cofh.lib.util.constants.NBTTags.TAG_ARROW_DATA;

public class BlazeArrow extends AbstractArrow {

    private static final int CLOUD_DURATION = 20;

    public static float defaultDamage = 1.5F;
    public static int effectDuration = 10;
    public static int effectRadius = 2;

    public boolean discharged;

    public BlazeArrow(EntityType<? extends BlazeArrow> entityIn, Level worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
    }

    public BlazeArrow(Level worldIn, LivingEntity shooter) {

        super(BLAZE_ARROW.get(), shooter, worldIn);
        this.baseDamage = defaultDamage;
    }

    public BlazeArrow(Level worldIn, double x, double y, double z) {

        super(BLAZE_ARROW.get(), x, y, z, worldIn);
        this.baseDamage = defaultDamage;
    }

    @Override
    protected ItemStack getPickupItem() {

        return discharged ? new ItemStack(Items.ARROW) : new ItemStack(BLAZE_ARROW_ITEM.get());
    }

    @Override
    protected void onHit(HitResult raytraceResultIn) {

        super.onHit(raytraceResultIn);

        if (Utils.isServerWorld(level) && !discharged && !isInWater() && effectRadius > 0) {
            if (effectDuration - 5 > 0) {
                AreaUtils.igniteNearbyEntities(this, level, this.blockPosition(), effectRadius, effectDuration - 5);
            }
            AreaUtils.igniteSpecial(this, level, this.blockPosition(), effectRadius, true, true, getOwner());
            AreaUtils.igniteNearbyGround(this, level, this.blockPosition(), effectRadius, 0.1);
            makeAreaOfEffectCloud();
            discharged = true;
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult raytraceResultIn) {

        super.onHitEntity(raytraceResultIn);

        Entity entity = raytraceResultIn.getEntity();
        if (!entity.isInvulnerable() && !entity.fireImmune() && !isInWater() && !(entity instanceof EnderMan)) {
            entity.setSecondsOnFire(effectDuration);
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
                this.level.addParticle(ParticleTypes.LAVA, this.getX() + d1 * 0.25D, this.getY() + d2 * 0.25D, this.getZ() + d0 * 0.25D, -d1, -d2 + 0.2D, -d0);
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
    public boolean isOnFire() {

        return !this.level.isClientSide;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloud cloud = new AreaEffectCloud(level, getX(), getY(), getZ());
        cloud.setRadius(1);
        cloud.setParticle(ParticleTypes.FLAME);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((effectRadius - cloud.getRadius()) / (float) cloud.getDuration());

        level.addFreshEntity(cloud);
    }

    // region FACTORY
    public static final ArrowItemCoFH.IArrowFactory<AbstractArrow> FACTORY = new ArrowItemCoFH.IArrowFactory<>() {

        @Override
        public AbstractArrow createArrow(Level world, LivingEntity living) {

            return new BlazeArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new BlazeArrow(world, posX, posY, posZ);
        }
    };
    // endregion

    // region CONFIG
    public static final IBaseConfig CONFIG = new IBaseConfig() {

        @Override
        public void apply(ForgeConfigSpec.Builder builder) {

            String name = "Blaze Arrow";

            builder.push(name);
            cfgDamage = builder
                    .comment("Adjust this to set the damage for the " + name + ". Vanilla Arrow value is 2.0.")
                    .defineInRange("Damage", defaultDamage, 0.0, 16.0);
            cfgDuration = builder
                    .comment("Adjust this to set the burn duration for the " + name + " (in seconds). Nearby targets will burn for 5 seconds less than a direct target.")
                    .defineInRange("Burn Duration", effectDuration, 5, 30);
            cfgRadius = builder
                    .comment("Adjust this to set the effect radius for the " + name + ". Set to 0 to disable, but that would be boring.")
                    .defineInRange("Radius", effectRadius, 0, 16);
            builder.pop();
        }

        @Override
        public void refresh() {

            defaultDamage = cfgDamage.get().floatValue();

            effectDuration = cfgDuration.get();
            effectRadius = cfgRadius.get();
        }

        private Supplier<Double> cfgDamage;
        private Supplier<Integer> cfgDuration;
        private Supplier<Integer> cfgRadius;
    };
    // endregion
}
