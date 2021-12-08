package cofh.archersparadox.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.PHANTASMAL_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.PHANTASMAL_ARROW_ITEM;

public class PhantasmalArrowEntity extends AbstractArrowEntity {

    private static boolean GLOWING = true;
    private static boolean NO_GRAVITY = true;

    private static int MAX_TICKS = 200;
    private static float MAX_VELOCITY = 2.5F;
    private static byte PIERCE = 16;

    private int ticksInAir;

    public PhantasmalArrowEntity(EntityType<? extends PhantasmalArrowEntity> entityIn, World worldIn) {

        super(entityIn, worldIn);
        setGlowing(GLOWING);
        setNoGravity(NO_GRAVITY);
        setPierceLevel(PIERCE);
    }

    public PhantasmalArrowEntity(World worldIn, LivingEntity shooter) {

        super(PHANTASMAL_ARROW_ENTITY, shooter, worldIn);
        setGlowing(GLOWING);
        setNoGravity(NO_GRAVITY);
        setPierceLevel(PIERCE);
    }

    public PhantasmalArrowEntity(World worldIn, double x, double y, double z) {

        super(PHANTASMAL_ARROW_ENTITY, x, y, z, worldIn);
        setGlowing(GLOWING);
        setNoGravity(NO_GRAVITY);
        setPierceLevel(PIERCE);
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(PHANTASMAL_ARROW_ITEM);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult raytraceResultIn) {

        Vector3d motion = getDeltaMovement();
        super.onHitEntity(raytraceResultIn);

        if (isAlive()) {
            setDeltaMovement(motion);
        }
    }

    @Override
    public void setCritArrow(boolean critical) {

    }

    @Override
    public void setKnockback(int knockbackStrengthIn) {

    }

    @Override
    public void setPierceLevel(byte level) {

        super.setPierceLevel(PIERCE);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {

        super.shoot(x, y, z, Math.min(velocity, MAX_VELOCITY), inaccuracy);
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
        //        BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
        //        BlockState blockstate = this.world.getBlockState(blockpos);
        //        if (!blockstate.isAir(this.world, blockpos) && !flag) {
        //            VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
        //            if (!voxelshape.isEmpty()) {
        //                for (AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
        //                    if (axisalignedbb.offset(blockpos).contains(new Vector3d(this.posX, this.posY, this.posZ))) {
        //                        this.inGround = true;
        //                        break;
        //                    }
        //                }
        //            }
        //        }
        //        if (this.arrowShake > 0) {
        //            --this.arrowShake;
        //        }
        //        if (this.isWet()) {
        //            this.extinguish();
        //        }
        //        if (this.inGround && !flag) {
        //            if (this.inBlockState != blockstate && this.world.areCollisionShapesEmpty(this.getBoundingBox().grow(0.06D))) {
        //                this.inGround = false;
        //                this.setMotion(vec3d.mul((this.rand.nextFloat() * 0.2F), (this.rand.nextFloat() * 0.2F), (this.rand.nextFloat() * 0.2F)));
        //                this.ticksInGround = 0;
        //                this.ticksInAir = 0;
        //            } else if (!this.world.isRemote) {
        //                this.tryDespawn();
        //            }
        //            ++this.timeInGround;
        //        } else {
        //        this.timeInGround = 0;
        ++this.ticksInAir;
        if (this.ticksInAir >= MAX_TICKS) {
            this.remove();
        }
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
            if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
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
        //                    this.world.addParticle(ParticleTypes.CRIT, this.posX + d1 * (double) i / 4.0D, this.posY + d2 * (double) i / 4.0D, this.posZ + d0 * (double) i / 4.0D, -d1, -d2 + 0.2D, -d0);
        //                }
        //            }
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
        //            float f1 = 0.99F;
        //            if (this.isInWater()) {
        //                for (int j = 0; j < 4; ++j) {
        //                    this.world.addParticle(ParticleTypes.BUBBLE, this.posX - d1 * 0.25D, this.posY - d2 * 0.25D, this.posZ - d0 * 0.25D, d1, d2, d0);
        //                }
        //                f1 = this.getWaterDrag();
        //            }
        //            this.setMotion(vec3d.scale(f1));
        //            if (!this.hasNoGravity() && !flag) {
        //                Vector3d vec3d3 = this.getMotion();
        //                this.setMotion(vec3d3.x, vec3d3.y - 0.05D, vec3d3.z);
        //            }
        this.setPos(this.getX(), this.getY(), this.getZ());
        //            this.doBlockCollisions();
        //    }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
