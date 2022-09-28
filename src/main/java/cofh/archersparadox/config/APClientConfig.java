package cofh.archersparadox.config;

import cofh.core.config.IBaseConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Supplier;

public class APClientConfig implements IBaseConfig {

    @Override
    public void apply(ForgeConfigSpec.Builder builder) {

        enableCreativeTab = builder
                .comment("If TRUE, Archer's Paradox will have its own Item Group (Creative Tab).")
                .define("Enable Item Group", true);
    }

    @Override
    public void refresh() {

    }

    // region VARIABLES
    public static Supplier<Boolean> enableCreativeTab = () -> true;
    // endregion
}