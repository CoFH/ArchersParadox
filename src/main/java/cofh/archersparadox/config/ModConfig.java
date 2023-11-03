package cofh.archersparadox.config;

import cofh.core.config.IBaseConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Supplier;

import static cofh.lib.util.Constants.TRUE;

public class ModConfig implements IBaseConfig {

    @Override
    public void apply(ForgeConfigSpec.Builder builder) {

        builder.push("Villagers");

        enableVillagerTrades = builder
                .comment("If TRUE, trades will be added to various Villagers.")
                .define("Enable Villager Trades", enableVillagerTrades);

        enableWandererTrades = builder
                .comment("If TRUE, trades will be added to the Wandering Trader.")
                .define("Enable Wandering Trader Trades", enableWandererTrades);

        builder.pop();
    }

    // region CONFIG VARIABLES
    public static Supplier<Boolean> enableVillagerTrades = TRUE;
    public static Supplier<Boolean> enableWandererTrades = TRUE;
    // endregion
}
