package cofh.archersparadox.init;

import cofh.archersparadox.entity.projectile.*;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class APConfig {

    private static boolean registered = false;

    public static void register() {

        if (registered) {
            return;
        }
        FMLJavaModLoadingContext.get().getModEventBus().register(APConfig.class);
        registered = true;

        genServerConfig();
        genClientConfig();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, serverSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, clientSpec);
    }

    private APConfig() {

    }

    // region CONFIG SPEC
    private static final Builder SERVER_CONFIG = new Builder();
    private static ForgeConfigSpec serverSpec;

    private static final Builder CLIENT_CONFIG = new Builder();
    private static ForgeConfigSpec clientSpec;

    private static void genServerConfig() {

        enableFletcherTrades = SERVER_CONFIG
                .comment("If TRUE, trades will be added to Fletcher Villagers.")
                .define("Enable Fletcher Trades", true);

        genArrowConfig();

        serverSpec = SERVER_CONFIG.build();

        refreshServerConfig();
    }

    private static void genClientConfig() {

        enableCreativeTab = CLIENT_CONFIG
                .comment("If TRUE, Archer's Paradox will have its own Item Group (Creative Tab).")
                .define("Enable Item Group", true);

        clientSpec = CLIENT_CONFIG.build();

        refreshClientConfig();
    }

    private static void genArrowConfig() {

        SERVER_CONFIG.push("Arrows");

        SERVER_CONFIG.push("Blaze");
        blazeArrowDuration = SERVER_CONFIG
                .comment("Adjust this to set the burn duration for the Blaze Arrow (in seconds). Nearby targets will burn for 5 seconds less than a direct target.")
                .defineInRange("Burn Duration", BlazeArrowEntity.effectDuration, 5, 30);
        blazeArrowRadius = SERVER_CONFIG
                .comment("Adjust this to set the effect radius for the Blaze Arrow. Set to 0 to disable, but that would be boring.")
                .defineInRange("Radius", BlazeArrowEntity.effectRadius, 0, 16);
        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Diamond");
        diamondArrowDamage = SERVER_CONFIG
                .comment("Adjust this to set the damage for the Diamond Arrow. Default Arrow value is 2.0.")
                .defineInRange("Damage", DiamondArrowEntity.defaultDamage, 0.5, 16.0);
        diamondArrowKnockback = SERVER_CONFIG
                .comment("Adjust this to set the inherent knockback strength of the Diamond Arrow. Default Arrow value is 0.")
                .defineInRange("Knockback", DiamondArrowEntity.defaultKnockback, 0, 16);
        diamondArrowPierce = SERVER_CONFIG
                .comment("Adjust this to set the inherent pierce of the Diamond Arrow. Default Arrow value is 0.")
                .defineInRange("Piercing", DiamondArrowEntity.defaultPierce, 0, 16);
        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Displacement");
        displacementArrowRadius = SERVER_CONFIG
                .comment("Adjust this to set the effect radius for the Displacement Arrow. Set to 0 to disable, but that would be boring (and make the arrow useless).")
                .defineInRange("Radius", DisplacementArrowEntity.effectRadius, 0, 16);
        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Explosive");
        explosiveArrowStrength = SERVER_CONFIG
                .comment("Adjust this to set the explosion strength for the Explosive Arrow.")
                .defineInRange("Strength", ExplosiveArrowEntity.explosionStrength, 0.5D, 16.0D);
        explosiveArrowBreakBlocks = SERVER_CONFIG
                .comment("If TRUE, explosions break blocks.")
                .define("Break Blocks", ExplosiveArrowEntity.explosionsBreakBlocks);
        explosiveArrowCauseFire = SERVER_CONFIG
                .comment("If TRUE, explosions cause fires if the arrow is on fire.")
                .define("Cause Fires", ExplosiveArrowEntity.explosionsCauseFire);
        explosiveArrowKnockbackBoost = SERVER_CONFIG
                .comment("If TRUE, explosion strength is modified by knockback bonuses, such as the Punch Enchantment.")
                .define("Knockback Boost", ExplosiveArrowEntity.knockbackBoost);
        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Frost");
        frostArrowRadius = SERVER_CONFIG
                .comment("Adjust this to set the effect radius for the Frost Arrow. Set to 0 to disable, but that would be boring.")
                .defineInRange("Radius", FrostArrowEntity.effectRadius, 0, 16);
        frostArrowPermanentLava = SERVER_CONFIG
                .comment("If TRUE, Frost Arrows will convert Lava into Obsidian. If FALSE, Glossed Magma.")
                .define("Permanent Lava Freeze", FrostArrowEntity.permanentLava);
        frostArrowPermanentWater = SERVER_CONFIG
                .comment("If TRUE, Frost Arrows will convert Water into Ice. If FALSE, Frosted Ice.")
                .define("Permanent Water Freeze", FrostArrowEntity.permanentWater);
        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Glowstone");
        glowstoneArrowRadius = SERVER_CONFIG
                .comment("Adjust this to set the effect radius for the Glowstone Arrow. Set to 0 to disable, but that would be boring (and make the arrow useless).")
                .defineInRange("Radius", GlowstoneArrowEntity.effectRadius, 0, 16);
        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Prismarine");
        prismarineArrowDamage = SERVER_CONFIG
                .comment("Adjust this to set the damage for the Prismarine Arrow. Default Arrow value is 2.0.")
                .defineInRange("Damage", PrismarineArrowEntity.defaultDamage, 0.5, 16.0);
        prismarineArrowKnockback = SERVER_CONFIG
                .comment("Adjust this to set the inherent knockback strength of the Prismarine Arrow. Default Arrow value is 0.")
                .defineInRange("Knockback", PrismarineArrowEntity.defaultKnockback, 0, 16);
        prismarineArrowPierce = SERVER_CONFIG
                .comment("Adjust this to set the inherent pierce of the Prismarine Arrow. Default Arrow value is 0.")
                .defineInRange("Piercing", PrismarineArrowEntity.defaultPierce, 0, 16);
        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Quartz");
        quartzArrowDamage = SERVER_CONFIG
                .comment("Adjust this to set the damage for the Quartz Arrow. Default Arrow value is 2.0.")
                .defineInRange("Damage", QuartzArrowEntity.defaultDamage, 0.5, 16.0);
        quartzArrowKnockback = SERVER_CONFIG
                .comment("Adjust this to set the inherent knockback strength of the Quartz Arrow. Default Arrow value is 0.")
                .defineInRange("Knockback", QuartzArrowEntity.defaultKnockback, 0, 16);
        quartzArrowPierce = SERVER_CONFIG
                .comment("Adjust this to set the inherent pierce of the Quartz Arrow. Default Arrow value is 0.")
                .defineInRange("Piercing", QuartzArrowEntity.defaultPierce, 0, 16);
        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Redstone");
        redstoneArrowRadius = SERVER_CONFIG
                .comment("Adjust this to set the effect radius for the Redstone Arrow. Set to 0 to disable, but that would be boring (and make the arrow useless).")
                .defineInRange("Radius", RedstoneArrowEntity.effectRadius, 0, 16);
        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Shulker");
        shulkerArrowDuration = SERVER_CONFIG
                .comment("Adjust this to set the effect duration (Levitation) for the Shulker Arrow. Set to 0 to disable.")
                .defineInRange("Effect Duration", ShulkerArrowEntity.effectDuration, 0, 9600);
        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Slime");
        slimeArrowBounces = SERVER_CONFIG
                .comment("Adjust this to set the number of bounces for the Slime Arrow.")
                .defineInRange("Bounces", SlimeArrowEntity.defaultBounces, 1, 16);
        slimeArrowKnockback = SERVER_CONFIG
                .comment("Adjust this to set the inherent knockback strength of the Slime Arrow. Default Arrow value is 0.")
                .defineInRange("Knockback", SlimeArrowEntity.defaultKnockback, 0, 16);
        slimeArrowKnockbackBoost = SERVER_CONFIG
                .comment("If TRUE, bounces are modified by knockback bonuses, such as the Punch Enchantment.")
                .define("Knockback Boost", SlimeArrowEntity.knockbackBoost);
        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Spore");
        sporeArrowRadius = SERVER_CONFIG
                .comment("Adjust this to set the effect radius for the Spore Arrow. Set to 0 to disable, but that would be boring (and make the arrow useless).")
                .defineInRange("Radius", SporeArrowEntity.effectRadius, 0, 16);
        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Verdant");
        verdantArrowRadius = SERVER_CONFIG
                .comment("Adjust this to set the effect radius for the Verdant Arrow. Set to 0 to disable, but that would be boring (and make the arrow useless).")
                .defineInRange("Radius", VerdantArrowEntity.effectRadius, 0, 16);
        SERVER_CONFIG.pop();
    }

    private static void refreshServerConfig() {

        refreshArrowConfig();
    }

    private static void refreshClientConfig() {

    }

    private static void refreshArrowConfig() {

        BlazeArrowEntity.effectDuration = blazeArrowDuration.get();
        BlazeArrowEntity.effectRadius = blazeArrowRadius.get();

        DiamondArrowEntity.defaultDamage = diamondArrowDamage.get().floatValue();
        DiamondArrowEntity.defaultKnockback = diamondArrowKnockback.get();
        DiamondArrowEntity.defaultPierce = diamondArrowPierce.get().byteValue();

        DisplacementArrowEntity.effectRadius = displacementArrowRadius.get();

        ExplosiveArrowEntity.explosionStrength = explosiveArrowStrength.get();
        ExplosiveArrowEntity.explosionsBreakBlocks = explosiveArrowBreakBlocks.get();
        ExplosiveArrowEntity.explosionsCauseFire = explosiveArrowCauseFire.get();
        ExplosiveArrowEntity.knockbackBoost = explosiveArrowKnockbackBoost.get();

        FrostArrowEntity.effectRadius = frostArrowRadius.get();
        FrostArrowEntity.permanentLava = frostArrowPermanentLava.get();
        FrostArrowEntity.permanentWater = frostArrowPermanentWater.get();

        GlowstoneArrowEntity.effectRadius = glowstoneArrowRadius.get();

        PrismarineArrowEntity.defaultDamage = prismarineArrowDamage.get().floatValue();
        PrismarineArrowEntity.defaultKnockback = prismarineArrowKnockback.get();
        PrismarineArrowEntity.defaultPierce = prismarineArrowPierce.get().byteValue();

        QuartzArrowEntity.defaultDamage = quartzArrowDamage.get().floatValue();
        QuartzArrowEntity.defaultKnockback = quartzArrowKnockback.get();
        QuartzArrowEntity.defaultPierce = quartzArrowPierce.get().byteValue();

        RedstoneArrowEntity.effectRadius = redstoneArrowRadius.get();

        ShulkerArrowEntity.effectDuration = shulkerArrowDuration.get();

        SlimeArrowEntity.defaultBounces = slimeArrowBounces.get();
        SlimeArrowEntity.defaultKnockback = slimeArrowKnockback.get();
        SlimeArrowEntity.knockbackBoost = slimeArrowKnockbackBoost.get();

        SporeArrowEntity.effectRadius = sporeArrowRadius.get();

        VerdantArrowEntity.effectRadius = verdantArrowRadius.get();
    }
    // endregion

    // region VARIABLES
    public static BooleanValue enableFletcherTrades;

    public static BooleanValue enableCreativeTab;

    private static IntValue blazeArrowDuration;
    private static IntValue blazeArrowRadius;

    private static DoubleValue diamondArrowDamage;
    private static IntValue diamondArrowKnockback;
    private static IntValue diamondArrowPierce;

    private static IntValue displacementArrowRadius;

    private static DoubleValue explosiveArrowStrength;
    private static BooleanValue explosiveArrowBreakBlocks;
    private static BooleanValue explosiveArrowCauseFire;
    private static BooleanValue explosiveArrowKnockbackBoost;

    private static IntValue frostArrowRadius;
    private static BooleanValue frostArrowPermanentLava;
    private static BooleanValue frostArrowPermanentWater;

    private static IntValue glowstoneArrowRadius;

    private static DoubleValue prismarineArrowDamage;
    private static IntValue prismarineArrowKnockback;
    private static IntValue prismarineArrowPierce;

    private static DoubleValue quartzArrowDamage;
    private static IntValue quartzArrowKnockback;
    private static IntValue quartzArrowPierce;

    private static IntValue redstoneArrowRadius;

    private static IntValue slimeArrowBounces;
    private static IntValue slimeArrowKnockback;
    private static BooleanValue slimeArrowKnockbackBoost;

    private static IntValue shulkerArrowDuration;

    private static IntValue sporeArrowRadius;

    private static IntValue verdantArrowRadius;
    // endregion

    // region CONFIGURATION
    @SubscribeEvent
    public static void configLoading(ModConfig.Loading event) {

        switch (event.getConfig().getType()) {
            case CLIENT:
                refreshClientConfig();
                break;
            case SERVER:
                refreshServerConfig();
        }
    }

    @SubscribeEvent
    public static void configReloading(ModConfig.Reloading event) {

        switch (event.getConfig().getType()) {
            case CLIENT:
                refreshClientConfig();
                break;
            case SERVER:
                refreshServerConfig();
        }
    }
    // endregion
}
