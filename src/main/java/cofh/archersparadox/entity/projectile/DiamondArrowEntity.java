package cofh.archersparadox.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.DIAMOND_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.DIAMOND_ARROW_ITEM;

public class DiamondArrowEntity extends AbstractArrowEntity {

    public static float defaultDamage = 4.0F;
    public static int defaultKnockback = 0;
    public static byte defaultPierce = 1;

    public DiamondArrowEntity(EntityType<? extends DiamondArrowEntity> entityIn, World worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    public DiamondArrowEntity(World worldIn, LivingEntity shooter) {

        super(DIAMOND_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = defaultDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    public DiamondArrowEntity(World worldIn, double x, double y, double z) {

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
    public IPacket<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
