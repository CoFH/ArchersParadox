package cofh.archersparadox.init.data;

import cofh.archersparadox.init.data.providers.ModItemModelProvider;
import cofh.archersparadox.init.data.providers.ModRecipeProvider;
import cofh.archersparadox.init.data.providers.ModTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.constants.ModIds.ID_ARCHERS_PARADOX;

@Mod.EventBusSubscriber (bus = Mod.EventBusSubscriber.Bus.MOD, modid = ID_ARCHERS_PARADOX)
public class ModDataGen {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {

        DataGenerator gen = event.getGenerator();
        PackOutput pOutput = gen.getPackOutput();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        ModTagsProvider.Block blockTags = new ModTagsProvider.Block(pOutput, event.getLookupProvider(), exFileHelper);
        gen.addProvider(event.includeServer(), blockTags);
        gen.addProvider(event.includeServer(), new ModTagsProvider.Item(pOutput, event.getLookupProvider(), blockTags.contentsGetter(), exFileHelper));
        gen.addProvider(event.includeServer(), new ModTagsProvider.Entity(pOutput, event.getLookupProvider(), exFileHelper));

        gen.addProvider(event.includeServer(), new ModRecipeProvider(pOutput));

        gen.addProvider(event.includeClient(), new ModItemModelProvider(pOutput, exFileHelper));
    }

}
