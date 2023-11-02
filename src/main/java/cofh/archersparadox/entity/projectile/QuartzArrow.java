package cofh.archersparadox.entity.projectile;

import cofh.core.config.IBaseConfig;
import cofh.lib.item.ArrowItemCoFH;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

import static cofh.archersparadox.init.ModEntities.QUARTZ_ARROW;
import static cofh.archersparadox.init.ModItems.QUARTZ_ARROW_ITEM;

public class QuartzArrow extends AbstractArrow {

    public static float defaultDamage = 2.5F;
    public static int defaultKnockback = 1;
    public static byte defaultPierce = 0;

    public QuartzArrow(EntityType<QuartzArrow> type, Level worldIn) {

        super(type, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    public QuartzArrow(Level worldIn, LivingEntity shooter) {

        super(QUARTZ_ARROW.get(), shooter, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    public QuartzArrow(Level worldIn, double x, double y, double z) {

        super(QUARTZ_ARROW.get(), x, y, z, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(QUARTZ_ARROW_ITEM.get());
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // region FACTORY
    public static final ArrowItemCoFH.IArrowFactory<AbstractArrow> FACTORY = new ArrowItemCoFH.IArrowFactory<>() {

        @Override
        public AbstractArrow createArrow(Level world, LivingEntity living) {

            return new QuartzArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new QuartzArrow(world, posX, posY, posZ);
        }
    };
    // endregion

    // region CONFIG
    public static final IBaseConfig CONFIG = new IBaseConfig() {

        @Override
        public void apply(ForgeConfigSpec.Builder builder) {

            String name = "Quartz Arrow";

            builder.push(name);
            cfgDamage = builder
                    .comment("Adjust this to set the damage for the " + name + ". Vanilla Arrow value is 2.0.")
                    .defineInRange("Damage", defaultDamage, 0.0, 16.0);
            cfgKnockback = builder
                    .comment("Adjust this to set the inherent knockback strength of the " + name + ". Vanilla Arrow value is 0.")
                    .defineInRange("Knockback", defaultKnockback, 0, 16);
            cfgPierce = builder
                    .comment("Adjust this to set the inherent pierce of the " + name + ". Vanilla Arrow value is 0.")
                    .defineInRange("Piercing", defaultPierce, 0, 16);
            builder.pop();
        }

        @Override
        public void refresh() {

            defaultDamage = cfgDamage.get().floatValue();
            defaultKnockback = cfgKnockback.get();
            defaultPierce = cfgPierce.get().byteValue();
        }

        private Supplier<Double> cfgDamage;
        private Supplier<Integer> cfgKnockback;
        private Supplier<Integer> cfgPierce;
    };
    // endregion
}
