package cofh.archersparadox;

import cofh.archersparadox.client.gui.QuiverScreen;
import cofh.archersparadox.client.renderer.entity.*;
import cofh.archersparadox.config.APClientConfig;
import cofh.archersparadox.entity.projectile.*;
import cofh.archersparadox.init.APEntities;
import cofh.archersparadox.init.APItems;
import cofh.lib.config.ConfigManager;
import cofh.lib.util.DeferredRegisterCoFH;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cofh.archersparadox.init.APIDs.ID_PRISMARINE_ARROW;
import static cofh.archersparadox.init.APReferences.*;
import static cofh.lib.util.constants.Constants.ID_ARCHERS_PARADOX;

@Mod (ID_ARCHERS_PARADOX)
public class ArchersParadox {

    public static final Logger LOG = LogManager.getLogger(ID_ARCHERS_PARADOX);
    public static final ConfigManager CONFIG_MANAGER = new ConfigManager();

    public static final DeferredRegisterCoFH<Item> ITEMS = DeferredRegisterCoFH.create(ForgeRegistries.ITEMS, ID_ARCHERS_PARADOX);

    public static final DeferredRegisterCoFH<MenuType<?>> CONTAINERS = DeferredRegisterCoFH.create(ForgeRegistries.CONTAINERS, ID_ARCHERS_PARADOX);
    public static final DeferredRegisterCoFH<MobEffect> EFFECTS = DeferredRegisterCoFH.create(ForgeRegistries.MOB_EFFECTS, ID_ARCHERS_PARADOX);
    public static final DeferredRegisterCoFH<EntityType<?>> ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.ENTITIES, ID_ARCHERS_PARADOX).preventDataFixers(true);

    public static CreativeModeTab itemGroup;

    public ArchersParadox() {

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::entityRendererSetup);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        ITEMS.register(modEventBus);

        CONTAINERS.register(modEventBus);
        EFFECTS.register(modEventBus);
        ENTITIES.register(modEventBus);

        CONFIG_MANAGER.register(modEventBus)
                .addClientConfig(new APClientConfig())
                .addServerConfig(ExplosiveArrow.CONFIG)
                .addServerConfig(QuartzArrow.CONFIG)
                .addServerConfig(DiamondArrow.CONFIG)
                .addServerConfig(PrismarineArrow.CONFIG)
                .addServerConfig(SlimeArrow.CONFIG);
        CONFIG_MANAGER.setupClient();

        // APConfig.register();

        APItems.register();

        // APEffects.register();
        APEntities.register();

        // setFlag(FLAG_ECTOPLASM, true);
    }

    // region INITIALIZATION
    private void entityRendererSetup(final EntityRenderersEvent.RegisterRenderers event) {

        event.registerEntityRenderer(EXPLOSIVE_ARROW_ENTITY, ExplosiveArrowRenderer::new);
        event.registerEntityRenderer(QUARTZ_ARROW_ENTITY, QuartzArrowRenderer::new);
        event.registerEntityRenderer(DIAMOND_ARROW_ENTITY, DiamondArrowRenderer::new);
        event.registerEntityRenderer(PRISMARINE_ARROW_ENTITY, PrismarineArrowRenderer::new);
        event.registerEntityRenderer(SLIME_ARROW_ENTITY, SlimeArrowRenderer::new);
        event.registerEntityRenderer(ENDER_ARROW_ENTITY, EnderArrowRenderer::new);
        event.registerEntityRenderer(PHANTASMAL_ARROW_ENTITY, PhantasmalArrowRenderer::new);
        event.registerEntityRenderer(SHULKER_ARROW_ENTITY, ShulkerArrowRenderer::new);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        CONFIG_MANAGER.setupServer();
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        if (APClientConfig.enableCreativeTab.get()) {
            itemGroup = new CreativeModeTab(-1, ID_ARCHERS_PARADOX) {

                @Override
                @OnlyIn (Dist.CLIENT)
                public ItemStack makeIcon() {

                    return new ItemStack(ITEMS.get(ID_PRISMARINE_ARROW));
                }
            };
        } else {
            itemGroup = CreativeModeTab.TAB_COMBAT;
        }
        MenuScreens.register(QUIVER_CONTAINER, QuiverScreen::new);
    }
    // endregion
}
