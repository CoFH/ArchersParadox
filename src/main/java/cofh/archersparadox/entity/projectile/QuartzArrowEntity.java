package cofh.archersparadox.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.QUARTZ_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.QUARTZ_ARROW_ITEM;

public class QuartzArrowEntity extends AbstractArrowEntity {

    public static float baseDamage = 2.5F;
    public static int baseKnockback = 1;
    public static byte basePierce = 0;

    public QuartzArrowEntity(EntityType<? extends QuartzArrowEntity> entityIn, World worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = baseDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    public QuartzArrowEntity(World worldIn, LivingEntity shooter) {

        super(QUARTZ_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = baseDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    public QuartzArrowEntity(World worldIn, double x, double y, double z) {

        super(QUARTZ_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = baseDamage;
        setKnockback(0);
        setPierceLevel((byte) 0);
    }

    @Override
    protected ItemStack getPickupItem() {

        return new ItemStack(QUARTZ_ARROW_ITEM);
    }

    @Override
    public void setKnockback(int knockbackStrengthIn) {

        super.setKnockback(baseKnockback + knockbackStrengthIn);
    }

    @Override
    public void setPierceLevel(byte level) {

        super.setPierceLevel((byte) (basePierce + level));
    }

    @Override
    public IPacket<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
