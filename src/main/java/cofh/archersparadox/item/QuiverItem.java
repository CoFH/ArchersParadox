package cofh.archersparadox.item;

import cofh.archersparadox.inventory.container.QuiverContainer;
import cofh.core.item.InventoryContainerItem;
import cofh.core.util.ProxyUtils;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.inventory.SimpleItemInv;
import cofh.lib.item.IColorableItem;
import cofh.lib.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class QuiverItem extends InventoryContainerItem implements IColorableItem, DyeableLeatherItem, MenuProvider {

    public QuiverItem(Properties builder, int slots) {

        super(builder, slots);

        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("color"), (stack, world, entity, seed) -> (hasCustomColor(stack) ? 1F : 0));
        ProxyUtils.registerColorable(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {

        ItemStack stack = playerIn.getItemInHand(handIn);
        return useDelegate(stack, playerIn, handIn) ? InteractionResultHolder.success(stack) : InteractionResultHolder.pass(stack);
    }

    // region HELPERS
    protected boolean useDelegate(ItemStack stack, Player player, InteractionHand hand) {

        if (Utils.isFakePlayer(player)) {
            return false;
        }
        if (player instanceof ServerPlayer) {
            //            if (!canPlayerAccess(stack, player)) {
            //                ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslationTextComponent("info.cofh.secure_warning", SecurityHelper.getOwnerName(stack)));
            //                return false;
            //            } else if (SecurityHelper.attemptClaimItem(stack, player)) {
            //                ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslationTextComponent("info.cofh.secure_item"));
            //                return false;
            //            }
            NetworkHooks.openGui((ServerPlayer) player, this);
        }
        return true;
    }

    @Override
    protected SimpleItemInv readInventoryFromNBT(ItemStack container) {

        CompoundTag containerTag = getOrCreateInvTag(container);
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
                return stack.is(ItemTags.ARROWS);
            }
        };
        inventory.read(containerTag);
        return inventory;
    }
    // endregion

    // region INamedContainerProvider
    @Override
    public Component getDisplayName() {

        return new TranslatableComponent("item.archers_paradox.quiver");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new QuiverContainer(i, inventory, player);
    }
    // endregion

    //    @Override
    //    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
    //
    //        return new FluidContainerItemWrapper(stack, this);
    //    }
}
