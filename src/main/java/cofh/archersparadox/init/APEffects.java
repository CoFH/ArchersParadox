package cofh.archersparadox.init;

import cofh.archersparadox.potion.ChallengeEffect;
import cofh.core.effect.NeutralEffect;
import net.minecraft.world.effect.MobEffectCategory;

import static cofh.archersparadox.ArchersParadox.EFFECTS;
import static cofh.archersparadox.init.APIDs.*;

public class APEffects {

    private APEffects() {

    }

    public static void register() {

        EFFECTS.register(ID_EFFECT_CHALLENGE_COMPLETE, () -> new NeutralEffect(MobEffectCategory.NEUTRAL, 0x888888));
        EFFECTS.register(ID_EFFECT_CHALLENGE_MISS, () -> new NeutralEffect(MobEffectCategory.NEUTRAL, 0x888888));
        EFFECTS.register(ID_EFFECT_CHALLENGE_STREAK, () -> new ChallengeEffect(MobEffectCategory.NEUTRAL, 0x888888));

        EFFECTS.register(ID_EFFECT_TRAINING_MISS, () -> new NeutralEffect(MobEffectCategory.NEUTRAL, 0x888888));
        EFFECTS.register(ID_EFFECT_TRAINING_STREAK, () -> new NeutralEffect(MobEffectCategory.NEUTRAL, 0x888888));
    }

}
