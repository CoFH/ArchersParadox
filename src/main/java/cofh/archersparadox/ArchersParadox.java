package cofh.archersparadox;

import cofh.archersparadox.client.renderer.entity.*;
import cofh.archersparadox.common.config.ModConfig;
import cofh.archersparadox.common.entity.projectile.*;
import cofh.archersparadox.init.registries.ModCreativeTabs;
import cofh.archersparadox.init.registries.ModEffects;
import cofh.archersparadox.init.registries.ModEntities;
import cofh.archersparadox.init.registries.ModItems;
import cofh.core.client.event.CoreClientEvents;
import cofh.core.common.config.ConfigManager;
import cofh.lib.util.DeferredRegisterCoFH;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cofh.archersparadox.init.registries.ModEntities.*;
import static cofh.lib.util.constants.ModIds.ID_ARCHERS_PARADOX;

@Mod (ID_ARCHERS_PARADOX)
public class ArchersParadox {

    public static final Logger LOG = LogManager.getLogger(ID_ARCHERS_PARADOX);
    public static final ConfigManager CONFIG_MANAGER = new ConfigManager();

    public static final DeferredRegisterCoFH<Item> ITEMS = DeferredRegisterCoFH.create(ForgeRegistries.ITEMS, ID_ARCHERS_PARADOX);
    public static final DeferredRegisterCoFH<CreativeModeTab> CREATIVE_TABS = DeferredRegisterCoFH.create(Registries.CREATIVE_MODE_TAB, ID_ARCHERS_PARADOX);

    public static final DeferredRegisterCoFH<MenuType<?>> CONTAINERS = DeferredRegisterCoFH.create(ForgeRegistries.MENU_TYPES, ID_ARCHERS_PARADOX);
    public static final DeferredRegisterCoFH<MobEffect> EFFECTS = DeferredRegisterCoFH.create(ForgeRegistries.MOB_EFFECTS, ID_ARCHERS_PARADOX);
    public static final DeferredRegisterCoFH<EntityType<?>> ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.ENTITY_TYPES, ID_ARCHERS_PARADOX);

    public ArchersParadox() {

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::entityRendererSetup);
        modEventBus.addListener(this::clientSetup);
        // modEventBus.addListener(this::creativeTabSetup);

        ITEMS.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);

        CONTAINERS.register(modEventBus);
        EFFECTS.register(modEventBus);
        ENTITIES.register(modEventBus);

        CONFIG_MANAGER.register(modEventBus)
                .addServerConfig(new ModConfig())
                .addServerConfig(ExplosiveArrow.CONFIG)
                .addServerConfig(QuartzArrow.CONFIG)
                .addServerConfig(DiamondArrow.CONFIG)
                .addServerConfig(LightningArrow.CONFIG)
                .addServerConfig(PrismarineArrow.CONFIG)
                .addServerConfig(SlimeArrow.CONFIG)
                .addServerConfig(ShulkerArrow.CONFIG)
                .addServerConfig(BlazeArrow.CONFIG)
                .addServerConfig(FrostArrow.CONFIG)
                .addServerConfig(VerdantArrow.CONFIG)
                .addServerConfig(SporeArrow.CONFIG);
        CONFIG_MANAGER.setupServer();

        ModItems.register();
        ModCreativeTabs.register();

        ModEffects.register();
        ModEntities.register();
    }

    // region INITIALIZATION
    private void entityRendererSetup(final EntityRenderersEvent.RegisterRenderers event) {

        event.registerEntityRenderer(EXPLOSIVE_ARROW.get(), ExplosiveArrowRenderer::new);
        event.registerEntityRenderer(QUARTZ_ARROW.get(), QuartzArrowRenderer::new);
        event.registerEntityRenderer(DIAMOND_ARROW.get(), DiamondArrowRenderer::new);
        event.registerEntityRenderer(PRISMARINE_ARROW.get(), PrismarineArrowRenderer::new);
        event.registerEntityRenderer(SLIME_ARROW.get(), SlimeArrowRenderer::new);
        event.registerEntityRenderer(ENDER_ARROW.get(), EnderArrowRenderer::new);
        event.registerEntityRenderer(TRAINING_ARROW.get(), TrainingArrowRenderer::new);
        event.registerEntityRenderer(CHALLENGE_ARROW.get(), ChallengeArrowRenderer::new);
        event.registerEntityRenderer(PHANTASMAL_ARROW.get(), PhantasmalArrowRenderer::new);
        event.registerEntityRenderer(SHULKER_ARROW.get(), ShulkerArrowRenderer::new);
        event.registerEntityRenderer(BLAZE_ARROW.get(), BlazeArrowRenderer::new);
        event.registerEntityRenderer(FROST_ARROW.get(), FrostArrowRenderer::new);
        event.registerEntityRenderer(LIGHTNING_ARROW.get(), LightningArrowRenderer::new);
        event.registerEntityRenderer(VERDANT_ARROW.get(), VerdantArrowRenderer::new);
        event.registerEntityRenderer(SPORE_ARROW.get(), SporeArrowRenderer::new);
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        event.enqueueWork(() -> CoreClientEvents.addNamespace(ID_ARCHERS_PARADOX));
    }

    private void creativeTabSetup(final BuildCreativeModeTabContentsEvent event) {

        if (event.getTabKey() == CreativeModeTabs.COMBAT) {

        }
    }
    // endregion
}
