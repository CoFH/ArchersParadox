package cofh.archersparadox.data.providers;

import cofh.lib.data.ItemModelProviderCoFH;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.archersparadox.ArchersParadox.ITEMS;
import static cofh.archersparadox.init.ModIDs.*;
import static cofh.lib.util.constants.ModIds.ID_ARCHERS_PARADOX;

public class ModItemModelProvider extends ItemModelProviderCoFH {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {

        super(output, ID_ARCHERS_PARADOX, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        generated(ITEMS.getSup(ID_EXPLOSIVE_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_QUARTZ_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_DIAMOND_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_PRISMARINE_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_SLIME_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_ENDER_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_TRAINING_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_CHALLENGE_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_PHANTASMAL_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_SHULKER_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_BLAZE_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_FROST_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_LIGHTNING_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_VERDANT_ARROW), PROJECTILES);
        generated(ITEMS.getSup(ID_SPORE_ARROW), PROJECTILES);
    }

}
