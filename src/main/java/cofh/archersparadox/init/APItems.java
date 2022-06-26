package cofh.archersparadox.init;

import cofh.archersparadox.ArchersParadox;
import cofh.archersparadox.entity.projectile.*;
import cofh.lib.item.ArrowItemCoFH;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

import static cofh.archersparadox.ArchersParadox.ITEMS;
import static cofh.archersparadox.init.APIDs.*;

public class APItems {

    private APItems() {

    }

    public static void register() {

    }

    // ITEMS.register(ID_QUIVER, () -> new QuiverItem(new Item.Properties().stacksTo(1).tab(group), 5));
    // CONTAINERS.register(ID_QUIVER,()->IForgeMenuType.create((windowId,inv,data)->new QuiverContainer(windowId, inv, ProxyUtils.getClientPlayer())));

    public static final RegistryObject<Item> EXPLOSIVE_ARROW_ITEM = ITEMS.register(ID_EXPLOSIVE_ARROW, () -> new ArrowItemCoFH(ExplosiveArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> QUARTZ_ARROW_ITEM = ITEMS.register(ID_QUARTZ_ARROW, () -> new ArrowItemCoFH(QuartzArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> DIAMOND_ARROW_ITEM = ITEMS.register(ID_DIAMOND_ARROW, () -> new ArrowItemCoFH(DiamondArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> PRISMARINE_ARROW_ITEM = ITEMS.register(ID_PRISMARINE_ARROW, () -> new ArrowItemCoFH(PrismarineArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> SLIME_ARROW_ITEM = ITEMS.register(ID_SLIME_ARROW, () -> new ArrowItemCoFH(SlimeArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> ENDER_ARROW_ITEM = ITEMS.register(ID_ENDER_ARROW, () -> new ArrowItemCoFH(EnderArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> TRAINING_ARROW_ITEM = ITEMS.register(ID_TRAINING_ARROW, () -> new ArrowItemCoFH(TrainingArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> CHALLENGE_ARROW_ITEM = ITEMS.register(ID_CHALLENGE_ARROW, () -> new ArrowItemCoFH(ChallengeArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).rarity(Rarity.UNCOMMON)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> PHANTASMAL_ARROW_ITEM = ITEMS.register(ID_PHANTASMAL_ARROW, () -> new ArrowItemCoFH(PhantasmalArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).rarity(Rarity.UNCOMMON)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> SHULKER_ARROW_ITEM = ITEMS.register(ID_SHULKER_ARROW, () -> new ArrowItemCoFH(ShulkerArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).rarity(Rarity.UNCOMMON)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> BLAZE_ARROW_ITEM = ITEMS.register(ID_BLAZE_ARROW, () -> new ArrowItemCoFH(BlazeArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> FROST_ARROW_ITEM = ITEMS.register(ID_FROST_ARROW, () -> new ArrowItemCoFH(FrostArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> LIGHTNING_ARROW_ITEM = ITEMS.register(ID_LIGHTNING_ARROW, () -> new ArrowItemCoFH(LightningArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> VERDANT_ARROW_ITEM = ITEMS.register(ID_VERDANT_ARROW, () -> new ArrowItemCoFH(VerdantArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    public static final RegistryObject<Item> SPORE_ARROW_ITEM = ITEMS.register(ID_SPORE_ARROW, () -> new ArrowItemCoFH(SporeArrow.FACTORY, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setDisplayGroup(() -> ArchersParadox.itemGroup));

}
