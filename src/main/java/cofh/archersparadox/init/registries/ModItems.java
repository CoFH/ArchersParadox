package cofh.archersparadox.init.registries;

import cofh.archersparadox.common.entity.projectile.*;
import cofh.lib.common.item.ArrowItemCoFH;
import com.google.common.collect.Sets;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

import static cofh.archersparadox.ArchersParadox.ITEMS;
import static cofh.archersparadox.init.registries.ModIDs.*;
import static cofh.lib.util.Utils.itemProperties;

public class ModItems {

    private ModItems() {

    }

    public static LinkedHashSet<DeferredHolder<Item, Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    public static DeferredHolder<Item, Item> registerWithTab(final String name, final Supplier<Item> supplier) {

        DeferredHolder<Item, Item> reg = ITEMS.register(name, supplier);
        CREATIVE_TAB_ITEMS.add(reg);
        return reg;
    }

    public static void register() {

    }

    // ITEMS.register(ID_QUIVER, () -> new QuiverItem(properties().stacksTo(1), 5));
    // CONTAINERS.register(ID_QUIVER,()->IForgeMenuType.create((windowId,inv,data)->new QuiverContainer(windowId, inv, ProxyUtils.getClientPlayer())));

    public static final DeferredHolder<Item, Item> EXPLOSIVE_ARROW_ITEM = registerWithTab(ID_EXPLOSIVE_ARROW, () -> new ArrowItemCoFH(ExplosiveArrow.FACTORY, itemProperties()));
    public static final DeferredHolder<Item, Item> QUARTZ_ARROW_ITEM = registerWithTab(ID_QUARTZ_ARROW, () -> new ArrowItemCoFH(QuartzArrow.FACTORY, itemProperties()));
    public static final DeferredHolder<Item, Item> DIAMOND_ARROW_ITEM = registerWithTab(ID_DIAMOND_ARROW, () -> new ArrowItemCoFH(DiamondArrow.FACTORY, itemProperties()));
    public static final DeferredHolder<Item, Item> PRISMARINE_ARROW_ITEM = registerWithTab(ID_PRISMARINE_ARROW, () -> new ArrowItemCoFH(PrismarineArrow.FACTORY, itemProperties()));
    public static final DeferredHolder<Item, Item> SLIME_ARROW_ITEM = registerWithTab(ID_SLIME_ARROW, () -> new ArrowItemCoFH(SlimeArrow.FACTORY, itemProperties()));
    public static final DeferredHolder<Item, Item> ENDER_ARROW_ITEM = registerWithTab(ID_ENDER_ARROW, () -> new ArrowItemCoFH(EnderArrow.FACTORY, itemProperties()));
    public static final DeferredHolder<Item, Item> TRAINING_ARROW_ITEM = registerWithTab(ID_TRAINING_ARROW, () -> new ArrowItemCoFH(TrainingArrow.FACTORY, itemProperties()));
    public static final DeferredHolder<Item, Item> CHALLENGE_ARROW_ITEM = registerWithTab(ID_CHALLENGE_ARROW, () -> new ArrowItemCoFH(ChallengeArrow.FACTORY, itemProperties().rarity(Rarity.UNCOMMON)));
    public static final DeferredHolder<Item, Item> PHANTASMAL_ARROW_ITEM = registerWithTab(ID_PHANTASMAL_ARROW, () -> new ArrowItemCoFH(PhantasmalArrow.FACTORY, itemProperties().rarity(Rarity.UNCOMMON)));
    public static final DeferredHolder<Item, Item> SHULKER_ARROW_ITEM = registerWithTab(ID_SHULKER_ARROW, () -> new ArrowItemCoFH(ShulkerArrow.FACTORY, itemProperties().rarity(Rarity.UNCOMMON)));
    public static final DeferredHolder<Item, Item> BLAZE_ARROW_ITEM = registerWithTab(ID_BLAZE_ARROW, () -> new ArrowItemCoFH(BlazeArrow.FACTORY, itemProperties()));
    public static final DeferredHolder<Item, Item> FROST_ARROW_ITEM = registerWithTab(ID_FROST_ARROW, () -> new ArrowItemCoFH(FrostArrow.FACTORY, itemProperties()));
    public static final DeferredHolder<Item, Item> LIGHTNING_ARROW_ITEM = registerWithTab(ID_LIGHTNING_ARROW, () -> new ArrowItemCoFH(LightningArrow.FACTORY, itemProperties()));
    public static final DeferredHolder<Item, Item> VERDANT_ARROW_ITEM = registerWithTab(ID_VERDANT_ARROW, () -> new ArrowItemCoFH(VerdantArrow.FACTORY, itemProperties()));
    public static final DeferredHolder<Item, Item> SPORE_ARROW_ITEM = registerWithTab(ID_SPORE_ARROW, () -> new ArrowItemCoFH(SporeArrow.FACTORY, itemProperties()));

}
