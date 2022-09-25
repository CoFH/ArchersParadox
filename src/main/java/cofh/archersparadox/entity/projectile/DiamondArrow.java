package cofh.archersparadox.entity.projectile;

import cofh.lib.config.IBaseConfig;
import cofh.lib.item.ArrowItemCoFH;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.DIAMOND_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.DIAMOND_ARROW_ITEM;

public class DiamondArrow extends AbstractArrow {

    public static float defaultDamage = 4.0F;
    public static int defaultKnockback = 0;
    public static byte defaultPierce = 1;

    public DiamondArrow(EntityType<DiamondArrow> type, Level worldIn) {

        super(type, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    public DiamondArrow(Level worldIn, LivingEntity shooter) {

        super(DIAMOND_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    public DiamondArrow(Level worldIn, double x, double y, double z) {

        super(DIAMOND_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(DIAMOND_ARROW_ITEM);
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
    public Packet<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // region FACTORY
    public static final ArrowItemCoFH.IArrowFactory<AbstractArrow> FACTORY = new ArrowItemCoFH.IArrowFactory<>() {

        @Override
        public AbstractArrow createArrow(Level world, LivingEntity living) {

            return new DiamondArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new DiamondArrow(world, posX, posY, posZ);
        }
    };
    // endregion

    // region CONFIG
    public static final IBaseConfig CONFIG = new IBaseConfig() {

        @Override
        public void apply(ForgeConfigSpec.Builder builder) {

            String name = "Diamond Arrow";

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

        private ForgeConfigSpec.DoubleValue cfgDamage;
        private ForgeConfigSpec.IntValue cfgKnockback;
        private ForgeConfigSpec.IntValue cfgPierce;
    };
    // endregion
}
