package com.bruno.testmod.event;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.ZombieEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
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

    @SubscribeEvent
    public static void onLivingVisibilityEvent(LivingEvent.LivingVisibilityEvent event) {
        if (event.getEntity() instanceof Zombie zombie && event.getLookingEntity() instanceof Player player) {
            player.sendSystemMessage(Component.literal("Zombie is looking at you"));
        }
    }
}
