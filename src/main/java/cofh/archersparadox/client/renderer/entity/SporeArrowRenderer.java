package cofh.archersparadox.client.renderer.entity;

import cofh.archersparadox.entity.projectile.SporeArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SporeArrowRenderer extends ArrowRenderer<SporeArrowEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation("archers_paradox:textures/entity/projectiles/spore_arrow.png");

    public SporeArrowRenderer(EntityRendererManager manager) {

        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(SporeArrowEntity entity) {

        return TEXTURE;
    }

}
