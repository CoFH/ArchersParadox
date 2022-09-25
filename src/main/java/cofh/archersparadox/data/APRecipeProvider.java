package cofh.archersparadox.data;

import cofh.lib.data.RecipeProviderCoFH;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static cofh.archersparadox.ArchersParadox.ITEMS;
import static cofh.archersparadox.init.APIDs.*;
import static cofh.lib.util.constants.ModIds.ID_ARCHERS_PARADOX;

public class APRecipeProvider extends RecipeProviderCoFH {

    public APRecipeProvider(DataGenerator generatorIn) {

        super(generatorIn, ID_ARCHERS_PARADOX);
    }

    @Override
    public String getName() {

        return "Archer's Paradox: Recipes";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_EXPLOSIVE_ARROW), 4)
                .define('X', Items.TNT)
                .define('#', Items.ARROW)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_components", has(Items.TNT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_QUARTZ_ARROW), 4)
                .define('X', Tags.Items.GEMS_QUARTZ)
                .define('#', Items.STICK)
                .define('Y', Items.FEATHER)
                .pattern("X")
                .pattern("#")
                .pattern("Y")
                .unlockedBy("has_components", has(Tags.Items.GEMS_QUARTZ))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_DIAMOND_ARROW), 4)
                .define('X', Tags.Items.GEMS_DIAMOND)
                .define('#', Items.STICK)
                .define('Y', Items.FEATHER)
                .pattern("X")
                .pattern("#")
                .pattern("Y")
                .unlockedBy("has_components", has(Tags.Items.GEMS_DIAMOND))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_PRISMARINE_ARROW), 4)
                .define('X', Items.PRISMARINE_SHARD)
                .define('#', Items.STICK)
                .define('Y', Items.FEATHER)
                .pattern("X")
                .pattern("#")
                .pattern("Y")
                .unlockedBy("has_components", has(Tags.Items.GEMS_PRISMARINE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_SLIME_ARROW), 4)
                .define('X', Tags.Items.SLIMEBALLS)
                .define('#', Items.ARROW)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_components", has(Tags.Items.SLIMEBALLS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_ENDER_ARROW), 4)
                .define('X', Items.ENDER_PEARL)
                .define('#', Items.ARROW)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_components", has(Items.ENDER_PEARL))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_TRAINING_ARROW), 4)
                .define('X', Items.RABBIT_HIDE)
                .define('#', Items.STICK)
                .define('Y', Items.FEATHER)
                .pattern("X")
                .pattern("#")
                .pattern("Y")
                .unlockedBy("has_components", has(Items.RABBIT_HIDE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_CHALLENGE_ARROW), 4)
                .define('X', Items.EXPERIENCE_BOTTLE)
                .define('Y', Tags.Items.GEMS_EMERALD)
                .define('Z', Tags.Items.GEMS_LAPIS)
                .define('#', ITEMS.get(ID_TRAINING_ARROW))
                .pattern("Y#Z")
                .pattern("#X#")
                .pattern("Z#Y")
                .unlockedBy("has_components", has(Items.EXPERIENCE_BOTTLE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_PHANTASMAL_ARROW), 4)
                .define('X', Items.GHAST_TEAR)
                .define('#', Items.ARROW)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_components", has(Items.GHAST_TEAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_SHULKER_ARROW), 4)
                .define('X', Items.ENDER_EYE)
                .define('Y', Items.END_STONE)
                .define('Z', Items.POPPED_CHORUS_FRUIT)
                .define('#', Items.ARROW)
                .pattern("Y#Z")
                .pattern("#X#")
                .pattern("Z#Y")
                .unlockedBy("has_components", has(Items.ENDER_EYE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_BLAZE_ARROW), 4)
                .define('X', Items.BLAZE_POWDER)
                .define('#', Items.ARROW)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_components", has(Items.BLAZE_POWDER))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_FROST_ARROW), 4)
                .define('X', Items.PACKED_ICE)
                .define('#', Items.ARROW)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_components", has(Items.PACKED_ICE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_LIGHTNING_ARROW), 4)
                .define('X', Items.LIGHTNING_ROD)
                .define('#', Items.ARROW)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_components", has(Items.NETHER_STAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_VERDANT_ARROW), 4)
                .define('X', Items.GRASS_BLOCK)
                .define('Y', Items.BONE_MEAL)
                .define('Z', ItemTags.SMALL_FLOWERS)
                .define('#', Items.ARROW)
                .pattern("Y#Z")
                .pattern("#X#")
                .pattern("Z#Y")
                .unlockedBy("has_components", has(Items.GRASS_BLOCK))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ITEMS.get(ID_SPORE_ARROW), 4)
                .define('X', Items.MYCELIUM)
                .define('Y', Items.BONE_MEAL)
                .define('Z', Tags.Items.MUSHROOMS)
                .define('#', Items.ARROW)
                .pattern("Y#Z")
                .pattern("#X#")
                .pattern("Z#Y")
                .unlockedBy("has_components", has(Items.MYCELIUM))
                .save(consumer);
    }

}
