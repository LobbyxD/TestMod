package com.bruno.testmod.event;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.item.custom.HammerItem;
import com.bruno.testmod.network.PacketHandler;
import com.bruno.testmod.network.packets.ZombieLevelSyncPacket;
import com.bruno.testmod.potion.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;

import java.util.*;
import java.util.Random;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    public static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();
    private static final Random RANDOM = new Random();
    public static final String LEVEL_TAG = "ZombieLevel"; // Unique key to store level
    public static final String AGGRO_TAG = "ZombieAggro"; // Unique key to store level

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

    // make zombie not catch fire during day
    @SubscribeEvent
    public static void onMobTick(LivingTickEvent event) {
        if(event.getEntity() instanceof Zombie zombie) {
            BlockPos eyePosition = BlockPos.containing(zombie.getX(), zombie.getEyeY(), zombie.getZ());
            if(zombie.level().isDay() && zombie.level().canSeeSky(eyePosition) && zombie.isOnFire() && !zombie.isInLava()) {
                zombie.clearFire();
            }
        }
    }

    @SubscribeEvent
    public static void onHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Zombie zombie && event.getSource().is(DamageTypes.PLAYER_ATTACK)) {
            int level = getEntityLevel(zombie);
            System.out.println("### Before Attack: Level = " + level);

            setEntityData(zombie, level + 1, getEntityAggro(zombie));
            System.out.println("### After Attack: Level = " + getEntityLevel(zombie));


//            CompoundTag persistentData = zombie.getPersistentData().getCompound("ForgeData");
//
//            // Log before changing the level
//            int oldLevel = persistentData.getInt("ZombieLevel");
//            System.out.println("### Before Attack: Level = " + oldLevel);
//
//            // Increase level (example logic)
//            int newLevel = oldLevel + 1;
//            persistentData.putInt("ZombieLevel", newLevel);
//            zombie.setPersistenceRequired();
//
//            System.out.println("### Zombie Level Increased: " + newLevel);

            // **Send update to client**
            //PacketHandler.sendToAllClients(new ZombieLevelSyncPacket(zombie.getId(), newLevel));
        }
    }

    // but - when zombies load in the world its not updated yet

    // cancel damage + animation
    @SubscribeEvent
    public static void onLivingGetAttackedEvent(LivingAttackEvent event) {
        if (event.getEntity().getType() == EntityType.ZOMBIE) {
            DamageSource source = event.getSource();

            //System.out.println("Zombie damgae source: " + source.toString());

            if (source.is(DamageTypes.LAVA) || source.is(DamageTypes.HOT_FLOOR) || source.is(DamageTypes.CAMPFIRE)) {
                if(source.is(DamageTypes.IN_FIRE)) {
                    event.getEntity().clearFire();
                    event.getEntity().extinguishFire();
                }
                event.setCanceled(true);
            }

            if(source.is(DamageTypes.CACTUS) || source.is(DamageTypes.FALLING_BLOCK) || source.is(DamageTypes.IN_WALL)) {
                event.setCanceled(true);
            }
        }
    }

    // note: zombies should turn to drowned mob, so take in mind
    @SubscribeEvent
    public static void onLivingDrownEvent(LivingDrownEvent event) {
        if(event.getEntity() instanceof Monster monster) {
            event.setBubbleCount(0);
            event.setDrowning(false);
        }
    }

    // cancel fall event for zombie
    @SubscribeEvent
    public static void onLivingFallEvent(LivingFallEvent event) {
        if(event.getEntity().getType() == EntityType.ZOMBIE) {
            event.setCanceled(true);
        }
    }

    // chance on spawn to make it rare
    @SubscribeEvent
    public static void onZombieSpawn(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide())
            return;

        if (event.getEntity() instanceof Zombie zombie) {
            if(RANDOM.nextFloat() < 0.10) {
                ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
                zombie.setItemSlot(EquipmentSlot.HEAD, helmet);
                zombie.setDropChance(EquipmentSlot.HEAD, 0.0F); // Prevents dropping the item

            }
        }

        if (event.getEntity() instanceof Zombie zombie) {

            // assign level
            int level = getEntityLevel(zombie);
            if(level == -1) {
                // decide if aggro or not
                boolean isAggro = RANDOM.nextFloat() < 0.20;
                setEntityData(zombie, 1, isAggro);
                if(isAggro) {
                    System.out.println("### Set aggro: " + zombie.getId());
                }
            } else {
                zombie.level().getServer().execute(() -> {
                    boolean isAggro = getEntityAggro(zombie);
                    PacketHandler.sendToAllClients(new ZombieLevelSyncPacket(zombie.getId(), level, isAggro));
                    System.out.println("### Sending Sync Packet to Client: " + level);
                });
            }
        }
    }

    // **Ensure Data is Saved When the World Saves**
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<?> event) {
        if (event.getObject() instanceof Zombie zombie) {
            int level = getEntityLevel(zombie);
            if(level > 0)
                System.out.println("### onAttachCapabilities Zombie Level: " + level);

        }
    }

    public static void setEntityData(Mob livingEntity, int level, boolean isAggro) {
        CompoundTag persistentData = livingEntity.getPersistentData();
        persistentData.putInt(LEVEL_TAG, level);
        persistentData.putBoolean(AGGRO_TAG, isAggro);
        PacketHandler.sendToAllClients(new ZombieLevelSyncPacket(livingEntity.getId(), level, isAggro));
        livingEntity.setPersistenceRequired();
    }

    public static int getEntityLevel(LivingEntity livingEntity) {
        CompoundTag persistentData = livingEntity.getPersistentData();
        return persistentData.contains(LEVEL_TAG) ? persistentData.getInt(LEVEL_TAG) : -1;
    }

    public static boolean getEntityAggro(LivingEntity livingEntity) {
        CompoundTag persistentData = livingEntity.getPersistentData();
        return persistentData.contains(AGGRO_TAG) && persistentData.getBoolean(AGGRO_TAG);
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
