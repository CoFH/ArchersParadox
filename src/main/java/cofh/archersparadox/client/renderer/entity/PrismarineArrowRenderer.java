package cofh.archersparadox.client.renderer.entity;

import cofh.archersparadox.entity.projectile.PrismarineArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PrismarineArrowRenderer extends ArrowRenderer<PrismarineArrowEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation("archers_paradox:textures/entity/projectiles/prismarine_arrow.png");

    public PrismarineArrowRenderer(EntityRendererManager manager) {

        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(PrismarineArrowEntity entity) {

        return TEXTURE;
    }

}
