package cofh.archersparadox.entity.projectile;

import cofh.lib.item.ArrowItemCoFH;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;

import static cofh.archersparadox.init.ModEntities.PHANTASMAL_ARROW;
import static cofh.archersparadox.init.ModItems.PHANTASMAL_ARROW_ITEM;

public class PhantasmalArrow extends AbstractArrow {

    private static final boolean GLOWING = true;
    private static final boolean NO_GRAVITY = true;

    private static final int MAX_TICKS = 200;
    private static final float MAX_VELOCITY = 2.5F;
    private static final byte PIERCE = 64;

    private int ticksInAir;

    public PhantasmalArrow(EntityType<? extends PhantasmalArrow> entityIn, Level worldIn) {

        super(entityIn, worldIn);
        setGlowingTag(GLOWING);
        setNoGravity(NO_GRAVITY);
        setPierceLevel(PIERCE);
    }

    public PhantasmalArrow(Level worldIn, LivingEntity shooter) {

        super(PHANTASMAL_ARROW.get(), shooter, worldIn);
        setGlowingTag(GLOWING);
        setNoGravity(NO_GRAVITY);
        setPierceLevel(PIERCE);
    }

    public PhantasmalArrow(Level worldIn, double x, double y, double z) {

        super(PHANTASMAL_ARROW.get(), x, y, z, worldIn);
        setGlowingTag(GLOWING);
        setNoGravity(NO_GRAVITY);
        setPierceLevel(PIERCE);
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(PHANTASMAL_ARROW_ITEM.get());
    }

    @Override
    protected void onHitEntity(EntityHitResult raytraceResultIn) {

        Vec3 motion = getDeltaMovement();
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
            this.setSharedFlag(6, isCurrentlyGlowing());
        }
        // The underlying Projectile and Entity tick() calls - we do NOT want to call super.tick().
        {
            if (!this.hasBeenShot) {
                this.gameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner());
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
        ++this.ticksInAir;
        if (this.ticksInAir >= MAX_TICKS) {
            this.discard();
        }
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
                if (entity instanceof Player && entity1 instanceof Player owner && !owner.canHarmPlayer((Player) entity)) {
                    hitresult = null;
                    entityhitresult = null;
                }
            }
            if (hitresult != null && hitresult.getType() == HitResult.Type.ENTITY && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
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

        double d7 = this.getX() + d5;
        double d2 = this.getY() + d6;
        double d3 = this.getZ() + d1;
        this.setPosRaw(d7, d2, d3);
        double d4 = vec3.horizontalDistance();
        if (flag) {
            this.setYRot((float) (Mth.atan2(-d5, -d1) * (double) (180F / (float) Math.PI)));
        } else {
            this.setYRot((float) (Mth.atan2(d5, d1) * (double) (180F / (float) Math.PI)));
        }
        this.setXRot((float) (Mth.atan2(d6, d4) * (double) (180F / (float) Math.PI)));
        this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
        this.setYRot(lerpRotation(this.yRotO, this.getYRot()));

        this.setPos(d7, d2, d3);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // region FACTORY
    public static final ArrowItemCoFH.IArrowFactory<AbstractArrow> FACTORY = new ArrowItemCoFH.IArrowFactory<>() {

        @Override
        public AbstractArrow createArrow(Level world, LivingEntity living) {

            return new PhantasmalArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new PhantasmalArrow(world, posX, posY, posZ);
        }
    };
    // endregion
}
