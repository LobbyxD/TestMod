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
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/entity/orbiting_texture.png");
        float scale = 0.5F; // Adjust the scale as needed

        // Calculate the horizontal offset using a sine wave for smooth oscillation
        double time = System.currentTimeMillis() / 1000.0; // Current time in seconds
        float oscillationSpeed = 1.0F; // Oscillations per second
        float amplitude = 0.5F; // Maximum horizontal movement
        float horizontalOffset = (float) (Math.sin(time * Math.PI * 2 * oscillationSpeed) * amplitude);

        // Push the current transformation matrix
        poseStack.pushPose();

        // Translate to the player's position, adjust the height, and apply horizontal offset
        poseStack.translate(horizontalOffset, player.getBbHeight() + 0.5F, 0.0D);

        // Rotate to face the camera
        poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        // Scale the texture
        poseStack.scale(-scale, -scale, scale);

        // Bind the texture
        RenderSystem.setShaderTexture(0, texture);

        // Define the vertex consumer
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));

        // Render a simple quad (rectangle)
        Matrix4f matrix = poseStack.last().pose();

        float halfWidth = 0.5F; // Half of the quad's width
        float heightAboveHead = 1.0F; // Distance above the player's head
        float zOffset = 0.0F; // Depth offset

        // Define the four corners of the quad
        Vector3f topLeft = new Vector3f(-halfWidth, heightAboveHead, zOffset);
        Vector3f topRight = new Vector3f(halfWidth, heightAboveHead, zOffset);
        Vector3f bottomLeft = new Vector3f(-halfWidth, 0.0F, zOffset);
        Vector3f bottomRight = new Vector3f(halfWidth, 0.0F, zOffset);

        float u0 = 0.0F; // Left edge of the texture
        float u1 = 1.0F; // Right edge of the texture
        float v0 = 1.0F; // Top edge of the texture
        float v1 = 0.0F; // Bottom edge of the texture

        int overlay = OverlayTexture.NO_OVERLAY;
        float red = 1.0F, green = 1.0F, blue = 1.0F, alpha = 1.0F; // White color
        float normalX = 0.0F, normalY = 1.0F, normalZ = 0.0F; // Upward normal

        // Bottom-left vertex
        vertexConsumer.addVertex(matrix, bottomLeft.x(), bottomLeft.y(), bottomLeft.z())
                .setColor((int)(red * 255), (int)(green * 255), (int)(blue * 255), (int)(alpha * 255))
                .setUv(u0, v1)
                .setOverlay(overlay)
                .setLight(light)
                .setNormal(normalX, normalY, normalZ);

        // Bottom-right vertex
        vertexConsumer.addVertex(matrix, bottomRight.x(), bottomRight.y(), bottomRight.z())
                .setColor((int)(red * 255), (int)(green * 255), (int)(blue * 255), (int)(alpha * 255))
                .setUv(u1, v1)
                .setOverlay(overlay)
                .setLight(light)
                .setNormal(normalX, normalY, normalZ);

        // Top-right vertex
        vertexConsumer.addVertex(matrix, topRight.x(), topRight.y(), topRight.z())
                .setColor((int)(red * 255), (int)(green * 255), (int)(blue * 255), (int)(alpha * 255))
                .setUv(u1, v0)
                .setOverlay(overlay)
                .setLight(light)
                .setNormal(normalX, normalY, normalZ);

        // Top-left vertex
        vertexConsumer.addVertex(matrix, topLeft.x(), topLeft.y(), topLeft.z())
                .setColor((int)(red * 255), (int)(green * 255), (int)(blue * 255), (int)(alpha * 255))
                .setUv(u0, v0)
                .setOverlay(overlay)
                .setLight(light)
                .setNormal(normalX, normalY, normalZ);

        // Pop the transformation matrix to restore the previous state
        poseStack.popPose();
    }



}
