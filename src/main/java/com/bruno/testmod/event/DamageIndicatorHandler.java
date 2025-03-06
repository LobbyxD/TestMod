package com.bruno.testmod.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = "testmod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DamageIndicatorHandler {

//    public static final Map<Integer, Float> damageMap = new ConcurrentHashMap<>();
//    public static final Map<Integer, Long> timestampMap = new ConcurrentHashMap<>();
//    public static final long DISPLAY_DURATION = 1000; // Display duration in milliseconds
//
//    @SubscribeEvent
//    public static void onLivingHurt(LivingHurtEvent event) {
//        if (event.getSource().getEntity() instanceof Player) {
//            LivingEntity entity = event.getEntity();
//            float damageAmount = event.getAmount();
//            System.out.println("Entity hurt: " + entity.getName().getString() + ", Damage: " + damageAmount);
//            damageMap.put(entity.getId(), damageAmount);
//            timestampMap.put(entity.getId(), System.currentTimeMillis());
//        }
//    }
//
//    @SubscribeEvent
//    public static void onRenderLiving(RenderLivingEvent.Post<LivingEntity, ?> event) {
//        LivingEntity entity = event.getEntity();
//        int entityId = entity.getId();
//
//        if (damageMap.containsKey(entityId)) {
//            long currentTime = System.currentTimeMillis();
//            long damageTime = timestampMap.get(entityId);
//
//            if (currentTime - damageTime < DISPLAY_DURATION) {
//                float damage = damageMap.get(entityId);
//                System.out.println("Rendering damage indicator for: " + entity.getName().getString() + ", Damage: " + damage);
//                renderDamageIndicator(event.getPoseStack(), event.getMultiBufferSource(), entity, damage);
//            } else {
//                damageMap.remove(entityId);
//                timestampMap.remove(entityId);
//            }
//        }
//    }
//
//    private static void renderDamageIndicator(PoseStack poseStack, MultiBufferSource bufferSource, LivingEntity entity, float damage) {
//        EntityRenderDispatcher renderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
//        Font fontRenderer = Minecraft.getInstance().font;
//
//        double x = entity.getX() - renderDispatcher.camera.getPosition().x;
//        double y = entity.getY() + entity.getBbHeight() + 0.5 - renderDispatcher.camera.getPosition().y;
//        double z = entity.getZ() - renderDispatcher.camera.getPosition().z;
//
//        String damageText = String.format("-%.1f", damage);
//
//        poseStack.pushPose();
//        poseStack.translate(x, y, z);
//        poseStack.mulPose(renderDispatcher.cameraOrientation());
//        poseStack.scale(-0.025F, -0.025F, 0.025F);
//
//        float backgroundOpacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
//        int backgroundColor = (int) (backgroundOpacity * 255.0F) << 24;
//        float textWidth = -fontRenderer.width(damageText) / 2.0F;
//
//        fontRenderer.drawInBatch(damageText, textWidth, 0, 0xFFFFFF, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, backgroundColor, 15728880);
//
//        poseStack.popPose();
//    }

    // Additional methods will be added here
}
