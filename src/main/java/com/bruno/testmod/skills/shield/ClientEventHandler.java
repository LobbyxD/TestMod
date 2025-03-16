package com.bruno.testmod.skills.shield;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.entity.ModEntities;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource bufferSource = event.getMultiBufferSource();
        int light = event.getPackedLight();

        // Ensure the player isn't invisible
        if (player.isInvisible()) return;

        // Call the method to render the custom texture
        renderCustomTextureAboveHead(player, poseStack, bufferSource, light);
    }

    private static void renderCustomTextureAboveHead(Player player, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        Minecraft mc = Minecraft.getInstance();
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/particle/shield.png");
        float scale = 0.5F; // Adjust the scale as needed

        // Calculate the horizontal offset using trigonometric functions
        double time = (double) System.currentTimeMillis() / 1000.0; // Current time in seconds
        float orbitRadius = 1.0F; // Radius of the orbit
        float orbitSpeed = 4.0F; // Speed of the orbit
        float heightAboveHead = -0.5f; // Adjust the height as needed

        // Draw the circular path
        int circleColor = 0xFFFFFF; // White color
        int segments = 50; // Number of segments to approximate the circle
        float orbitHeight = player.getBbHeight() + heightAboveHead - 0.2f;
        drawStackedCircles(poseStack, bufferSource, orbitRadius, segments, circleColor, light, orbitHeight, 15, 0.001f);

        float circle = (float) (Math.PI * 2);
        float[] initialAngles = {0.0F, circle / 3, circle * 2 / 3};
        for (int angleIdx = 0; angleIdx < initialAngles.length; angleIdx++) {

            float offsetX = orbitRadius * (float) Math.cos(time * orbitSpeed + initialAngles[angleIdx]);
            float offsetZ = orbitRadius * (float) Math.sin(time * orbitSpeed + initialAngles[angleIdx]);  // time * orbitSpeed

            // Calculate the rotation angle for the texture
            float textureRotationSpeed = 90.0F; // Degrees per second
            float textureRotationAngle = (float) (time * textureRotationSpeed % 360);

            // Push the current transformation matrix
            poseStack.pushPose();

            // Translate to the player's position, adjust the height, and apply horizontal offset

            poseStack.translate(offsetX, player.getBbHeight() + heightAboveHead, offsetZ);

            // Apply rotation to the texture around its center
            poseStack.mulPose(Axis.YP.rotationDegrees(textureRotationAngle));

            // Scale the texture
            poseStack.scale(-scale, -scale, scale);

            // Bind the texture
            RenderSystem.setShaderTexture(0, texture);

            // Define the vertex consumer
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));

            // Render multiple quads stacked along the z-axis
            Matrix4f matrix = poseStack.last().pose();

            float halfWidth = 0.5F; // Half of the quad's width
            float height = 1.0F; // Height of the quad
            int numberOfQuads = 15; // Number of quads to stack
            float depthOffset = 0.01F; // Distance between each stacked quad

            float u0 = 0.0F; // Left edge of the texture
            float u1 = 1.0F; // Right edge of the texture
            float v0 = 1.0F; // Top edge of the texture
            float v1 = 0.0F; // Bottom edge of the texture

            int overlay = OverlayTexture.NO_OVERLAY;
            float red = 1.0F, green = 1.0F, blue = 1.0F, alpha = 1.0F; // White color
            float normalX = 0.0F, normalY = 1.0F, normalZ = 1.0F; // Normal pointing outward along z-axis

            for (int i = 0; i < numberOfQuads; i++) {
                float zOffset = i * depthOffset;

                // Define the four corners of the quad
                Vector3f topLeft = new Vector3f(-halfWidth, height, zOffset);
                Vector3f topRight = new Vector3f(halfWidth, height, zOffset);
                Vector3f bottomLeft = new Vector3f(-halfWidth, 0.0F, zOffset);
                Vector3f bottomRight = new Vector3f(halfWidth, 0.0F, zOffset);

                // Bottom-left vertex
                vertexConsumer.addVertex(matrix, bottomLeft.x(), bottomLeft.y(), bottomLeft.z())
                        .setColor((int) (red * 255), (int) (green * 255), (int) (blue * 255), (int) (alpha * 255))
                        .setUv(u0, v1)
                        .setOverlay(overlay)
                        .setLight(light)
                        .setNormal(normalX, normalY, normalZ);

                // Bottom-right vertex
                vertexConsumer.addVertex(matrix, bottomRight.x(), bottomRight.y(), bottomRight.z())
                        .setColor((int) (red * 255), (int) (green * 255), (int) (blue * 255), (int) (alpha * 255))
                        .setUv(u1, v1)
                        .setOverlay(overlay)
                        .setLight(light)
                        .setNormal(normalX, normalY, normalZ);

                // Top-right vertex
                vertexConsumer.addVertex(matrix, topRight.x(), topRight.y(), topRight.z())
                        .setColor((int) (red * 255), (int) (green * 255), (int) (blue * 255), (int) (alpha * 255))
                        .setUv(u1, v0)
                        .setOverlay(overlay)
                        .setLight(light)
                        .setNormal(normalX, normalY, normalZ);

                // Top-left vertex
                vertexConsumer.addVertex(matrix, topLeft.x(), topLeft.y(), topLeft.z())
                        .setColor((int) (red * 255), (int) (green * 255), (int) (blue * 255), (int) (alpha * 255))
                        .setUv(u0, v0)
                        .setOverlay(overlay)
                        .setLight(light)
                        .setNormal(normalX, normalY, normalZ);
            }

            // Pop the transformation matrix to restore the previous state
            poseStack.popPose();
        }
    }

    private static void drawStackedCircles(PoseStack poseStack, MultiBufferSource bufferSource, float radius, int segments, int color, int light, float yOffset, int layers, float layerSpacing) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.lines());
        Matrix4f matrix = poseStack.last().pose();
        float angleIncrement = (float) (2 * Math.PI / segments);
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        float alpha = 0.4F; // Full opacity
        float normalX = 0.0F, normalY = 1.0F, normalZ = 0.0F; // Upward normal

        for (int layer = 0; layer < layers; layer++) {
            float currentYOffset = yOffset + layer * layerSpacing;
            for (int i = 0; i <= segments; i++) {
                float angle = i * angleIncrement;
                float x = radius * (float) Math.cos(angle);
                float z = radius * (float) Math.sin(angle);

                vertexConsumer.addVertex(matrix, x, currentYOffset, z)
                        .setColor((int) (red * 255), (int) (green * 255), (int) (blue * 255), (int) (alpha * 255))
                        .setUv(0.0F, 0.0F) // UV coordinates are not used for lines
                        .setOverlay(OverlayTexture.NO_OVERLAY)
                        .setLight(light)
                        .setNormal(normalX, normalY, normalZ);
            }
        }
    }






}
