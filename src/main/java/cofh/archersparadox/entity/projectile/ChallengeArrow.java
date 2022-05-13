package cofh.archersparadox.entity.projectile;

import cofh.lib.item.impl.ArrowItemCoFH;
import cofh.lib.util.Utils;
import net.minecraft.network.protocol.Packet;
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

import static cofh.archersparadox.init.APReferences.*;

public class ChallengeArrow extends AbstractArrow {

    private static final float defaultDamage = 0.0F;
    private static final int DISTANCE_FACTOR = 4;
    private static final int MAX_DISTANCE = 10;
    private static final int DURATION = 200;
    private static final int MISS_DURATION = 600;

    public boolean discharged;
    private Vec3 origin;

    public ChallengeArrow(EntityType<? extends ChallengeArrow> entityIn, Level worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
    }

    public ChallengeArrow(Level worldIn, LivingEntity shooter) {

        super(CHALLENGE_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = defaultDamage;
        this.origin = shooter.position();
    }

    public ChallengeArrow(Level worldIn, double x, double y, double z) {

        super(CHALLENGE_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = defaultDamage;
        this.origin = new Vec3(x, y, z);
    }

    @Override
    protected ItemStack getPickupItem() {

        return discharged ? new ItemStack(TRAINING_ARROW_ITEM) : new ItemStack(CHALLENGE_ARROW_ITEM);
    }

    @Override
    protected void onHit(HitResult raytraceResultIn) {

        if (raytraceResultIn.getType() == HitResult.Type.BLOCK) {
            if (getOwner() instanceof Player shooter && !Utils.isFakePlayer(getOwner())) {
                if (shooter.hasEffect(CHALLENGE_STREAK)) {
                    MobEffectInstance effect = shooter.getEffect(CHALLENGE_STREAK);
                    shooter.onEffectRemoved(effect);
                    shooter.removeEffectNoUpdate(CHALLENGE_STREAK);

                    shooter.addEffect(new MobEffectInstance(CHALLENGE_MISS, MISS_DURATION, 0, false, false));
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
                if (shooter != target && !shooter.hasEffect(CHALLENGE_MISS) && !shooter.hasEffect(CHALLENGE_COMPLETE)) {
                    int challengeCount = 0;
                    if (shooter.hasEffect(CHALLENGE_STREAK)) {
                        challengeCount = shooter.getEffect(CHALLENGE_STREAK).getAmplifier() + 1;
                    }
                    Vec3 originVec = origin == null ? shooter.position() : origin;
                    double distance = originVec.distanceTo(this.position());

                    if (distance >= Math.min(MAX_DISTANCE, challengeCount)) {
                        int distanceBonus = (int) (DISTANCE_FACTOR * distance);
                        shooter.addEffect(new MobEffectInstance(CHALLENGE_STREAK, DURATION + distanceBonus, challengeCount, false, false));
                        shooter.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.0F, Math.min(0.6F + 0.05F * challengeCount, 1.1F));
                        shooter.level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.NOTE_BLOCK_CHIME, shooter.getSoundSource(), 1.0F, Math.min(0.6F + 0.05F * challengeCount, 1.1F));
                        discharged = true;
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
    public Packet<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // region FACTORY
    public static final ArrowItemCoFH.IArrowFactory<AbstractArrow> FACTORY = new ArrowItemCoFH.IArrowFactory<>() {

        @Override
        public AbstractArrow createArrow(Level world, LivingEntity living) {

            return new ChallengeArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new ChallengeArrow(world, posX, posY, posZ);
        }
    };
    // endregion
}
