package cofh.archersparadox.init;

import cofh.archersparadox.effect.ChallengeEffect;
import cofh.core.effect.NeutralMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.RegistryObject;

import static cofh.archersparadox.ArchersParadox.EFFECTS;
import static cofh.archersparadox.init.ModIDs.*;

public class ModEffects {

    private ModEffects() {

    }

    public static void register() {


    }

    public static final RegistryObject<MobEffect> CHALLENGE_COMPLETE = EFFECTS.register(ID_EFFECT_CHALLENGE_COMPLETE, () -> new NeutralMobEffect(MobEffectCategory.NEUTRAL, 0x888888));
    public static final RegistryObject<MobEffect> CHALLENGE_MISS = EFFECTS.register(ID_EFFECT_CHALLENGE_MISS, () -> new NeutralMobEffect(MobEffectCategory.NEUTRAL, 0x888888));
    public static final RegistryObject<MobEffect> CHALLENGE_STREAK = EFFECTS.register(ID_EFFECT_CHALLENGE_STREAK, () -> new ChallengeEffect(MobEffectCategory.NEUTRAL, 0x888888));
    public static final RegistryObject<MobEffect> TRAINING_MISS = EFFECTS.register(ID_EFFECT_TRAINING_MISS, () -> new NeutralMobEffect(MobEffectCategory.NEUTRAL, 0x888888));
    public static final RegistryObject<MobEffect> TRAINING_STREAK = EFFECTS.register(ID_EFFECT_TRAINING_STREAK, () -> new NeutralMobEffect(MobEffectCategory.NEUTRAL, 0x888888));

}
