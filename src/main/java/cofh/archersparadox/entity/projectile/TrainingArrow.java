package cofh.archersparadox.entity.projectile;

import cofh.lib.item.ArrowItemCoFH;
import cofh.lib.util.Utils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import static cofh.archersparadox.init.APEffects.TRAINING_MISS;
import static cofh.archersparadox.init.APEffects.TRAINING_STREAK;
import static cofh.archersparadox.init.APEntities.TRAINING_ARROW;
import static cofh.archersparadox.init.APItems.TRAINING_ARROW_ITEM;

public class TrainingArrow extends AbstractArrow {

    private static final float defaultDamage = 0.0F;
    private static final int DISTANCE_FACTOR = 4;
    private static final int MAX_DISTANCE = 10;
    private static final int DURATION = 200;
    private static final int MIN_DURATION = 40;

    private Vec3 origin;

    public TrainingArrow(EntityType<? extends TrainingArrow> entityIn, Level worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
    }

    public TrainingArrow(Level worldIn, LivingEntity shooter) {

        super(TRAINING_ARROW.get(), shooter, worldIn);
        this.baseDamage = defaultDamage;
        this.origin = shooter.position();
    }

    public TrainingArrow(Level worldIn, double x, double y, double z) {

        super(TRAINING_ARROW.get(), x, y, z, worldIn);
        this.baseDamage = defaultDamage;
        this.origin = new Vec3(x, y, z);
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(TRAINING_ARROW_ITEM.get());
    }

    @Override
    protected void onHit(HitResult raytraceResultIn) {

        if (raytraceResultIn.getType() == HitResult.Type.BLOCK) {
            if (getOwner() instanceof Player shooter && !Utils.isFakePlayer(getOwner())) {
                if (shooter.hasEffect(TRAINING_STREAK.get())) {
                    MobEffectInstance effect = shooter.getEffect(TRAINING_STREAK.get());
                    shooter.onEffectRemoved(effect);
                    shooter.removeEffectNoUpdate(TRAINING_STREAK.get());

                    Vec3 originVec = origin == null ? shooter.position() : origin;
                    double distance = originVec.distanceTo(this.position());
                    int distanceBonus = (int) (DISTANCE_FACTOR * distance);

                    shooter.addEffect(new MobEffectInstance(TRAINING_MISS.get(), Math.max(MIN_DURATION, DURATION - distanceBonus), 0, false, false));
                    shooter.level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.GLASS_BREAK, shooter.getSoundSource(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                }
            }
        }
        super.onHit(raytraceResultIn);
    }

    @Override
    protected void onHitEntity(EntityHitResult raytraceResultIn) {

        Entity entity = raytraceResultIn.getEntity();
        if (entity instanceof LivingEntity target) {
            if (getOwner() instanceof Player shooter && !Utils.isFakePlayer(getOwner())) {
                if (shooter != target && !shooter.hasEffect(TRAINING_MISS.get())) {
                    int trainingCount = 0;
                    if (shooter.hasEffect(TRAINING_STREAK.get())) {
                        trainingCount = shooter.getEffect(TRAINING_STREAK.get()).getAmplifier() + 1;
                    }
                    Vec3 originVec = origin == null ? shooter.position() : origin;
                    double distance = originVec.distanceTo(this.position());

                    if (distance >= Math.min(MAX_DISTANCE, trainingCount)) {
                        int distanceBonus = (int) (DISTANCE_FACTOR * distance);
                        shooter.addEffect(new MobEffectInstance(TRAINING_STREAK.get(), DURATION + distanceBonus, trainingCount, false, false));
                        shooter.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.0F, Math.min(0.6F + 0.05F * trainingCount, 1.1F));
                        shooter.level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.NOTE_BLOCK_CHIME, shooter.getSoundSource(), 1.0F, Math.min(0.6F + 0.05F * trainingCount, 1.1F));
                    }
                }
            }
        }
        if (this.pickup == Pickup.ALLOWED) {
            this.spawnAtLocation(this.getPickupItem(), 0.1F);
        }
        this.discard();
        this.playSound(SoundEvents.SAND_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    public void setSecondsOnFire(int seconds) {

    }

    @Override
    public void setKnockback(int knockbackStrengthIn) {

    }

    @Override
    public void setPierceLevel(byte level) {

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // region FACTORY
    public static final ArrowItemCoFH.IArrowFactory<AbstractArrow> FACTORY = new ArrowItemCoFH.IArrowFactory<>() {

        @Override
        public AbstractArrow createArrow(Level world, LivingEntity living) {

            return new TrainingArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new TrainingArrow(world, posX, posY, posZ);
        }
    };
    // endregion
}
