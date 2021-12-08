package cofh.archersparadox.entity.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.PRISMARINE_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.PRISMARINE_ARROW_ITEM;

public class PrismarineArrowEntity extends AbstractArrowEntity {

    public static float defaultDamage = 2.0F;
    public static int defaultKnockback = 0;
    public static byte defaultPierce = 0;

    public PrismarineArrowEntity(EntityType<? extends PrismarineArrowEntity> entityIn, World worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    public PrismarineArrowEntity(World worldIn, LivingEntity shooter) {

        super(PRISMARINE_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    public PrismarineArrowEntity(World worldIn, double x, double y, double z) {

        super(PRISMARINE_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(PRISMARINE_ARROW_ITEM);
    }

    @Override
    public void setKnockback(int knockbackStrengthIn) {

        super.setKnockback(defaultKnockback + knockbackStrengthIn);
    }

    @Override
    public void setPierceLevel(byte level) {

        super.setPierceLevel((byte) (defaultPierce + level));
    }

    @Override
    protected float getWaterInertia() {

        return 0.99F;
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
            RayTraceResult raytraceresult = this.level.clip(new RayTraceContext(vec3d1, vec3d2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
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
            if (this.isCritArrow()) {
                for (int i = 0; i < 4; ++i) {
                    this.level.addParticle(ParticleTypes.CRIT, this.getX() + d1 * (double) i / 4.0D, this.getY() + d2 * (double) i / 4.0D, this.getZ() + d0 * (double) i / 4.0D, -d1, -d2 + 0.2D, -d0);
                }
            }
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
            //            if (this.isInWater()) {
            //                for (int j = 0; j < 4; ++j) {
            //                    this.world.addParticle(ParticleTypes.BUBBLE, this.getPosX() - d1 * 0.25D, this.getPosY() - d2 * 0.25D, this.getPosZ() - d0 * 0.25D, d1, d2, d0);
            //                }
            //                f1 = this.getWaterDrag();
            //            }
            this.setDeltaMovement(vec3d.scale(f1));
            if (!this.isNoGravity() && !flag) {
                Vector3d vec3d3 = this.getDeltaMovement();
                this.setDeltaMovement(vec3d3.x, vec3d3.y - (this.isInWater() ? 0.01D : 0.05D), vec3d3.z);
            }
            this.setPos(this.getX(), this.getY(), this.getZ());
            this.checkInsideBlocks();
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
