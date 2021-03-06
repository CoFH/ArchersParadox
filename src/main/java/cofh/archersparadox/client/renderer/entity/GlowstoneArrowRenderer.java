package cofh.archersparadox.client.renderer.entity;

import cofh.archersparadox.entity.projectile.GlowstoneArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn (Dist.CLIENT)
public class GlowstoneArrowRenderer extends ArrowRenderer<GlowstoneArrowEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation("archers_paradox:textures/entity/projectiles/glowstone_arrow.png");

    public GlowstoneArrowRenderer(EntityRendererManager manager) {

        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(GlowstoneArrowEntity entity) {

        return TEXTURE;
    }

}
