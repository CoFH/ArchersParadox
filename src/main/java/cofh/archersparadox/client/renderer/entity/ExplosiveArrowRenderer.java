package cofh.archersparadox.client.renderer.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn (Dist.CLIENT)
public class ExplosiveArrowRenderer extends ArrowRenderer<AbstractArrow> {

    public static final ResourceLocation TEXTURE = new ResourceLocation("archers_paradox:textures/entity/projectiles/explosive_arrow.png");

    public ExplosiveArrowRenderer(EntityRendererProvider.Context ctx) {

        super(ctx);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractArrow entity) {

        return TEXTURE;
    }

}
