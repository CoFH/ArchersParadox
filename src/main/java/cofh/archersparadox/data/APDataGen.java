package cofh.archersparadox.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.constants.ModIds.ID_ARCHERS_PARADOX;

@Mod.EventBusSubscriber (bus = Mod.EventBusSubscriber.Bus.MOD, modid = ID_ARCHERS_PARADOX)
public class APDataGen {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {

        DataGenerator gen = event.getGenerator();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        APTagsProvider.Block blockTags = new APTagsProvider.Block(gen, exFileHelper);

        gen.addProvider(event.includeServer(), blockTags);
        gen.addProvider(event.includeServer(), new APTagsProvider.Item(gen, blockTags, exFileHelper));

        gen.addProvider(event.includeServer(), new APRecipeProvider(gen));

        gen.addProvider(event.includeClient(), new APItemModelProvider(gen, exFileHelper));
    }

}
