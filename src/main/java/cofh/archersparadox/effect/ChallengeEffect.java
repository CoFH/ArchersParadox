package cofh.archersparadox.effect;

import cofh.lib.effect.MobEffectCoFH;
import cofh.lib.util.Utils;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import static cofh.archersparadox.init.ModEffects.CHALLENGE_COMPLETE;

public class ChallengeEffect extends MobEffectCoFH {

    public static int maxExperience = 500;

    public ChallengeEffect(MobEffectCategory typeIn, int liquidColorIn) {

        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {

        int level = amplifier + 1;

        if (entityLivingBaseIn instanceof Player) {
            Player player = (Player) entityLivingBaseIn;
            if (Utils.isServerWorld(entityLivingBaseIn.level)) {
                player.giveExperiencePoints(Math.min(level * level + level, maxExperience));
            }
            player.addEffect(new MobEffectInstance(CHALLENGE_COMPLETE.get(), 960 * level, 0, false, false));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {

        return duration == 1;
    }

}
