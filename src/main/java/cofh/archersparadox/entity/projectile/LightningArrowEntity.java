package cofh.archersparadox.entity.projectile;

import cofh.lib.util.Utils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import static cofh.archersparadox.init.APReferences.LIGHTNING_ARROW_ENTITY;
import static cofh.archersparadox.init.APReferences.LIGHTNING_ARROW_ITEM;
import static cofh.lib.util.constants.NBTTags.TAG_ARROW_DATA;

public class LightningArrowEntity extends AbstractArrowEntity {

    public static float baseDamage = 1.5F;

    public boolean discharged;

    public LightningArrowEntity(EntityType<? extends LightningArrowEntity> entityIn, World worldIn) {

        super(entityIn, worldIn);
        this.damage = baseDamage;
    }

    public LightningArrowEntity(World worldIn, LivingEntity shooter) {

        super(LIGHTNING_ARROW_ENTITY, shooter, worldIn);
        this.damage = baseDamage;
    }

    public LightningArrowEntity(World worldIn, double x, double y, double z) {

        super(LIGHTNING_ARROW_ENTITY, x, y, z, worldIn);
        this.damage = baseDamage;
    }

    @Override
    protected ItemStack getArrowStack() {

        return discharged ? new ItemStack(Items.ARROW) : new ItemStack(LIGHTNING_ARROW_ITEM);
    }

    @Override
    protected void onImpact(RayTraceResult raytraceResultIn) {

        super.onImpact(raytraceResultIn);

        if (Utils.isServerWorld(world) && !discharged && !isInWater() && !isInLava()) {
            BlockPos pos = this.getPosition();
            if (this.world.canSeeSky(pos)) {
                Utils.spawnLightningBolt(world, pos, func_234616_v_());
                discharged = true;
            }
        }

        //        if (!discharged && raytraceResultIn.getType() != RayTraceResult.Type.MISS) {
        //            if (!isInWater() && !isInLava()) {
        //                BlockPos pos = this.getPosition();
        //                if (this.world.canSeeSky(pos)) {
        //                    Utils.spawnLightningBolt(world, pos, func_234616_v_());
        //                    discharged = true;
        //                }
        //            }
        //        }
    }

    @Override
    public void setIsCritical(boolean critical) {

    }

    @Override
    public void setKnockbackStrength(int knockbackStrengthIn) {

    }

    @Override
    public void setPierceLevel(byte level) {

    }

    @Override
    public void writeAdditional(CompoundNBT compound) {

        super.writeAdditional(compound);
        compound.putBoolean(TAG_ARROW_DATA, discharged);

    }

    @Override
    public void readAdditional(CompoundNBT compound) {

        super.readAdditional(compound);
        discharged = compound.getBoolean(TAG_ARROW_DATA);
    }

    @Override
    public IPacket<?> createSpawnPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
