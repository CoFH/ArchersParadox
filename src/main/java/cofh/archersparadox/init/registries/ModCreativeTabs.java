package cofh.archersparadox.init.registries;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import static cofh.archersparadox.ArchersParadox.CREATIVE_TABS;
import static cofh.archersparadox.ArchersParadox.ITEMS;
import static cofh.archersparadox.init.registries.ModIDs.ID_PRISMARINE_ARROW;
import static cofh.lib.util.constants.ModIds.ID_ARCHERS_PARADOX;

public class ModCreativeTabs {

    private ModCreativeTabs() {

    }

    public static void register() {

    }

    private static final RegistryObject<CreativeModeTab> TAB = CREATIVE_TABS.register(ID_ARCHERS_PARADOX, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.archers_paradox"))
            .icon(() -> new ItemStack(ITEMS.get(ID_PRISMARINE_ARROW)))
            .displayItems((parameters, output) -> ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> output.accept(item.get())))
            .build());

}
