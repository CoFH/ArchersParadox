package cofh.archersparadox.init.registries;

import cofh.archersparadox.common.entity.projectile.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;

import static cofh.archersparadox.ArchersParadox.ENTITIES;
import static cofh.archersparadox.init.registries.ModIDs.*;

public class ModEntities {

    private ModEntities() {

    }

    public static void register() {

    }

    public static final DeferredHolder<EntityType<?>, EntityType<ExplosiveArrow>> EXPLOSIVE_ARROW = ENTITIES.register(ID_EXPLOSIVE_ARROW, () -> EntityType.Builder.<ExplosiveArrow>of(ExplosiveArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_EXPLOSIVE_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<QuartzArrow>> QUARTZ_ARROW = ENTITIES.register(ID_QUARTZ_ARROW, () -> EntityType.Builder.<QuartzArrow>of(QuartzArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_QUARTZ_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<DiamondArrow>> DIAMOND_ARROW = ENTITIES.register(ID_DIAMOND_ARROW, () -> EntityType.Builder.<DiamondArrow>of(DiamondArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_DIAMOND_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<PrismarineArrow>> PRISMARINE_ARROW = ENTITIES.register(ID_PRISMARINE_ARROW, () -> EntityType.Builder.<PrismarineArrow>of(PrismarineArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_PRISMARINE_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<SlimeArrow>> SLIME_ARROW = ENTITIES.register(ID_SLIME_ARROW, () -> EntityType.Builder.<SlimeArrow>of(SlimeArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_SLIME_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<EnderArrow>> ENDER_ARROW = ENTITIES.register(ID_ENDER_ARROW, () -> EntityType.Builder.<EnderArrow>of(EnderArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_ENDER_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<TrainingArrow>> TRAINING_ARROW = ENTITIES.register(ID_TRAINING_ARROW, () -> EntityType.Builder.<TrainingArrow>of(TrainingArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_TRAINING_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<ChallengeArrow>> CHALLENGE_ARROW = ENTITIES.register(ID_CHALLENGE_ARROW, () -> EntityType.Builder.<ChallengeArrow>of(ChallengeArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_CHALLENGE_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<PhantasmalArrow>> PHANTASMAL_ARROW = ENTITIES.register(ID_PHANTASMAL_ARROW, () -> EntityType.Builder.<PhantasmalArrow>of(PhantasmalArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_PHANTASMAL_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<ShulkerArrow>> SHULKER_ARROW = ENTITIES.register(ID_SHULKER_ARROW, () -> EntityType.Builder.<ShulkerArrow>of(ShulkerArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_SHULKER_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<BlazeArrow>> BLAZE_ARROW = ENTITIES.register(ID_BLAZE_ARROW, () -> EntityType.Builder.<BlazeArrow>of(BlazeArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_BLAZE_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<FrostArrow>> FROST_ARROW = ENTITIES.register(ID_FROST_ARROW, () -> EntityType.Builder.<FrostArrow>of(FrostArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_FROST_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<LightningArrow>> LIGHTNING_ARROW = ENTITIES.register(ID_LIGHTNING_ARROW, () -> EntityType.Builder.<LightningArrow>of(LightningArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_LIGHTNING_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<VerdantArrow>> VERDANT_ARROW = ENTITIES.register(ID_VERDANT_ARROW, () -> EntityType.Builder.<VerdantArrow>of(VerdantArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_VERDANT_ARROW));
    public static final DeferredHolder<EntityType<?>, EntityType<SporeArrow>> SPORE_ARROW = ENTITIES.register(ID_SPORE_ARROW, () -> EntityType.Builder.<SporeArrow>of(SporeArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_SPORE_ARROW));

}
