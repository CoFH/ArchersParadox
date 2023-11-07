//package cofh.archersparadox.inventory.container;
//
//import cofh.core.inventory.container.ContainerCoFH;
//import cofh.lib.api.item.IInventoryContainerItem;
//import cofh.lib.inventory.SimpleItemInv;
//import cofh.lib.inventory.container.slot.SlotCoFH;
//import cofh.lib.inventory.container.slot.SlotLocked;
//import cofh.lib.inventory.wrapper.InvWrapperCoFH;
//import cofh.lib.util.helpers.MathHelper;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.inventory.Slot;
//import net.minecraft.world.item.ItemStack;
//
//import static cofh.archersparadox.init.APReferences.QUIVER_CONTAINER;
//
//public class QuiverContainer extends ContainerCoFH {
//
//    public static final int MAX_SLOTS = 54;
//    public static final int MAX_ROWS = MAX_SLOTS / 9;
//
//    protected final IInventoryContainerItem containerItem;
//    protected SimpleItemInv itemInventory;
//    protected InvWrapperCoFH invWrapper;
//    protected ItemStack containerStack;
//
//    public SlotLocked lockedSlot;
//
//    protected int numSlots;
//    protected int rows;
//
//    public QuiverContainer(int windowId, Inventory inventory, Player player) {
//
//        super(QUIVER_CONTAINER.get(), windowId, inventory, player);
//
//        allowSwap = false;
//
//        containerStack = player.getMainHandItem();
//        containerItem = (IInventoryContainerItem) containerStack.getItem();
//
//        numSlots = MathHelper.clamp(containerItem.getContainerSlots(containerStack), 0, MAX_SLOTS);
//        itemInventory = containerItem.getContainerInventory(containerStack);
//        invWrapper = new InvWrapperCoFH(itemInventory, numSlots);
//
//        rows = MathHelper.clamp(numSlots / 9, 0, MAX_ROWS);
//        int extraSlots = numSlots % 9;
//
//        int xOffset = 8;
//        int yOffset = 44 - 9 * MathHelper.clamp(rows + (extraSlots > 0 ? 1 : 0), 0, 3);
//
//        for (int i = 0; i < rows * 9; ++i) {
//            addSlot(new SlotCoFH(invWrapper, i, xOffset + i % 9 * 18, yOffset + i / 9 * 18));
//        }
//        if (extraSlots > 0) {
//            xOffset = 89 - 9 * extraSlots;
//            for (int i = numSlots - extraSlots; i < numSlots; ++i) {
//                addSlot(new SlotCoFH(invWrapper, i, xOffset + i % extraSlots * 18, yOffset + 18 * rows));
//            }
//        }
//        bindPlayerInventory(inventory);
//    }
//
//    @Override
//    protected void bindPlayerInventory(Inventory inventory) {
//
//        int xOffset = getPlayerInventoryHorizontalOffset();
//        int yOffset = getPlayerInventoryVerticalOffset();
//
//        for (int i = 0; i < 3; ++i) {
//            for (int j = 0; j < 9; ++j) {
//                addSlot(new Slot(inventory, j + i * 9 + 9, xOffset + j * 18, yOffset + i * 18));
//            }
//        }
//        for (int i = 0; i < 9; ++i) {
//            if (i == inventory.selected) {
//                lockedSlot = new SlotLocked(inventory, i, xOffset + i * 18, yOffset + 58);
//                addSlot(lockedSlot);
//            } else {
//                addSlot(new Slot(inventory, i, xOffset + i * 18, yOffset + 58));
//            }
//        }
//    }
//
//    @Override
//    protected int getPlayerInventoryVerticalOffset() {
//
//        return 84 + getExtraRows() * 18;
//    }
//
//    public int getExtraRows() {
//
//        return MathHelper.clamp((rows + (numSlots % 9 > 0 ? 1 : 0)) - 3, 0, (MAX_ROWS - 3));
//    }
//
//    public int getContainerInventorySize() {
//
//        return numSlots;
//    }
//
//    @Override
//    protected int getMergeableSlotCount() {
//
//        return invWrapper.getContainerSize();
//    }
//
//    @Override
//    public boolean stillValid(Player playerIn) {
//
//        return true;
//    }
//
//    @Override
//    public void removed(Player playerIn) {
//
//        itemInventory.write(containerItem.getOrCreateInvTag(containerStack));
//        containerItem.onContainerInventoryChanged(containerStack);
//        super.removed(playerIn);
//    }
//
//}
