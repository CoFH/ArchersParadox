package cofh.archersparadox.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.archersparadox.ArchersParadox.ITEMS;
import static cofh.archersparadox.init.APIDs.*;
import static cofh.lib.util.constants.ModIds.ID_ARCHERS_PARADOX;

public class APTagsProvider {

    public static class Block extends BlockTagsProvider {

        public Block(DataGenerator gen, ExistingFileHelper existingFileHelper) {

            super(gen, ID_ARCHERS_PARADOX, existingFileHelper);
        }

        @Override
        public String getName() {

            return "Archer's Paradox: Block Tags";
        }

        @Override
        protected void addTags() {

        }

    }

    public static class Item extends ItemTagsProvider {

        public Item(DataGenerator gen, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {

            super(gen, blockTagProvider, ID_ARCHERS_PARADOX, existingFileHelper);
        }

        @Override
        public String getName() {

            return "Archer's Paradox: Item Tags";
        }

        @Override
        protected void addTags() {

            tag(ItemTags.ARROWS).add(
                    ITEMS.get(ID_EXPLOSIVE_ARROW),
                    ITEMS.get(ID_QUARTZ_ARROW),
                    ITEMS.get(ID_DIAMOND_ARROW),
                    ITEMS.get(ID_PRISMARINE_ARROW),
                    ITEMS.get(ID_SLIME_ARROW),
                    ITEMS.get(ID_ENDER_ARROW),
                    ITEMS.get(ID_TRAINING_ARROW),
                    ITEMS.get(ID_CHALLENGE_ARROW),
                    ITEMS.get(ID_PHANTASMAL_ARROW),
                    ITEMS.get(ID_SHULKER_ARROW),
                    ITEMS.get(ID_BLAZE_ARROW),
                    ITEMS.get(ID_FROST_ARROW),
                    ITEMS.get(ID_LIGHTNING_ARROW),
                    ITEMS.get(ID_VERDANT_ARROW),
                    ITEMS.get(ID_SPORE_ARROW)
            );
        }

    }

}
