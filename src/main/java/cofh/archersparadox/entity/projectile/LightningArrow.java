package cofh.archersparadox.entity.projectile;

import cofh.lib.item.impl.ArrowItemCoFH;
import cofh.lib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.LIGHTNING_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.LIGHTNING_ARROW_ITEM;
import static cofh.lib.util.constants.NBTTags.TAG_ARROW_DATA;

public class LightningArrow extends AbstractArrow {

    public static float defaultDamage = 1.5F;

    public boolean discharged;

    public LightningArrow(EntityType<? extends LightningArrow> entityIn, Level worldIn) {

        super(entityIn, worldIn);
        this.baseDamage = defaultDamage;
    }

    public LightningArrow(Level worldIn, LivingEntity shooter) {

        super(LIGHTNING_ARROW_ENTITY, shooter, worldIn);
        this.baseDamage = defaultDamage;
    }

    public LightningArrow(Level worldIn, double x, double y, double z) {

        super(LIGHTNING_ARROW_ENTITY, x, y, z, worldIn);
        this.baseDamage = defaultDamage;
    }

    @Override
    protected ItemStack getPickupItem() {

        return discharged ? new ItemStack(Items.ARROW) : new ItemStack(LIGHTNING_ARROW_ITEM);
    }

    @Override
    protected void onHit(HitResult raytraceResultIn) {

        super.onHit(raytraceResultIn);

        if (Utils.isServerWorld(level) && !discharged && !isInWater() && !isInLava()) {
            BlockPos pos = this.blockPosition();
            if (this.level.canSeeSky(pos)) {
                Utils.spawnLightningBolt(level, pos, getOwner());
                discharged = true;
            }
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
    public Packet<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // region FACTORY
    public static final ArrowItemCoFH.IArrowFactory<AbstractArrow> FACTORY = new ArrowItemCoFH.IArrowFactory<>() {

        @Override
        public AbstractArrow createArrow(Level world, LivingEntity living) {

            return new LightningArrow(world, living);
        }

        @Override
        public AbstractArrow createArrow(Level world, double posX, double posY, double posZ) {

            return new LightningArrow(world, posX, posY, posZ);
        }
    };
    // endregion
}
