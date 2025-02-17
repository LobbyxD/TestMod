package com.bruno.testmod.event;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.item.custom.HammerItem;
import com.bruno.testmod.potion.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    public static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if(pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if(event.getEntity() instanceof Sheep sheep && event.getSource().getDirectEntity() instanceof Player player) {
            if(player.getMainHandItem().getItem() == Items.END_ROD) {
                player.sendSystemMessage(Component.literal(player.getName().getString() + " JUST HIT A SHEEP WITH AN END ROD! YOU SICK FRICK!"));
                sheep.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 5));
                player.getMainHandItem().shrink(1);
            }
        }
    }

    // register potion recipe
    @SubscribeEvent
    public static void onBrewingRecipeRegister(BrewingRecipeRegisterEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.SLIME_BALL, ModPotions.SLIMEY_POTION.getHolder().get());
    }

    @SubscribeEvent
    public static void onLivingSetAttackTarget(@NotNull LivingChangeTargetEvent event) {
        if (event.getEntity() instanceof Zombie zombie) {
            UUID targetPlayerUUID = getTargetPlayerUUID(zombie);
            if (event.getNewTarget() instanceof Player player) {

                // start attacking a new player
                if (targetPlayerUUID == null) {
                    setZombiePlayerUUID(zombie, player.getUUID());  // update new player
                    //player.sendSystemMessage(Component.literal("Zombie has found you"));
                    zombie.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4f);
                }
                // attacking the same player
                else if (targetPlayerUUID.equals(player.getUUID())) {
                    //player.sendSystemMessage(Component.literal("Zombie is targeting at you §9STILL§r"));
                }
                // attacking a new player (changed player target)
                else {
                    // send message to old player
//                    Player oldPlayer = getPlayerByUUIDFromServer(targetPlayerUUID);
//                    oldPlayer.sendSystemMessage(Component.literal("Zombie is §9NOT§r targeting you anymore, but someone else"));

                    // update new player
                    setZombiePlayerUUID(zombie, player.getUUID());
                }

            } else {  // if not targeting a player
                if(targetPlayerUUID != null) {
//                    Player oldPlayer = getPlayerByUUIDFromServer(targetPlayerUUID);
//                    oldPlayer.sendSystemMessage(Component.literal("Zombie is §NOT§r targeting you anymore"));

                    // Remove the UUID if the target is not a player
                    zombie.getPersistentData().remove("TargetPlayerUUID");

                    zombie.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.23f);
                }
            }
        }

    }

    public static UUID getTargetPlayerUUID(Zombie zombie) {
        CompoundTag persistentData = zombie.getPersistentData();
        if (persistentData.hasUUID("TargetPlayerUUID")) {
            return persistentData.getUUID("TargetPlayerUUID");
        }
        return null; // No player UUID stored
    }

    public static void setZombiePlayerUUID(Zombie zombie, UUID playerUUID) {
        CompoundTag persistentData = zombie.getPersistentData();
        persistentData.putUUID("TargetPlayerUUID", playerUUID);
    }

    public static Player getPlayerByUUIDFromServer(UUID playerUUID) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return server.getPlayerList().getPlayer(playerUUID);
    }

}
