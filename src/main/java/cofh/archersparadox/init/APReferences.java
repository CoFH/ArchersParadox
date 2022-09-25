package cofh.archersparadox.init;

import cofh.archersparadox.entity.projectile.*;
import cofh.archersparadox.inventory.container.QuiverContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ObjectHolder;

import static cofh.archersparadox.init.APIDs.*;
import static cofh.lib.util.constants.ModIds.ID_ARCHERS_PARADOX;

@ObjectHolder (ID_ARCHERS_PARADOX)
public class APReferences {

    private APReferences() {

    }

    // region EFFECTS
    @ObjectHolder (ID_EFFECT_CHALLENGE_COMPLETE)
    public static final MobEffect CHALLENGE_COMPLETE = null;

    @ObjectHolder (ID_EFFECT_CHALLENGE_MISS)
    public static final MobEffect CHALLENGE_MISS = null;

    @ObjectHolder (ID_EFFECT_CHALLENGE_STREAK)
    public static final MobEffect CHALLENGE_STREAK = null;

    @ObjectHolder (ID_EFFECT_TRAINING_MISS)
    public static final MobEffect TRAINING_MISS = null;

    @ObjectHolder (ID_EFFECT_TRAINING_STREAK)
    public static final MobEffect TRAINING_STREAK = null;
    // endregion

    // region ENTITIES
    @ObjectHolder (ID_EXPLOSIVE_ARROW)
    public static final EntityType<ExplosiveArrow> EXPLOSIVE_ARROW_ENTITY = null;

    @ObjectHolder (ID_QUARTZ_ARROW)
    public static final EntityType<QuartzArrow> QUARTZ_ARROW_ENTITY = null;

    @ObjectHolder (ID_DIAMOND_ARROW)
    public static final EntityType<DiamondArrow> DIAMOND_ARROW_ENTITY = null;

    @ObjectHolder (ID_PRISMARINE_ARROW)
    public static final EntityType<PrismarineArrow> PRISMARINE_ARROW_ENTITY = null;

    @ObjectHolder (ID_SLIME_ARROW)
    public static final EntityType<SlimeArrow> SLIME_ARROW_ENTITY = null;

    @ObjectHolder (ID_ENDER_ARROW)
    public static final EntityType<EnderArrow> ENDER_ARROW_ENTITY = null;

    @ObjectHolder (ID_TRAINING_ARROW)
    public static final EntityType<TrainingArrow> TRAINING_ARROW_ENTITY = null;

    @ObjectHolder (ID_CHALLENGE_ARROW)
    public static final EntityType<ChallengeArrow> CHALLENGE_ARROW_ENTITY = null;

    @ObjectHolder (ID_PHANTASMAL_ARROW)
    public static final EntityType<PhantasmalArrow> PHANTASMAL_ARROW_ENTITY = null;

    @ObjectHolder (ID_SHULKER_ARROW)
    public static final EntityType<ShulkerArrow> SHULKER_ARROW_ENTITY = null;

    @ObjectHolder (ID_BLAZE_ARROW)
    public static final EntityType<BlazeArrow> BLAZE_ARROW_ENTITY = null;

    @ObjectHolder (ID_FROST_ARROW)
    public static final EntityType<FrostArrow> FROST_ARROW_ENTITY = null;

    @ObjectHolder (ID_LIGHTNING_ARROW)
    public static final EntityType<LightningArrow> LIGHTNING_ARROW_ENTITY = null;

    @ObjectHolder (ID_SPORE_ARROW)
    public static final EntityType<SporeArrow> SPORE_ARROW_ENTITY = null;

    @ObjectHolder (ID_VERDANT_ARROW)
    public static final EntityType<VerdantArrow> VERDANT_ARROW_ENTITY = null;

    //    @ObjectHolder (ID_DISPLACEMENT_ARROW)
    //    public static final EntityType<DisplacementArrowEntity> DISPLACEMENT_ARROW_ENTITY = null;
    //
    //    @ObjectHolder (ID_GLOWSTONE_ARROW)
    //    public static final EntityType<GlowstoneArrowEntity> GLOWSTONE_ARROW_ENTITY = null;
    //
    //    @ObjectHolder (ID_REDSTONE_ARROW)
    //    public static final EntityType<RedstoneArrowEntity> REDSTONE_ARROW_ENTITY = null;


    // endregion

    // region ITEMS
    @ObjectHolder (ID_EXPLOSIVE_ARROW)
    public static final Item EXPLOSIVE_ARROW_ITEM = null;

    @ObjectHolder (ID_QUARTZ_ARROW)
    public static final Item QUARTZ_ARROW_ITEM = null;

    @ObjectHolder (ID_DIAMOND_ARROW)
    public static final Item DIAMOND_ARROW_ITEM = null;

    @ObjectHolder (ID_PRISMARINE_ARROW)
    public static final Item PRISMARINE_ARROW_ITEM = null;

    @ObjectHolder (ID_SLIME_ARROW)
    public static final Item SLIME_ARROW_ITEM = null;

    @ObjectHolder (ID_ENDER_ARROW)
    public static final Item ENDER_ARROW_ITEM = null;

    @ObjectHolder (ID_TRAINING_ARROW)
    public static final Item TRAINING_ARROW_ITEM = null;

    @ObjectHolder (ID_CHALLENGE_ARROW)
    public static final Item CHALLENGE_ARROW_ITEM = null;

    @ObjectHolder (ID_PHANTASMAL_ARROW)
    public static final Item PHANTASMAL_ARROW_ITEM = null;

    @ObjectHolder (ID_SHULKER_ARROW)
    public static final Item SHULKER_ARROW_ITEM = null;

    @ObjectHolder (ID_BLAZE_ARROW)
    public static final Item BLAZE_ARROW_ITEM = null;

    @ObjectHolder (ID_DISPLACEMENT_ARROW)
    public static final Item DISPLACEMENT_ARROW_ITEM = null;

    @ObjectHolder (ID_FROST_ARROW)
    public static final Item FROST_ARROW_ITEM = null;

    @ObjectHolder (ID_GLOWSTONE_ARROW)
    public static final Item GLOWSTONE_ARROW_ITEM = null;

    @ObjectHolder (ID_LIGHTNING_ARROW)
    public static final Item LIGHTNING_ARROW_ITEM = null;

    @ObjectHolder (ID_MAGMA_ARROW)
    public static final Item MAGMA_ARROW_ITEM = null;

    @ObjectHolder (ID_REDSTONE_ARROW)
    public static final Item REDSTONE_ARROW_ITEM = null;

    @ObjectHolder (ID_SPORE_ARROW)
    public static final Item SPORE_ARROW_ITEM = null;

    @ObjectHolder (ID_VERDANT_ARROW)
    public static final Item VERDANT_ARROW_ITEM = null;
    // endregion

    // region CONTAINERS
    @ObjectHolder (ID_QUIVER)
    public static final MenuType<QuiverContainer> QUIVER_CONTAINER = null;
    // endregion
}
