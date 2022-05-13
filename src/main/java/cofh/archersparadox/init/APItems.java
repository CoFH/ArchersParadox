package cofh.archersparadox.init;

import cofh.archersparadox.ArchersParadox;
import cofh.archersparadox.entity.projectile.*;
import cofh.archersparadox.inventory.container.QuiverContainer;
import cofh.core.util.ProxyUtils;
import cofh.lib.item.impl.ArrowItemCoFH;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.extensions.IForgeMenuType;

import static cofh.archersparadox.ArchersParadox.CONTAINERS;
import static cofh.archersparadox.ArchersParadox.ITEMS;
import static cofh.archersparadox.init.APIDs.*;

public class APItems {

    private APItems() {

    }

    public static void register() {

        CreativeModeTab group = CreativeModeTab.TAB_COMBAT;

        // ITEMS.register(ID_QUIVER, () -> new QuiverItem(new Item.Properties().stacksTo(1).tab(group), 5));
        CONTAINERS.register(ID_QUIVER, () -> IForgeMenuType.create((windowId, inv, data) -> new QuiverContainer(windowId, inv, ProxyUtils.getClientPlayer())));

        ITEMS.register(ID_EXPLOSIVE_ARROW, () -> new ArrowItemCoFH(ExplosiveArrow.FACTORY, new Item.Properties().tab(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_QUARTZ_ARROW, () -> new ArrowItemCoFH(QuartzArrow.FACTORY, new Item.Properties().tab(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_DIAMOND_ARROW, () -> new ArrowItemCoFH(DiamondArrow.FACTORY, new Item.Properties().tab(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_PRISMARINE_ARROW, () -> new ArrowItemCoFH(PrismarineArrow.FACTORY, new Item.Properties().tab(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_SLIME_ARROW, () -> new ArrowItemCoFH(SlimeArrow.FACTORY, new Item.Properties().tab(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_ENDER_ARROW, () -> new ArrowItemCoFH(EnderArrow.FACTORY, new Item.Properties().tab(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_TRAINING_ARROW, () -> new ArrowItemCoFH(TrainingArrow.FACTORY, new Item.Properties().tab(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_CHALLENGE_ARROW, () -> new ArrowItemCoFH(ChallengeArrow.FACTORY, new Item.Properties().tab(group).rarity(Rarity.UNCOMMON)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_PHANTASMAL_ARROW, () -> new ArrowItemCoFH(PhantasmalArrow.FACTORY, new Item.Properties().tab(group).rarity(Rarity.UNCOMMON)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_SHULKER_ARROW, () -> new ArrowItemCoFH(ShulkerArrow.FACTORY, new Item.Properties().tab(group).rarity(Rarity.UNCOMMON)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_BLAZE_ARROW, () -> new ArrowItemCoFH(BlazeArrow.FACTORY, new Item.Properties().tab(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_FROST_ARROW, () -> new ArrowItemCoFH(FrostArrow.FACTORY, new Item.Properties().tab(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_LIGHTNING_ARROW, () -> new ArrowItemCoFH(LightningArrow.FACTORY, new Item.Properties().tab(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_VERDANT_ARROW, () -> new ArrowItemCoFH(VerdantArrow.FACTORY, new Item.Properties().tab(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
        ITEMS.register(ID_SPORE_ARROW, () -> new ArrowItemCoFH(SporeArrow.FACTORY, new Item.Properties().tab(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    }

}
