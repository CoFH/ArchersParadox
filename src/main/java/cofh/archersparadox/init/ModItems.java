package cofh.archersparadox.init;

import cofh.archersparadox.entity.projectile.*;
import cofh.lib.item.ArrowItemCoFH;
import com.google.common.collect.Sets;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

import static cofh.archersparadox.ArchersParadox.ITEMS;
import static cofh.archersparadox.init.ModIDs.*;

public class ModItems {

    private ModItems() {

    }

    public static LinkedHashSet<RegistryObject<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    public static RegistryObject<Item> registerWithTab(final String name, final Supplier<Item> supplier) {

        RegistryObject<Item> reg = ITEMS.register(name, supplier);
        CREATIVE_TAB_ITEMS.add(reg);
        return reg;
    }

    public static void register() {

    }

    // ITEMS.register(ID_QUIVER, () -> new QuiverItem(new Item.Properties().stacksTo(1), 5));
    // CONTAINERS.register(ID_QUIVER,()->IForgeMenuType.create((windowId,inv,data)->new QuiverContainer(windowId, inv, ProxyUtils.getClientPlayer())));

    public static final RegistryObject<Item> EXPLOSIVE_ARROW_ITEM = registerWithTab(ID_EXPLOSIVE_ARROW, () -> new ArrowItemCoFH(ExplosiveArrow.FACTORY, new Item.Properties()));
    public static final RegistryObject<Item> QUARTZ_ARROW_ITEM = registerWithTab(ID_QUARTZ_ARROW, () -> new ArrowItemCoFH(QuartzArrow.FACTORY, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_ARROW_ITEM = registerWithTab(ID_DIAMOND_ARROW, () -> new ArrowItemCoFH(DiamondArrow.FACTORY, new Item.Properties()));
    public static final RegistryObject<Item> PRISMARINE_ARROW_ITEM = registerWithTab(ID_PRISMARINE_ARROW, () -> new ArrowItemCoFH(PrismarineArrow.FACTORY, new Item.Properties()));
    public static final RegistryObject<Item> SLIME_ARROW_ITEM = registerWithTab(ID_SLIME_ARROW, () -> new ArrowItemCoFH(SlimeArrow.FACTORY, new Item.Properties()));
    public static final RegistryObject<Item> ENDER_ARROW_ITEM = registerWithTab(ID_ENDER_ARROW, () -> new ArrowItemCoFH(EnderArrow.FACTORY, new Item.Properties()));
    public static final RegistryObject<Item> TRAINING_ARROW_ITEM = registerWithTab(ID_TRAINING_ARROW, () -> new ArrowItemCoFH(TrainingArrow.FACTORY, new Item.Properties()));
    public static final RegistryObject<Item> CHALLENGE_ARROW_ITEM = registerWithTab(ID_CHALLENGE_ARROW, () -> new ArrowItemCoFH(ChallengeArrow.FACTORY, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PHANTASMAL_ARROW_ITEM = registerWithTab(ID_PHANTASMAL_ARROW, () -> new ArrowItemCoFH(PhantasmalArrow.FACTORY, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SHULKER_ARROW_ITEM = registerWithTab(ID_SHULKER_ARROW, () -> new ArrowItemCoFH(ShulkerArrow.FACTORY, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BLAZE_ARROW_ITEM = registerWithTab(ID_BLAZE_ARROW, () -> new ArrowItemCoFH(BlazeArrow.FACTORY, new Item.Properties()));
    public static final RegistryObject<Item> FROST_ARROW_ITEM = registerWithTab(ID_FROST_ARROW, () -> new ArrowItemCoFH(FrostArrow.FACTORY, new Item.Properties()));
    public static final RegistryObject<Item> LIGHTNING_ARROW_ITEM = registerWithTab(ID_LIGHTNING_ARROW, () -> new ArrowItemCoFH(LightningArrow.FACTORY, new Item.Properties()));
    public static final RegistryObject<Item> VERDANT_ARROW_ITEM = registerWithTab(ID_VERDANT_ARROW, () -> new ArrowItemCoFH(VerdantArrow.FACTORY, new Item.Properties()));
    public static final RegistryObject<Item> SPORE_ARROW_ITEM = registerWithTab(ID_SPORE_ARROW, () -> new ArrowItemCoFH(SporeArrow.FACTORY, new Item.Properties()));

}
