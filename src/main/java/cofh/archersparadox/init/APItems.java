package cofh.archersparadox.init;

import cofh.archersparadox.ArchersParadox;
import cofh.archersparadox.entity.projectile.*;
import cofh.core.item.ArrowItemCoFH;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.world.World;

import static cofh.archersparadox.ArchersParadox.ITEMS;
import static cofh.archersparadox.init.APIDs.*;

public class APItems {

    private APItems() {

    }

    public static void register() {

        ItemGroup group = ItemGroup.COMBAT;

        ITEMS.register(ID_BLAZE_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new BlazeArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new BlazeArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_CHALLENGE_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new ChallengeArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new ChallengeArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group).rarity(Rarity.UNCOMMON)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_DIAMOND_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new DiamondArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new DiamondArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_DISPLACEMENT_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new DisplacementArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new DisplacementArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_ENDER_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new EnderArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new EnderArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_EXPLOSIVE_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new ExplosiveArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new ExplosiveArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_FROST_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new FrostArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new FrostArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_GLOWSTONE_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new GlowstoneArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new GlowstoneArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_LIGHTNING_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new LightningArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new LightningArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        // ITEMS.register(ID_MAGMA_ARROW, () -> new ArrowItemCoFH(MagmaArrowEntity::new, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_PHANTASMAL_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new PhantasmalArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new PhantasmalArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_PRISMARINE_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new PrismarineArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new PrismarineArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_QUARTZ_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new QuartzArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new QuartzArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_REDSTONE_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new RedstoneArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new RedstoneArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_SHULKER_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new ShulkerArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new ShulkerArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group).rarity(Rarity.UNCOMMON)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_SLIME_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new SlimeArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new SlimeArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_SPORE_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new SporeArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new SporeArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));

        ITEMS.register(ID_TRAINING_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new TrainingArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new TrainingArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup).setInfinitySupport(true));

        ITEMS.register(ID_VERDANT_ARROW, () -> new ArrowItemCoFH(new ArrowItemCoFH.IArrowFactory<AbstractArrowEntity>() {

            @Override
            public AbstractArrowEntity createArrow(World world, LivingEntity living) {

                return new VerdantArrowEntity(world, living);
            }

            @Override
            public AbstractArrowEntity createArrow(World world, double posX, double posY, double posZ) {

                return new VerdantArrowEntity(world, posX, posY, posZ);
            }
        }, new Item.Properties().group(group)).setDisplayGroup(() -> ArchersParadox.itemGroup));
    }

}
