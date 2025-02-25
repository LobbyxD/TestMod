package com.bruno.testmod.event;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.ZombieEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

    private static final double MAX_RENDER_DISTANCE = 10.0; // Max distance for rendering
    private static final int BAR_LENGTH = 10; // Number of segments in the health bar

    @SubscribeEvent
    public static void onComputerFovModiferEvent(ComputeFovModifierEvent event) {
        if (event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() == ModItems.BRUNITE_BOW.get()) {
            float fovModifer = 1f;
            int ticksUsingItem = event.getPlayer().getTicksUsingItem();
            float deltaTicks = (float)ticksUsingItem / 20f;
            if (deltaTicks > 1f) {
                deltaTicks = 1f;
            } else {
                deltaTicks *= deltaTicks;
            }
            fovModifer *= 1f - deltaTicks * 0.15f;
            event.setNewFovModifier(fovModifer);
        }
    }

    // health bar
    @SubscribeEvent
    public static void onRenderZombie(RenderLivingEvent.Post<Zombie, ?> event) {
        if (event.getEntity() instanceof Zombie zombie) {
            PoseStack poseStack = event.getPoseStack();
            MultiBufferSource bufferSource = event.getMultiBufferSource();
            Minecraft mc = Minecraft.getInstance();

            if (mc.player == null || zombie.isInvisible()) return; // Ignore if no player or zombie is invisible

            // **Calculate Distance to Player**
            double distance = mc.player.distanceTo(zombie);
            if (distance > MAX_RENDER_DISTANCE) return; // Don't render if too far away

            float health = zombie.getHealth();
            float maxHealth = zombie.getMaxHealth();
            float healthPercentage = health / maxHealth;

            // **Generate Health Bar**
            int filledBars = (int) (BAR_LENGTH * healthPercentage); // Number of filled segments
            int emptyBars = Math.max(1, BAR_LENGTH - filledBars); // Remaining empty segments

            String filled = "§a" + "█".repeat(filledBars);  // Green filled bar
            String empty = "§7" + "▒".repeat(emptyBars);    // Gray empty bar
            String healthText = filled + empty;             // Combine filled and empty

            // **Get Entity Name**
            String entityName = zombie.getDisplayName().getString(); // Get the name (or "Zombie" if unnamed)

            // Font renderer to draw text
            Font font = mc.font;

            // Position the text above the zombie's head
            poseStack.pushPose();
            poseStack.translate(0.0D, zombie.getBbHeight() + 0.6F, 0.0D); // Move text above head

            // **Rotate text to always face the camera**
            poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation()); // Correct method
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F)); // Fixes upside-down rendering

            poseStack.scale(-0.025F, -0.025F, 0.025F); // Adjust text size

            // **Draw Entity Name Above**
            float nameWidth = font.width(entityName) / 2.0F;
            font.drawInBatch(entityName, -nameWidth, -10, 0xFFFFFF, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, event.getPackedLight());

            // **Draw the Health Bar Below**
            float textWidth = font.width(healthText) / 2.0F;
            font.drawInBatch(healthText, -textWidth, 0, 0xFFFFFF, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, event.getPackedLight());

            poseStack.popPose();
        }
    }

}
