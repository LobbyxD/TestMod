package com.bruno.testmod.skills.beam;

import com.bruno.testmod.TestMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class LightRayRenderer<T extends LightRayEntity> extends EntityRenderer<T> {

    public LightRayRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        // Set up beam visual params
        float beamHeight = 128f;
        float radius = 0.5f;
        int beamSlices = 16; // number of quads around the circle

        // Beam color (yellowish glow)
        float r = 1.0f, g = 1.0f, b = 1f, a = 0.9f;
        int overlay = OverlayTexture.NO_OVERLAY;
        int light = 0xF000F0; // full brightness

        Matrix4f matrix = poseStack.last().pose();
//        VertexConsumer builder = bufferSource.getBuffer(RenderType.lightning());

        ResourceLocation beaconTexture = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/beacon_beam.png");
        VertexConsumer builder = bufferSource.getBuffer(RenderType.entityTranslucentCull(beaconTexture));


        // uvs
        float scrollSpeed = 0.02f;
        float vOffset = (entity.tickCount + partialTicks) * scrollSpeed;

        float v1 = vOffset;
        float v2 = vOffset + 1.0f;

        // fade effect - divine flash
        float lifetime = 12;
        float lifeRatio = 1f - (entity.tickCount + partialTicks) / lifetime;
        float alpha = Mth.clamp(lifeRatio, 0f, 0.5f) * 0.5f;

        for (int i = 0; i < beamSlices; i++) {
            double angle1 = (2 * Math.PI / beamSlices) * i;
            double angle2 = (2 * Math.PI / beamSlices) * (i + 1);

            float x1 = (float) Math.cos(angle1) * radius;
            float z1 = (float) Math.sin(angle1) * radius;
            float x2 = (float) Math.cos(angle2) * radius;
            float z2 = (float) Math.sin(angle2) * radius;

            // Draw vertical quad slice of the beam
            addBeamVertex(builder, matrix, x1, 0f, z1, r, g, b, alpha, 0f, v1, overlay, light, 0f, 1f, 0f);
            addBeamVertex(builder, matrix, x1, beamHeight, z1, r, g, b, alpha, 0f, v2, overlay, light, 0f, 1f, 0f);
            addBeamVertex(builder, matrix, x2, beamHeight, z2, r, g, b, alpha, 1f, v2, overlay, light, 0f, 1f, 0f);
            addBeamVertex(builder, matrix, x2, 0f, z2, r, g, b, alpha, 1f, v1, overlay, light, 0f, 1f, 0f);

        }

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(T pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true; // Always render
    }

//    @Override
//    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
//        double dx = this.getX() - cameraX;
//        double dy = (this.getY() + 64) - cameraY; // center beam height
//        double dz = this.getZ() - cameraZ;
//        double distanceSq = dx * dx + dy * dy + dz * dz;
//        return distanceSq < 512 * 512; // render if within 512 blocks
//    }


    private void addBeamVertex(VertexConsumer builder, Matrix4f matrix,
                               float x, float y, float z,
                               float r, float g, float b, float a,
                               float u, float v,
                               int overlay, int light,
                               float normalX, float normalY, float normalZ) {

        builder.addVertex(matrix, x, y, z)
                .setColor((int)(r * 255), (int)(g * 255), (int)(b * 255), (int)(a * 255))
                .setUv(u, v)
                .setOverlay(overlay)
                .setLight(light)
                .setNormal(normalX, normalY, normalZ);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return null; // No texture needed
    }
}
