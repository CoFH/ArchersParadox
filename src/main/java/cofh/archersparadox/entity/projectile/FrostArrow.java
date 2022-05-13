package cofh.archersparadox.entity.projectile;

import cofh.lib.config.IBaseConfig;
import cofh.lib.item.impl.ArrowItemCoFH;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.FROST_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.FROST_ARROW_ITEM;
import static cofh.lib.util.constants.NBTTags.TAG_ARROW_DATA;
import static cofh.lib.util.references.CoreReferences.CHILLED;
import static cofh.lib.util.references.CoreReferences.FROST_PARTICLE;

public class FrostArrow extends AbstractArrow {

    private static final int CLOUD_DURATION = 20;

    public static float defaultDamage = 1.5F;
    public static int effectAmplifier = 1;
    public static int effectDuration = 100;
    public static int effectRadius = 4;
    public static boolean permanentLava = true;
    public static boolean permanentWater = true;

    public boolean discharged;

    public FrostArrow(EntityType<? extends FrostArrow> entityIn, Level worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
    }

    public FrostArrow(Level worldIn, LivingEntity shooter) {

        super(FROST_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = defaultDamage;
    }

    public FrostArrow(Level worldIn, double x, double y, double z) {

        super(FROST_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = defaultDamage;
    }

    @Override
    protected ItemStack getPickupItem() {

        return discharged ? new ItemStack(Items.ARROW) : new ItemStack(FROST_ARROW_ITEM);
    }

    @Override
    protected void onHit(HitResult raytraceResultIn) {

        super.onHit(raytraceResultIn);

        if (!discharged && raytraceResultIn.getType() != HitResult.Type.MISS) {
            if (effectRadius > 0) {
                if (Utils.isServerWorld(level)) {
                    AreaUtils.freezeNearbyGround(this, level, this.blockPosition(), effectRadius);
                    AreaUtils.freezeSurfaceWater(this, level, this.blockPosition(), effectRadius, permanentWater);
                    AreaUtils.freezeSurfaceLava(this, level, this.blockPosition(), effectRadius, permanentLava);
                    makeAreaOfEffectCloud();
                }
                discharged = true;
            }
            if (lastState != null && lastState.getFluidState() != Fluids.EMPTY.defaultFluidState()) {
                this.inGround = false;
                this.setDeltaMovement(getDeltaMovement().multiply((this.random.nextFloat() * 0.2F), (this.random.nextFloat() * 0.2F), (this.random.nextFloat() * 0.2F)));
                this.life = 0;
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult raytraceResultIn) {

        super.onHitEntity(raytraceResultIn);

        Entity entity = raytraceResultIn.getEntity();
        if (entity.isOnFire()) {
            entity.clearFire();
        }
        if (!entity.isInvulnerable() && entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            living.addEffect(new MobEffectInstance(CHILLED, effectDuration, effectAmplifier, false, false));
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

        if (!this.level.isClientSide) {
            this.setSharedFlag(6, isCurrentlyGlowing());
        }
        // The underlying Projectile and Entity tick() calls - we do NOT want to call super.tick().
        {
            if (!this.hasBeenShot) {
                this.gameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner(), this.blockPosition());
                this.hasBeenShot = true;
            }
            if (!this.leftOwner) {
                this.leftOwner = this.checkLeftOwner();
            }
            this.baseTick();
        }
        boolean flag = this.isNoPhysics();
        Vec3 vec3 = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = vec3.horizontalDistance();
            this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
            this.setXRot((float) (Mth.atan2(vec3.y, d0) * (double) (180F / (float) Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level.getBlockState(blockpos);
        if (!blockstate.isAir() && !flag) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level, blockpos);
            if (!voxelshape.isEmpty()) {
                Vec3 vec31 = this.position();
                for (AABB aabb : voxelshape.toAabbs()) {
                    if (aabb.move(blockpos).contains(vec31)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }
        if (this.shakeTime > 0) {
            --this.shakeTime;
        }
        if (this.isInWaterOrRain() || blockstate.is(Blocks.POWDER_SNOW)) {
            this.clearFire();
        }
        if (this.inGround && !flag) {
            if (this.lastState != blockstate && this.shouldFall()) {
                this.startFalling();
            } else if (!this.level.isClientSide) {
                this.tickDespawn();
            }
            ++this.inGroundTime;
        } else {
            this.inGroundTime = 0;
            Vec3 vec32 = this.position();
            Vec3 vec33 = vec32.add(vec3);
            HitResult hitresult = this.level.clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (hitresult.getType() != HitResult.Type.MISS) {
                vec33 = hitresult.getLocation();
            }
            while (!this.isRemoved()) {
                EntityHitResult entityhitresult = this.findHitEntity(vec32, vec33);
                if (entityhitresult != null) {
                    hitresult = entityhitresult;
                }
                if (hitresult != null && hitresult.getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult) hitresult).getEntity();
                    Entity entity1 = this.getOwner();
                    if (entity instanceof Player && entity1 instanceof Player && !((Player) entity1).canHarmPlayer((Player) entity)) {
                        hitresult = null;
                        entityhitresult = null;
                    }
                }
                if (hitresult != null && hitresult.getType() != HitResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                    this.onHit(hitresult);
                    this.hasImpulse = true;
                }
                if (entityhitresult == null || this.getPierceLevel() <= 0) {
                    break;
                }
                hitresult = null;
            }
            vec3 = this.getDeltaMovement();
            double d5 = vec3.x;
            double d6 = vec3.y;
            double d1 = vec3.z;

            this.level.addParticle(FROST_PARTICLE, this.getX() + d5 * 0.25D, this.getY() + d6 * 0.25D, this.getZ() + d1 * 0.25D, -d5, -d6 + 0.2D, -d1);

            double d7 = this.getX() + d5;
            double d2 = this.getY() + d6;
            double d3 = this.getZ() + d1;
            double d4 = vec3.horizontalDistance();
            if (flag) {
                this.setYRot((float) (Mth.atan2(-d5, -d1) * (double) (180F / (float) Math.PI)));
            } else {
                this.setYRot((float) (Mth.atan2(d5, d1) * (double) (180F / (float) Math.PI)));
            }
            this.setXRot((float) (Mth.atan2(d6, d4) * (double) (180F / (float) Math.PI)));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
            float f = 0.99F;
            if (this.isInWater()) {
                for (int j = 0; j < 4; ++j) {
                    this.level.addParticle(ParticleTypes.BUBBLE, d7 - d5 * 0.25D, d2 - d6 * 0.25D, d3 - d1 * 0.25D, d5, d6, d1);
                }
                f = this.getWaterInertia();
            }
            this.setDeltaMovement(vec3.scale(f));
            if (!this.isNoGravity() && !flag) {
                Vec3 vec34 = this.getDeltaMovement();
                this.setDeltaMovement(vec34.x, vec34.y - (double) 0.05F, vec34.z);
            }
            this.setPos(d7, d2, d3);
            this.checkInsideBlocks();
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
        cloud.setParticle(FROST_PARTICLE);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((effectRadius - cloud.getRadius()) / (float) cloud.getDuration());

        level.addFreshEntity(cloud);
    }

    // region FACTORY
    public static final ArrowItemCoFH.IArrowFactory<AbstractArrow> FACTORY = new ArrowItemCoFH.IArrowFactory<>() {

        @Override
        public AbstractArrow createArrow(Level world, LivingEntity living) {

            return new FrostArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new FrostArrow(world, posX, posY, posZ);
        }
    };
    // endregion

    // region CONFIG
    public static final IBaseConfig CONFIG = new IBaseConfig() {

        @Override
        public void apply(ForgeConfigSpec.Builder builder) {

            String name = "Frost Arrow";

            builder.push(name);
            cfgDamage = builder
                    .comment("Adjust this to set the damage for the " + name + ". Vanilla Arrow value is 2.0.")
                    .defineInRange("Damage", defaultDamage, 0.0, 16.0);
            cfgDuration = builder
                    .comment("Adjust this to set the chill duration for the " + name + "  (in ticks; there are 20 ticks per second).")
                    .defineInRange("Chill Duration", effectDuration, 20, 1200);
            cfgRadius = builder
                    .comment("Adjust this to set the effect radius for the " + name + ". Set to 0 to disable, but that would be boring.")
                    .defineInRange("Radius", effectRadius, 0, 16);
            cfgPermanentLava = builder
                    .comment("If TRUE, " + name + "s will convert Lava into Obsidian. If FALSE, Glossed Magma.")
                    .define("Permanent Lava Freeze", permanentLava);
            cfgPermanentWater = builder
                    .comment("If TRUE, " + name + "s will convert Water into Ice. If FALSE, Frosted Ice.")
                    .define("Permanent Water Freeze", permanentWater);
            builder.pop();
        }

        @Override
        public void refresh() {

            defaultDamage = cfgDamage.get().floatValue();

            effectDuration = cfgDuration.get();
            effectRadius = cfgRadius.get();

            permanentLava = cfgPermanentLava.get();
            permanentWater = cfgPermanentWater.get();
        }

        private ForgeConfigSpec.DoubleValue cfgDamage;
        private ForgeConfigSpec.IntValue cfgDuration;
        private ForgeConfigSpec.IntValue cfgRadius;

        private ForgeConfigSpec.BooleanValue cfgPermanentLava;
        private ForgeConfigSpec.BooleanValue cfgPermanentWater;
    };
    // endregion
}
