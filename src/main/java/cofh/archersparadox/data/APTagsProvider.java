package cofh.archersparadox.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.archersparadox.init.APReferences.*;
import static cofh.lib.util.constants.Constants.ID_ARCHERS_PARADOX;

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
        protected void registerTags() {

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
        protected void registerTags() {

            getOrCreateBuilder(ItemTags.ARROWS).add(
                    BLAZE_ARROW_ITEM,
                    CHALLENGE_ARROW_ITEM,
                    DIAMOND_ARROW_ITEM,
                    DISPLACEMENT_ARROW_ITEM,
                    ENDER_ARROW_ITEM,
                    EXPLOSIVE_ARROW_ITEM,
                    FROST_ARROW_ITEM,
                    GLOWSTONE_ARROW_ITEM,
                    LIGHTNING_ARROW_ITEM,
                    PHANTASMAL_ARROW_ITEM,
                    PRISMARINE_ARROW_ITEM,
                    QUARTZ_ARROW_ITEM,
                    REDSTONE_ARROW_ITEM,
                    SHULKER_ARROW_ITEM,
                    SLIME_ARROW_ITEM,
                    SPORE_ARROW_ITEM,
                    TRAINING_ARROW_ITEM,
                    VERDANT_ARROW_ITEM
            );
        }

    }

}
