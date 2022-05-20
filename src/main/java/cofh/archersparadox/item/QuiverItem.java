package cofh.archersparadox.item;

import cofh.archersparadox.inventory.container.QuiverContainer;
import cofh.core.item.InventoryContainerItem;
import cofh.core.util.helpers.ChatHelper;
import cofh.lib.capability.CapabilityArchery;
import cofh.lib.capability.IArcheryAmmoItem;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.inventory.SimpleItemInv;
import cofh.lib.item.IColorableItem;
import cofh.lib.item.IMultiModeItem;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.SecurityHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class QuiverItem extends InventoryContainerItem implements IColorableItem, IMultiModeItem, DyeableLeatherItem, MenuProvider {

    protected Supplier<CreativeModeTab> displayGroup;

    public QuiverItem(Properties builder, int slots) {

        super(builder, slots);

        //        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("color"), (stack, world, entity, seed) -> (hasCustomColor(stack) ? 1F : 0));
        //        ProxyUtils.registerColorable(this);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {

        if (!showInGroups.getAsBoolean() || displayGroup != null && displayGroup.get() != null && displayGroup.get() != group) {
            return;
        }
        super.fillItemCategory(group, items);
    }

    @Override
    public Collection<CreativeModeTab> getCreativeTabs() {

        return displayGroup != null && displayGroup.get() != null ? Collections.singletonList(displayGroup.get()) : super.getCreativeTabs();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {

        ItemStack stack = playerIn.getItemInHand(handIn);
        return useDelegate(stack, playerIn, handIn) ? InteractionResultHolder.success(stack) : InteractionResultHolder.pass(stack);
    }

    // region HELPERS
    protected boolean useDelegate(ItemStack stack, Player player, InteractionHand hand) {

        if (Utils.isFakePlayer(player) || hand == InteractionHand.OFF_HAND) {
            return false;
        }
        if (player instanceof ServerPlayer) {
            if (!canPlayerAccess(stack, player)) {
                ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslatableComponent("info.cofh.secure_warning", SecurityHelper.getOwnerName(stack)));
                return false;
            } else if (SecurityHelper.attemptClaimItem(stack, player)) {
                ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslatableComponent("info.cofh.secure_item"));
                return false;
            }
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

    // region MenuProvider
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

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {

        return new QuiverItemWrapper(stack, this);
    }

    // region IMultiModeItem
    @Override
    public int getNumModes(ItemStack stack) {

        return slots;
    }
    // endregion

    // region CAPABILITY WRAPPER
    protected class QuiverItemWrapper implements ICapabilityProvider, IArcheryAmmoItem {

        private final LazyOptional<IArcheryAmmoItem> holder = LazyOptional.of(() -> this);

        protected final ItemStack container;
        protected final QuiverItem item;

        public QuiverItemWrapper(ItemStack containerIn, QuiverItem itemIn) {

            this.container = containerIn;
            this.item = itemIn;
        }

        protected ItemStack getSelectedArrow() {

            return item.getContainerInventory(container).get(item.getMode(container));
        }

        @Override
        public void onArrowLoosed(Player shooter) {

            ItemStack arrow = getSelectedArrow();
            arrow.shrink(1);
            if (arrow.isEmpty()) {
                arrow = ItemStack.EMPTY;
            }
            item.getContainerInventory(container).set(getMode(container), arrow);
        }

        @Override
        public AbstractArrow createArrowEntity(Level world, Player shooter) {

            return ((ArrowItem) getSelectedArrow().getItem()).createArrow(world, getSelectedArrow(), shooter);
        }

        @Override
        public boolean isEmpty(Player shooter) {

            return getSelectedArrow().isEmpty();
        }

        @Override
        public boolean isInfinite(ItemStack bow, Player shooter) {

            return ((ArrowItem) getSelectedArrow().getItem()).isInfinite(getSelectedArrow(), bow, shooter);
        }

        // region ICapabilityProvider
        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

            return CapabilityArchery.AMMO_ITEM_CAPABILITY.orEmpty(cap, holder);
        }
        // endregion
    }
    // endregion
}
