package cofh.archersparadox.item;

import cofh.archersparadox.inventory.container.QuiverContainer;
import cofh.core.item.InventoryContainerItem;
import cofh.core.util.ProxyUtils;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.inventory.SimpleItemInv;
import cofh.lib.item.IColorableItem;
import cofh.lib.util.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class QuiverItem extends InventoryContainerItem implements IColorableItem, IDyeableArmorItem, INamedContainerProvider {

    public QuiverItem(Properties builder, int slots) {

        super(builder, slots);

        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("color"), (stack, world, entity) -> (hasCustomColor(stack) ? 1F : 0));
        ProxyUtils.registerColorable(this);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack stack = playerIn.getItemInHand(handIn);
        return useDelegate(stack, playerIn, handIn) ? ActionResult.success(stack) : ActionResult.pass(stack);
    }

    // region HELPERS
    protected boolean useDelegate(ItemStack stack, PlayerEntity player, Hand hand) {

        if (Utils.isFakePlayer(player)) {
            return false;
        }
        if (player instanceof ServerPlayerEntity) {
            //            if (!canPlayerAccess(stack, player)) {
            //                ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslationTextComponent("info.cofh.secure_warning", SecurityHelper.getOwnerName(stack)));
            //                return false;
            //            } else if (SecurityHelper.attemptClaimItem(stack, player)) {
            //                ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslationTextComponent("info.cofh.secure_item"));
            //                return false;
            //            }
            NetworkHooks.openGui((ServerPlayerEntity) player, this);
        }
        return true;
    }

    @Override
    protected SimpleItemInv readInventoryFromNBT(ItemStack container) {

        CompoundNBT containerTag = getOrCreateInvTag(container);
        int numSlots = getContainerSlots(container);
        ArrayList<ItemStorageCoFH> invSlots = new ArrayList<>(numSlots);
        for (int i = 0; i < numSlots; ++i) {
            invSlots.add(new ItemStorageCoFH());
        }
        SimpleItemInv inventory = new SimpleItemInv(invSlots) {

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

                if (slot < 0 || slot >= getSlots()) {
                    return false;
                }
                return stack.getItem().is(ItemTags.ARROWS);
            }
        };
        inventory.read(containerTag);
        return inventory;
    }
    // endregion

    // region INamedContainerProvider
    @Override
    public ITextComponent getDisplayName() {

        return new TranslationTextComponent("item.archers_paradox.quiver");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new QuiverContainer(i, inventory, player);
    }
    // endregion

    //    @Override
    //    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
    //
    //        return new FluidContainerItemWrapper(stack, this);
    //    }
}
