package com.bruno.testmod.skills;

import com.bruno.testmod.event.damagefloat.ModParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber
public class ZombieRightClickHandler {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onPlayerRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof Zombie zombie && event.getHand() == InteractionHand.MAIN_HAND) {
            Player player = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.isEmpty()) {
                teleportPlayerBehindEntity(player, zombie);
                rotatePlayerToFaceEntity(player, zombie);
                dealDamageToEntity(player, zombie, 10.0F); // 10 hit points (5 hearts)
            }

        }

        if (!event.getLevel().isClientSide() && event.getTarget() instanceof Zombie zombie) {
            Player player = event.getEntity();

            // Check if the player is holding a stick in the main hand
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.is(Items.STICK)) {
                // Apply knockback and damage
                applyKnockbackAndDamage(zombie, player);
            }
            else if(mainHandItem.is(Items.BLAZE_ROD)) {
                applyShards(zombie, player);
            }
        }

        if (event.getTarget() instanceof Zombie zombie) {
            Player player = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.is(Items.ARROW)) {
                spawnHealingParticlesT2(player);
            }
        }
    }

    public static void applyShards(Zombie zombie, Player player) {
        Level level = zombie.level();
        if (level instanceof ServerLevel serverLevel) {
            double x = zombie.getX();
            double y = zombie.getY() + 1; // Slightly above ground
            double z = zombie.getZ();

            int particleCount = 30;
            double radius = 0.5; // Small starting radius
            for (int i = 0; i < particleCount; i++) {
                double angle = 2 * Math.PI * i / particleCount;
                double particleX = x + radius * Math.cos(angle);
                double particleZ = z + radius * Math.sin(angle);

                // Spawn custom shard particles with outward motion
                serverLevel.sendParticles(
                        ModParticles.SHARD_PARTICLE.get(),
                        particleX, y, particleZ, // Position
                        1, // Count
                        Math.cos(angle) * 0.2, 0.1, Math.sin(angle) * 0.2, // Velocity
                        0 // Speed modifier (not used in custom particle)
                );
            }

            // Define the radius and damage
            radius = 4.0;
            float initialDamage = 5.0f;
            float delayedDamage = 2.5f;

            // Get all nearby zombies within radius
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, new AABB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius))) {
                if (entity instanceof Zombie nearbyZombie) {
                    nearbyZombie.hurt(player.damageSources().playerAttack(player), initialDamage);

                    // Schedule delayed damage after 350ms (7 ticks)
                    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                    executor.schedule(() -> hurtZombie(zombie, player, delayedDamage), 350, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    public static void hurtZombie(Zombie zombie, Player player, float delayedDamage) {
        zombie.hurt(player.damageSources().playerAttack(player), delayedDamage);
    }

    public static void spawnHealingParticlesT2(Player player) {
        player.displayClientMessage(Component.literal("Healing test c"), true);
//        if (player.level() instanceof ServerLevel serverLevel) {
//            double radius = 1; // Radius of the circle
//            int particleCount = 10; // Number of particles
//            double centerX = player.getX();
//            double centerY = player.getY() + 0.5; // Slightly above the player's position
//            double centerZ = player.getZ();
//
//            for (int i = 0; i < particleCount; i++) {
//                double angle = 2 * Math.PI * i / particleCount; // Calculate angle for each particle
//                double offsetX = radius * Math.cos(angle);
//                double offsetZ = radius * Math.sin(angle);
//
//                serverLevel.sendParticles(
//                        ModParticles.HEALING_PARTICLE.get(),
//                        centerX + offsetX, centerY, centerZ + offsetZ, // Position
//                        1, // Count
//                        0.0, 0.1, 0.0, // Offset for spread
//                        0.0 // Speed
//                );
//            }
//        }

        if (player.level() instanceof ServerLevel serverLevel) {
            double centerX = player.getX();
            double centerY = player.getY() + 0.5;
            double centerZ = player.getZ();
            double radius = 1.0; // Radius of the circle
            int particleCount = 18; // Number of particles

            for (int i = 0; i < particleCount; i++) {
                double initialAngle = 2 * Math.PI * i / particleCount;
                double x = centerX + radius * Math.cos(initialAngle);
                double z = centerZ + radius * Math.sin(initialAngle);
                double y = centerY;

                serverLevel.sendParticles(
                        ModParticles.HEALING_PARTICLE.get(),
                        x, y, z, // Position
                        1, // Count
                        0, 0, 0, // Velocity components
                        0 // Speed (not used when direction is specified)
                );
            }
        }
    }

    private static void applyKnockbackAndDamage(Zombie zombie, Player player) {
        // Define knockback strength
        double knockbackStrength = 0.3;

        // Calculate knockback direction (away from the player)
        double deltaX = zombie.getX() - player.getX();
        double deltaZ = zombie.getZ() - player.getZ();
        double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        if (horizontalDistance > 0.0) {
            // Normalize direction and apply horizontal knockback
            zombie.setDeltaMovement(
                    deltaX / horizontalDistance * knockbackStrength,
                    0.6, // Vertical knockback
                    deltaZ / horizontalDistance * knockbackStrength
            );
        }

        // Apply damage to the zombie
        float damageAmount = 5.0F; // Adjust the damage as needed
        zombie.hurt(player.damageSources().playerAttack(player), damageAmount);
    }

    private static void teleportPlayerBehindEntity(Player player, Zombie zombie) {
        // Calculate the vector pointing from the zombie to the player
        Vec3 direction = player.position().subtract(zombie.position()).normalize();

        // Calculate the position behind the zombie
        double distanceBehind = 1.0; // Distance to teleport behind
        Vec3 behindPosition = zombie.position().subtract(direction.scale(distanceBehind));

        // Set the player's position to the calculated position
        player.teleportTo(behindPosition.x, behindPosition.y, behindPosition.z);
    }

    private static void rotatePlayerToFaceEntity(Player player, Zombie zombie) {
        // Calculate the direction vector from the player to the zombie
        Vec3 direction = zombie.position().subtract(player.position()).normalize();

        // Calculate the yaw (horizontal rotation)
        double yaw = Math.toDegrees(Math.atan2(direction.z, direction.x)) + 90.0;

        // Calculate the pitch (vertical rotation)
        double distance = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
        double pitch = -Math.toDegrees(Math.atan2(direction.y, distance));

        // Set the player's rotation
        player.setYRot((float) yaw);
        player.setXRot((float) pitch);
    }

    private static void dealDamageToEntity(Player player, Zombie zombie, float damage) {
        zombie.hurt(player.damageSources().playerAttack(player), damage);
    }




    // double attack - created custom damage for the following attack
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // Check if the source of damage is a player
        if (!event.getSource().is(ModDamageSources.CUSTOM_DAMAGE) && event.getSource().getEntity() instanceof Player player) {
            // Check if the player is attacking with bare hands
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.isEmpty()) {
                // Check if the entity being attacked is a zombie
                if (event.getEntity() instanceof Zombie zombie) {
                    // Schedule a second hit after 0.2 seconds (4 ticks)
                    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                    executor.schedule(() -> execute(zombie, player, 5f), 350, TimeUnit.MILLISECONDS);
                    //scheduleSecondHit(zombie, player);
                }
            }
        }
    }

    public static void execute(LivingEntity target, Player attacker, float damageAmount) {
        if (!target.level().isClientSide && target.isAlive()) {
            // Deal damage to the target
            //target.hurt(attacker.damageSources().playerAttack(attacker), amount); // 1.0F represents half a heart
            ServerLevel serverLevel = (ServerLevel) target.level();
            DamageSource customDamageSource = ModDamageSources.createCustomDamage(serverLevel, attacker);
            target.hurt(customDamageSource, damageAmount);

            double deltaX = target.getX() - attacker.getX();
            double deltaZ = target.getZ() - attacker.getZ();
            double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

            if (horizontalDistance > 0.0) {
                // Normalize direction and apply horizontal knockback
                target.setDeltaMovement(
                        deltaX / horizontalDistance * 0.2f,
                        0.1, // Vertical knockback
                        deltaZ / horizontalDistance * 0.2f
                );

            }
        }
    }
}
