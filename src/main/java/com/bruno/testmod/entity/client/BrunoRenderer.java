package com.bruno.testmod.entity.client;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.entity.custom.BrunoEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BrunoRenderer extends MobRenderer<BrunoEntity, BrunoModel<BrunoEntity>> {
    public BrunoRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BrunoModel<>(pContext.bakeLayer(BrunoModel.LAYER_LOCATION)), 0.85f);
    }

    @Override
    public ResourceLocation getTextureLocation(BrunoEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/entity/bruno/bruno.png");
    }

    @Override
    public void render(BrunoEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            pPoseStack.scale(1f, 1f, 1f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
