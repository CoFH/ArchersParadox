package cofh.archersparadox.entity.projectile;

import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.FROST_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.FROST_ARROW_ITEM;
import static cofh.lib.util.constants.NBTTags.TAG_ARROW_DATA;
import static cofh.lib.util.references.CoreReferences.CHILLED;

public class FrostArrowEntity extends AbstractArrowEntity {

    private static final int CLOUD_DURATION = 20;

    public static float baseDamage = 1.5F;
    public static int effectAmplifier = 1;
    public static int effectDuration = 100;
    public static int effectRadius = 4;
    public static boolean permanentLava = true;
    public static boolean permanentWater = true;

    public boolean discharged;

    public FrostArrowEntity(EntityType<? extends FrostArrowEntity> entityIn, World worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = baseDamage;
    }

    public FrostArrowEntity(World worldIn, LivingEntity shooter) {

        super(FROST_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = baseDamage;
    }

    public FrostArrowEntity(World worldIn, double x, double y, double z) {

        super(FROST_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = baseDamage;
    }

    @Override
    protected ItemStack getPickupItem() {

        return discharged ? new ItemStack(Items.ARROW) : new ItemStack(FROST_ARROW_ITEM);
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {

        super.onHit(raytraceResultIn);

        if (!discharged && raytraceResultIn.getType() != RayTraceResult.Type.MISS) {
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
    protected void onHitEntity(EntityRayTraceResult raytraceResultIn) {

        super.onHitEntity(raytraceResultIn);

        Entity entity = raytraceResultIn.getEntity();
        if (entity.isOnFire()) {
            entity.clearFire();
        }
        if (!entity.isInvulnerable() && entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            living.addEffect(new EffectInstance(CHILLED, effectDuration, effectAmplifier, false, false));
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
            this.setSharedFlag(6, this.isGlowing());
        }
        this.baseTick();

        boolean flag = this.isNoPhysics();
        Vector3d vec3d = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = MathHelper.sqrt(getHorizontalDistanceSqr(vec3d));
            this.yRot = (float) (MathHelper.atan2(vec3d.x, vec3d.z) * (double) (180F / (float) Math.PI));
            this.xRot = (float) (MathHelper.atan2(vec3d.y, f) * (double) (180F / (float) Math.PI));
            this.yRotO = this.yRot;
            this.xRotO = this.xRot;
        }
        BlockPos blockpos = new BlockPos(this.getX(), this.getY(), this.getZ());
        BlockState blockstate = this.level.getBlockState(blockpos);
        if (!blockstate.isAir(this.level, blockpos) && !flag) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level, blockpos);
            if (!voxelshape.isEmpty()) {
                for (AxisAlignedBB axisalignedbb : voxelshape.toAabbs()) {
                    if (axisalignedbb.move(blockpos).contains(new Vector3d(this.getX(), this.getY(), this.getZ()))) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }
        if (this.shakeTime > 0) {
            --this.shakeTime;
        }
        if (this.isInWaterOrRain()) {
            this.clearFire();
        }
        if (this.inGround && !flag) {
            if (this.lastState != blockstate && this.level.noCollision(this.getBoundingBox().inflate(0.06D))) {
                this.inGround = false;
                this.setDeltaMovement(vec3d.multiply((this.random.nextFloat() * 0.2F), (this.random.nextFloat() * 0.2F), (this.random.nextFloat() * 0.2F)));
                this.life = 0;
            } else if (!this.level.isClientSide) {
                this.tickDespawn();
            }
            ++this.inGroundTime;
        } else {
            this.inGroundTime = 0;
            Vector3d vec3d1 = new Vector3d(this.getX(), this.getY(), this.getZ());
            Vector3d vec3d2 = vec3d1.add(vec3d);
            RayTraceResult raytraceresult = this.level.clip(new RayTraceContext(vec3d1, vec3d2, RayTraceContext.BlockMode.COLLIDER, discharged ? RayTraceContext.FluidMode.NONE : RayTraceContext.FluidMode.SOURCE_ONLY, this));
            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                vec3d2 = raytraceresult.getLocation();
            }
            while (this.isAlive()) {
                EntityRayTraceResult entityraytraceresult = this.findHitEntity(vec3d1, vec3d2);
                if (entityraytraceresult != null) {
                    raytraceresult = entityraytraceresult;
                }
                if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                    Entity entity = ((EntityRayTraceResult) raytraceresult).getEntity();
                    Entity entity1 = this.getOwner();
                    if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity) entity1).canHarmPlayer((PlayerEntity) entity)) {
                        raytraceresult = null;
                        entityraytraceresult = null;
                    }
                }
                if (raytraceresult != null && !flag && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                    this.onHit(raytraceresult);
                    this.hasImpulse = true;
                }
                if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
                    break;
                }
                raytraceresult = null;
            }
            vec3d = this.getDeltaMovement();
            double d1 = vec3d.x;
            double d2 = vec3d.y;
            double d0 = vec3d.z;
            //            if (this.getIsCritical()) {
            //                for (int i = 0; i < 4; ++i) {
            //                    this.world.addParticle(ParticleTypes.CRIT, this.getPosX() + d1 * (double) i / 4.0D, this.getPosY() + d2 * (double) i / 4.0D, this.getPosZ() + d0 * (double) i / 4.0D, -d1, -d2 + 0.2D, -d0);
            //                }
            //            }
            this.level.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX() + d1 * 0.25D, this.getY() + d2 * 0.25D, this.getZ() + d0 * 0.25D, -d1, -d2 + 0.2D, -d0);

            this.setPosRaw(this.getX() + d1, this.getY() + d2, this.getZ() + d0);
            float f4 = MathHelper.sqrt(getHorizontalDistanceSqr(vec3d));
            if (flag) {
                this.yRot = (float) (MathHelper.atan2(-d1, -d0) * (double) (180F / (float) Math.PI));
            } else {
                this.yRot = (float) (MathHelper.atan2(d1, d0) * (double) (180F / (float) Math.PI));
            }
            this.xRot = (float) (MathHelper.atan2(d2, f4) * (double) (180F / (float) Math.PI));
            while (this.xRot - this.xRotO < -180.0F) {
                this.xRotO -= 360.0F;
            }
            while (this.xRot - this.xRotO >= 180.0F) {
                this.xRotO += 360.0F;
            }
            while (this.yRot - this.yRotO < -180.0F) {
                this.yRotO -= 360.0F;
            }
            while (this.yRot - this.yRotO >= 180.0F) {
                this.yRotO += 360.0F;
            }
            this.xRot = MathHelper.lerp(0.2F, this.xRotO, this.xRot);
            this.yRot = MathHelper.lerp(0.2F, this.yRotO, this.yRot);
            float f1 = 0.99F;
            if (this.isInWater()) {
                for (int j = 0; j < 4; ++j) {
                    this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - d1 * 0.25D, this.getY() - d2 * 0.25D, this.getZ() - d0 * 0.25D, d1, d2, d0);
                }
                f1 = this.getWaterInertia();
            }
            this.setDeltaMovement(vec3d.scale(f1));
            if (!this.isNoGravity() && !flag) {
                Vector3d vec3d3 = this.getDeltaMovement();
                this.setDeltaMovement(vec3d3.x, vec3d3.y - 0.05D, vec3d3.z);
            }
            this.setPos(this.getX(), this.getY(), this.getZ());
            this.checkInsideBlocks();
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
        cloud.setParticle(ParticleTypes.ITEM_SNOWBALL);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((effectRadius - cloud.getRadius()) / (float) cloud.getDuration());

        level.addFreshEntity(cloud);
    }

}
