package net.squidwalk.entitiy.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.squidwalk.SquidWalk;
import net.squidwalk.entitiy.custom.OrangeSquidEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OrangeSquidRenderer extends GeoEntityRenderer<OrangeSquidEntity> {

    public OrangeSquidRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new OrangeSquidModel());
    }

    @Override
    public Identifier getTextureLocation(OrangeSquidEntity animatable) {
        return new Identifier(SquidWalk.MOD_ID,"textures/entity/orange_squid.png");
    }

    @Override
    public void render(OrangeSquidEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
