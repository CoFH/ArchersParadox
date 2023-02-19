package cofh.archersparadox.client.renderer.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class FrostArrowRenderer extends ArrowRenderer<AbstractArrow> {

    public static final ResourceLocation TEXTURE = new ResourceLocation("archers_paradox:textures/entity/projectiles/frost_arrow.png");

    public FrostArrowRenderer(EntityRendererProvider.Context ctx) {

        super(ctx);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow entity) {

        return TEXTURE;
    }

}
