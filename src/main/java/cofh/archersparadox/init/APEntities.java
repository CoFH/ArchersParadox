package cofh.archersparadox.init;

import cofh.archersparadox.entity.projectile.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static cofh.archersparadox.ArchersParadox.ENTITIES;
import static cofh.archersparadox.init.APIDs.*;

public class APEntities {

    private APEntities() {

    }

    public static void register() {

        ENTITIES.register(ID_EXPLOSIVE_ARROW, () -> EntityType.Builder.<ExplosiveArrow>of(ExplosiveArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_EXPLOSIVE_ARROW));
        ENTITIES.register(ID_QUARTZ_ARROW, () -> EntityType.Builder.<QuartzArrow>of(QuartzArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_QUARTZ_ARROW));
        ENTITIES.register(ID_DIAMOND_ARROW, () -> EntityType.Builder.<DiamondArrow>of(DiamondArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_DIAMOND_ARROW));
        ENTITIES.register(ID_PRISMARINE_ARROW, () -> EntityType.Builder.<PrismarineArrow>of(PrismarineArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_PRISMARINE_ARROW));
        ENTITIES.register(ID_SLIME_ARROW, () -> EntityType.Builder.<SlimeArrow>of(SlimeArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_SLIME_ARROW));
        ENTITIES.register(ID_ENDER_ARROW, () -> EntityType.Builder.<EnderArrow>of(EnderArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_ENDER_ARROW));
        ENTITIES.register(ID_PHANTASMAL_ARROW, () -> EntityType.Builder.<PhantasmalArrow>of(PhantasmalArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_PHANTASMAL_ARROW));
        ENTITIES.register(ID_SHULKER_ARROW, () -> EntityType.Builder.<ShulkerArrow>of(ShulkerArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(ID_SHULKER_ARROW));

        //        ENTITIES.register(ID_BLAZE_ARROW, () -> EntityType.Builder.<BlazeArrowEntity>of(BlazeArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(ID_BLAZE_ARROW));
        //        ENTITIES.register(ID_CHALLENGE_ARROW, () -> EntityType.Builder.<ChallengeArrowEntity>of(ChallengeArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(ID_CHALLENGE_ARROW));
        //        ENTITIES.register(ID_DISPLACEMENT_ARROW, () -> EntityType.Builder.<DisplacementArrowEntity>of(DisplacementArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(ID_DISPLACEMENT_ARROW));
        //        ENTITIES.register(ID_ENDER_ARROW, () -> EntityType.Builder.<EnderArrowEntity>of(EnderArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(ID_ENDER_ARROW));
        //        ENTITIES.register(ID_FROST_ARROW, () -> EntityType.Builder.<FrostArrowEntity>of(FrostArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(ID_FROST_ARROW));
        //        ENTITIES.register(ID_GLOWSTONE_ARROW, () -> EntityType.Builder.<GlowstoneArrowEntity>of(GlowstoneArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(ID_GLOWSTONE_ARROW));
        //        ENTITIES.register(ID_LIGHTNING_ARROW, () -> EntityType.Builder.<LightningArrowEntity>of(LightningArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(ID_LIGHTNING_ARROW));
        //        // ENTITIES.register(ID_MAGMA_ARROW, () -> EntityType.Builder.<MagmaArrowEntity>create(MagmaArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build(ID_MAGMA_ARROW));
        //        ENTITIES.register(ID_PHANTASMAL_ARROW, () -> EntityType.Builder.<PhantasmalArrowEntity>of(PhantasmalArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(ID_PHANTASMAL_ARROW));
        //        ENTITIES.register(ID_REDSTONE_ARROW, () -> EntityType.Builder.<RedstoneArrowEntity>of(RedstoneArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(ID_REDSTONE_ARROW));
        //        ENTITIES.register(ID_SPORE_ARROW, () -> EntityType.Builder.<SporeArrowEntity>of(SporeArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(ID_SPORE_ARROW));
        //        ENTITIES.register(ID_TRAINING_ARROW, () -> EntityType.Builder.<TrainingArrowEntity>of(TrainingArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(ID_TRAINING_ARROW));
        //        ENTITIES.register(ID_VERDANT_ARROW, () -> EntityType.Builder.<VerdantArrowEntity>of(VerdantArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(ID_VERDANT_ARROW));
    }

}
