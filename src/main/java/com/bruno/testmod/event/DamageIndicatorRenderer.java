package com.bruno.testmod.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "testmod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DamageIndicatorRenderer {

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



}
