package cofh.archersparadox.entity.projectile;

import cofh.lib.config.IBaseConfig;
import cofh.lib.item.impl.ArrowItemCoFH;
import cofh.lib.util.Utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.SLIME_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.SLIME_ARROW_ITEM;
import static cofh.lib.util.constants.NBTTags.TAG_ARROW_DATA;

public class SlimeArrow extends AbstractArrow {

    private static final float MAX_VELOCITY = 3.0F;
    private static final float MIN_VELOCITY = 0.5F;

    public static float defaultDamage = 0.5F;
    public static int defaultBounces = 4;
    public static int defaultKnockback = 4;
    public static boolean knockbackBoost = true;

    private int curBounces = 0;
    private int maxBounces = defaultBounces;

    public SlimeArrow(EntityType<? extends SlimeArrow> entityIn, Level worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
    }

    public SlimeArrow(Level worldIn, LivingEntity shooter) {

        super(SLIME_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
    }

    public SlimeArrow(Level worldIn, double x, double y, double z) {

        super(SLIME_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(SLIME_ARROW_ITEM);
    }

    @Override
    protected void onHit(HitResult raytraceResultIn) {

        if (raytraceResultIn.getType() != HitResult.Type.MISS) {
            this.setSoundEvent(SoundEvents.SLIME_BLOCK_HIT);

            if (raytraceResultIn.getType() == HitResult.Type.ENTITY) {
                this.onHitEntity((EntityHitResult) raytraceResultIn);
            } else if (raytraceResultIn.getType() == HitResult.Type.BLOCK) {
                Vec3 motion = getDeltaMovement();
                if (motion.lengthSqr() < MIN_VELOCITY || isInWater() || curBounces >= maxBounces) {
                    super.onHit(raytraceResultIn);
                    return;
                }
                BlockHitResult blockraytraceresult = (BlockHitResult) raytraceResultIn;
                switch (blockraytraceresult.getDirection()) {
                    case DOWN, UP -> this.setDeltaMovement(motion.x, motion.y * -1, motion.z);
                    case NORTH, SOUTH -> this.setDeltaMovement(motion.x, motion.y, motion.z * -1);
                    case WEST, EAST -> this.setDeltaMovement(motion.x * -1, motion.y, motion.z);
                }
                double f = motion.horizontalDistance();
                this.yRot = (float) (Mth.atan2(motion.x, motion.z) * (double) (180F / (float) Math.PI));
                this.xRot = (float) (Mth.atan2(motion.y, f) * (double) (180F / (float) Math.PI));
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

        super.setKnockback(defaultKnockback + knockbackStrengthIn);
        if (knockbackBoost) {
            this.maxBounces = defaultBounces + this.knockback;
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
                Vec3 vec3d = this.getDeltaMovement();
                double d1 = vec3d.x;
                double d2 = vec3d.y;
                double d0 = vec3d.z;
                this.level.addParticle(ParticleTypes.ITEM_SLIME, this.getX() + d1 * 0.25D, this.getY() + d2 * 0.25D, this.getZ() + d0 * 0.25D, -d1, -d2 + 0.2D, -d0);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {

        super.addAdditionalSaveData(compound);
        compound.putInt(TAG_ARROW_DATA, curBounces);

    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {

        super.readAdditionalSaveData(compound);
        curBounces = compound.getInt(TAG_ARROW_DATA);
    }

    @Override
    public Packet<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // region FACTORY
    public static final ArrowItemCoFH.IArrowFactory<AbstractArrow> FACTORY = new ArrowItemCoFH.IArrowFactory<>() {

        @Override
        public AbstractArrow createArrow(Level world, LivingEntity living) {

            return new SlimeArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new SlimeArrow(world, posX, posY, posZ);
        }
    };
    // endregion

    // region CONFIG
    public static final IBaseConfig CONFIG = new IBaseConfig() {

        @Override
        public void apply(ForgeConfigSpec.Builder builder) {

            String name = "Slime Arrow";

            builder.push(name);
            cfgDamage = builder
                    .comment("Adjust this to set the damage for the " + name + ". Vanilla Arrow value is 2.0.")
                    .defineInRange("Damage", defaultDamage, 0.0, 16.0);
            cfgKnockback = builder
                    .comment("Adjust this to set the inherent knockback strength of the " + name + ". Vanilla Arrow value is 0.")
                    .defineInRange("Knockback", defaultKnockback, 0, 16);
            cfgBounces = builder
                    .comment("Adjust this to set the number of bounces for the " + name + ".")
                    .defineInRange("Bounces", defaultBounces, 1, 16);
            cfgKnockbackBoost = builder
                    .comment("If TRUE, bounces are modified by knockback bonuses, such as the Punch Enchantment.")
                    .define("Knockback Boost", knockbackBoost);
            builder.pop();
        }

        @Override
        public void refresh() {

            defaultDamage = cfgDamage.get().floatValue();
            defaultKnockback = cfgKnockback.get();

            defaultBounces = cfgBounces.get();
            knockbackBoost = cfgKnockbackBoost.get();
        }

        private ForgeConfigSpec.DoubleValue cfgDamage;
        private ForgeConfigSpec.IntValue cfgKnockback;
        private ForgeConfigSpec.IntValue cfgBounces;
        private ForgeConfigSpec.BooleanValue cfgKnockbackBoost;
    };
    // endregion
}
