package cofh.archersparadox.entity.projectile;

import cofh.lib.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.*;

public class TrainingArrowEntity extends AbstractArrowEntity {

    private static float defaultDamage = 0.0F;
    private static final int DISTANCE_FACTOR = 4;
    private static final int MAX_DISTANCE = 10;
    private static final int DURATION = 200;
    private static final int MIN_DURATION = 40;

    private Vector3d origin;

    public TrainingArrowEntity(EntityType<? extends TrainingArrowEntity> entityIn, World worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
    }

    public TrainingArrowEntity(World worldIn, LivingEntity shooter) {

        super(TRAINING_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = defaultDamage;
        this.origin = shooter.position();
    }

    public TrainingArrowEntity(World worldIn, double x, double y, double z) {

        super(TRAINING_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = defaultDamage;
        this.origin = new Vector3d(x, y, z);
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(TRAINING_ARROW_ITEM);
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {

        if (raytraceResultIn.getType() == RayTraceResult.Type.BLOCK) {
            if (getOwner() instanceof PlayerEntity && !Utils.isFakePlayer(getOwner())) {
                PlayerEntity shooter = (PlayerEntity) getOwner();
                if (shooter.hasEffect(TRAINING_STREAK)) {
                    EffectInstance effect = shooter.getEffect(TRAINING_STREAK);
                    shooter.onEffectRemoved(effect);
                    shooter.removeEffectNoUpdate(TRAINING_STREAK);

                    Vector3d originVec = origin == null ? shooter.position() : origin;
                    double distance = originVec.distanceTo(this.position());
                    int distanceBonus = (int) (DISTANCE_FACTOR * distance);

                    shooter.addEffect(new EffectInstance(TRAINING_MISS, Math.max(MIN_DURATION, DURATION - distanceBonus), 0, false, false));
                    shooter.level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.GLASS_BREAK, shooter.getSoundSource(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                }
            }
        }
        super.onHit(raytraceResultIn);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult raytraceResultIn) {

        Entity entity = raytraceResultIn.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) entity;

            if (getOwner() instanceof PlayerEntity && !Utils.isFakePlayer(getOwner())) {
                PlayerEntity shooter = (PlayerEntity) getOwner();
                if (shooter != target && !shooter.hasEffect(TRAINING_MISS)) {
                    int trainingCount = 0;
                    if (shooter.hasEffect(TRAINING_STREAK)) {
                        trainingCount = shooter.getEffect(TRAINING_STREAK).getAmplifier() + 1;
                    }
                    Vector3d originVec = origin == null ? shooter.position() : origin;
                    double distance = originVec.distanceTo(this.position());

                    if (distance >= Math.min(MAX_DISTANCE, trainingCount)) {
                        int distanceBonus = (int) (DISTANCE_FACTOR * distance);
                        shooter.addEffect(new EffectInstance(TRAINING_STREAK, DURATION + distanceBonus, trainingCount, false, false));
                        shooter.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.0F, Math.min(0.6F + 0.05F * trainingCount, 1.1F));
                        shooter.level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.NOTE_BLOCK_CHIME, shooter.getSoundSource(), 1.0F, Math.min(0.6F + 0.05F * trainingCount, 1.1F));
                    }
                }
            }
        }
        if (this.pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
            this.spawnAtLocation(this.getPickupItem(), 0.1F);
        }
        this.remove();
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
    public IPacket<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
