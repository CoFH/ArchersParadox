package cofh.archersparadox.event;

import cofh.archersparadox.config.ModConfig;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static cofh.archersparadox.ArchersParadox.ITEMS;
import static cofh.archersparadox.init.ModIDs.*;
import static cofh.core.util.helpers.ItemHelper.cloneStack;
import static cofh.lib.util.constants.ModIds.ID_ARCHERS_PARADOX;

@Mod.EventBusSubscriber (modid = ID_ARCHERS_PARADOX)
public class CommonSetupEvents {

    private CommonSetupEvents() {

    }

    @SubscribeEvent
    public static void setupVillagerTrades(final VillagerTradesEvent event) {

        if (!ModConfig.enableVillagerTrades.get()) {
            return;
        }
        if (event.getType() == VillagerProfession.FLETCHER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            List<VillagerTrades.ItemListing> noviceTrades = trades.get(1);
            noviceTrades.add(new BasicItemListing(cloneStack(Items.EMERALD, 1), cloneStack(Items.ARROW, 8), cloneStack(ITEMS.get(ID_TRAINING_ARROW), 8), 16, 1, 0.05F));

            List<VillagerTrades.ItemListing> apprenticeTrades = trades.get(2);
            apprenticeTrades.add(new BasicItemListing(cloneStack(Items.EMERALD, 1), cloneStack(Items.ARROW, 4), cloneStack(ITEMS.get(ID_PRISMARINE_ARROW), 4), 12, 5, 0.05F));
            apprenticeTrades.add(new BasicItemListing(cloneStack(Items.EMERALD, 1), cloneStack(Items.ARROW, 4), cloneStack(ITEMS.get(ID_QUARTZ_ARROW), 4), 12, 5, 0.05F));
            apprenticeTrades.add(new BasicItemListing(cloneStack(Items.EMERALD, 1), cloneStack(Items.ARROW, 4), cloneStack(ITEMS.get(ID_SLIME_ARROW), 4), 12, 5, 0.05F));

            List<VillagerTrades.ItemListing> journeymanTrades = trades.get(3);
            journeymanTrades.add(new BasicItemListing(cloneStack(Items.EMERALD, 3), cloneStack(Items.ARROW, 4), cloneStack(ITEMS.get(ID_BLAZE_ARROW), 4), 12, 10, 0.05F));
            journeymanTrades.add(new BasicItemListing(cloneStack(Items.EMERALD, 3), cloneStack(Items.ARROW, 4), cloneStack(ITEMS.get(ID_FROST_ARROW), 4), 12, 10, 0.05F));
            journeymanTrades.add(new BasicItemListing(cloneStack(Items.EMERALD, 3), cloneStack(Items.ARROW, 4), cloneStack(ITEMS.get(ID_LIGHTNING_ARROW), 4), 12, 10, 0.05F));

            List<VillagerTrades.ItemListing> expertTrades = trades.get(4);
            expertTrades.add(new BasicItemListing(cloneStack(Items.EMERALD, 4), cloneStack(Items.ARROW, 4), cloneStack(ITEMS.get(ID_CHALLENGE_ARROW), 4), 8, 15, 0.05F));

            List<VillagerTrades.ItemListing> masterTrades = trades.get(5);
            masterTrades.add(new BasicItemListing(cloneStack(Items.EMERALD, 4), cloneStack(Items.ARROW, 2), cloneStack(ITEMS.get(ID_PHANTASMAL_ARROW), 2), 8, 20, 0.05F));
        }
    }

    @SubscribeEvent
    public static void setupWandererTrades(final WandererTradesEvent event) {

        if (!ModConfig.enableWandererTrades.get()) {
            return;
        }
        event.getRareTrades().add(new BasicItemListing(cloneStack(Items.EMERALD, 4), cloneStack(ITEMS.get(ID_VERDANT_ARROW), 8), 8, 1, 0.05F));
        event.getRareTrades().add(new BasicItemListing(cloneStack(Items.EMERALD, 4), cloneStack(ITEMS.get(ID_SPORE_ARROW), 8), 8, 1, 0.05F));
    }

}
