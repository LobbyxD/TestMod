package com.bruno.testmod.event.damagefloat;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.network.PacketHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        //this should be client sided buuut its only fired on server
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof LivingEntity entity &&
                event.getEntity().getType() != EntityType.PLAYER && event.getAmount() != 0) {
            var message = new ClientBoundDamageNumberMessage(entity.getId(), event.getAmount(), event.getSource().typeHolder(), false, 1);
            if (event.getSource().getEntity() instanceof ServerPlayer attackingPlayer) {
                //NetworkHelper.sendToClientPlayer(attackingPlayer, message);
                PacketHandler.sendToPlayer(message, attackingPlayer);
            }
        }
    }
}
