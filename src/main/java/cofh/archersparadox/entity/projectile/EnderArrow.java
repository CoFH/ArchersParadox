package cofh.archersparadox.entity.projectile;

import cofh.lib.item.ArrowItemCoFH;
import cofh.lib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import static cofh.archersparadox.init.APEntities.ENDER_ARROW;
import static cofh.archersparadox.init.APItems.ENDER_ARROW_ITEM;
import static cofh.core.init.CoreMobEffects.ENDERFERENCE;
import static cofh.lib.util.constants.NBTTags.TAG_ARROW_DATA;

public class EnderArrow extends AbstractArrow {

    private static final float defaultDamage = 0.5F;
    private static final int DURATION = 80;
    private static final int DURATION_FACTOR = 2;

    public boolean discharged;
    private BlockPos origin;

    public EnderArrow(EntityType<? extends EnderArrow> entityIn, Level worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
    }

    public EnderArrow(Level worldIn, LivingEntity shooter) {

        super(ENDER_ARROW.get(), shooter, worldIn);
        this.baseDamage = defaultDamage;
        this.origin = shooter.blockPosition();
    }

    public EnderArrow(Level worldIn, double x, double y, double z) {

        super(ENDER_ARROW.get(), x, y, z, worldIn);
        this.baseDamage = defaultDamage;
        this.origin = new BlockPos(x, y, z);
    }

    @Override
    protected ItemStack getPickupItem() {

        return discharged ? new ItemStack(Items.ARROW) : new ItemStack(ENDER_ARROW_ITEM.get());
    }

    @Override
    protected void onHit(HitResult raytraceResultIn) {

        super.onHit(raytraceResultIn);

        Entity shooter = getOwner();
        if (raytraceResultIn.getType() != HitResult.Type.MISS && !discharged && shooter != null) {
            int duration = DURATION;
            if (raytraceResultIn.getType() == HitResult.Type.BLOCK) {
                Utils.teleportEntityTo(shooter, this.blockPosition());
                if (shooter instanceof LivingEntity && !Utils.isFakePlayer(shooter)) {
                    ((LivingEntity) shooter).addEffect(new MobEffectInstance(ENDERFERENCE.get(), duration, 0, false, false));
                }
            }
            if (raytraceResultIn.getType() == HitResult.Type.ENTITY) {
                BlockPos originPos = origin == null ? shooter.blockPosition() : origin;
                Utils.teleportEntityTo(shooter, blockPosition());
                if (shooter instanceof LivingEntity && !Utils.isFakePlayer(shooter)) {
                    ((LivingEntity) shooter).addEffect(new MobEffectInstance(ENDERFERENCE.get(), duration, 0, false, false));
                }
                Entity entity = ((EntityHitResult) raytraceResultIn).getEntity();
                if (entity instanceof LivingEntity && entity.canChangeDimensions()) {
                    Utils.teleportEntityTo(entity, originPos);
                    ((LivingEntity) entity).addEffect(new MobEffectInstance(ENDERFERENCE.get(), duration, 0, false, false));
                }
            }
            discharged = true;
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
            if (Utils.isClientWorld(level)) {
                Vec3 vec3d = this.getDeltaMovement();
                double d1 = vec3d.x;
                double d2 = vec3d.y;
                double d0 = vec3d.z;
                this.level.addParticle(ParticleTypes.PORTAL, this.getX() + d1 * 0.25D, this.getY() + d2 * 0.25D, this.getZ() + d0 * 0.25D, -d1, -d2 + 0.2D, -d0);
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // region FACTORY
    public static final ArrowItemCoFH.IArrowFactory<AbstractArrow> FACTORY = new ArrowItemCoFH.IArrowFactory<>() {

        @Override
        public AbstractArrow createArrow(Level world, LivingEntity living) {

            return new EnderArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new EnderArrow(world, posX, posY, posZ);
        }
    };
    // endregion
}
