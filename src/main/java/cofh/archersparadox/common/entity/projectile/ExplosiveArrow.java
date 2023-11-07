package cofh.archersparadox.common.entity.projectile;

import cofh.core.common.config.IBaseConfig;
import cofh.lib.common.item.ArrowItemCoFH;
import cofh.lib.util.Utils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

import static cofh.archersparadox.init.registries.ModEntities.EXPLOSIVE_ARROW;
import static cofh.archersparadox.init.registries.ModItems.EXPLOSIVE_ARROW_ITEM;

public class ExplosiveArrow extends AbstractArrow {

    public static float defaultDamage = 0.5F;
    public static double explosionStrength = 1.9;
    public static boolean explosionsBreakBlocks = true;
    public static boolean explosionsCauseFire = true;
    public static boolean knockbackBoost = true;

    public ExplosiveArrow(EntityType<? extends ExplosiveArrow> entityIn, Level worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
    }

    public ExplosiveArrow(Level worldIn, LivingEntity shooter) {

        super(EXPLOSIVE_ARROW.get(), shooter, worldIn);
        this.baseDamage = defaultDamage;
    }

    public ExplosiveArrow(Level worldIn, double x, double y, double z) {

        super(EXPLOSIVE_ARROW.get(), x, y, z, worldIn);
        this.baseDamage = defaultDamage;
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(EXPLOSIVE_ARROW_ITEM.get());
    }

    @Override
    protected void onHit(HitResult raytraceResultIn) {

        super.onHit(raytraceResultIn);

        if (Utils.isServerWorld(level)) {
            level.explode(this, this.getX(), this.getY(), this.getZ(), (float) (explosionStrength + (knockbackBoost ? knockback : 0)), explosionsCauseFire && isOnFire(), explosionsBreakBlocks ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE);
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult raytraceResultIn) {

        super.onHitEntity(raytraceResultIn);

        Entity entity = raytraceResultIn.getEntity();
        entity.invulnerableTime = 0;
    }

    @Override
    public void setCritArrow(boolean critical) {

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

            return new ExplosiveArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new ExplosiveArrow(world, posX, posY, posZ);
        }
    };
    // endregion

    // region CONFIG
    public static final IBaseConfig CONFIG = new IBaseConfig() {

        @Override
        public void apply(ForgeConfigSpec.Builder builder) {

            String name = "Explosive Arrow";

            builder.push(name);
            cfgDamage = builder
                    .comment("Adjust this to set the damage for the " + name + ". Vanilla Arrow value is 2.0.")
                    .defineInRange("Damage", defaultDamage, 0.0, 16.0);
            cfgExplosiveStrength = builder
                    .comment("Adjust this to set the explosion strength for the " + name + ".")
                    .defineInRange("Strength", explosionStrength, 0.5D, 16.0D);
            cfgBreakBlocks = builder
                    .comment("If TRUE, explosions break blocks.")
                    .define("Break Blocks", explosionsBreakBlocks);
            cfgCauseFire = builder
                    .comment("If TRUE, explosions cause fires if the arrow is on fire.")
                    .define("Cause Fires", explosionsCauseFire);
            cfgKnockbackBoost = builder
                    .comment("If TRUE, bounces are modified by knockback bonuses, such as the Punch Enchantment.")
                    .define("Knockback Boost", knockbackBoost);
            builder.pop();
        }

        @Override
        public void refresh() {

            defaultDamage = cfgDamage.get().floatValue();
            explosionStrength = cfgExplosiveStrength.get();
            explosionsBreakBlocks = cfgBreakBlocks.get();
            explosionsCauseFire = cfgCauseFire.get();
            knockbackBoost = cfgKnockbackBoost.get();
        }

        private Supplier<Double> cfgDamage;
        private Supplier<Double> cfgExplosiveStrength;
        private Supplier<Boolean> cfgBreakBlocks;
        private Supplier<Boolean> cfgCauseFire;
        private Supplier<Boolean> cfgKnockbackBoost;
    };
    // endregion
}
