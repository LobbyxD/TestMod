package com.bruno.testmod.event;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.block.ModBlocks;
import com.bruno.testmod.block.entity.custom.LightningStrikeBlockEntity;
import com.bruno.testmod.entity.client.menu.CustomChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.living.MobSpawnEvent;


@Mod.EventBusSubscriber(modid = TestMod.MOD_ID)
public class EntitySpawnHandler {
    private static final Component CONTAINER_TITLE = Component.translatable("container.custom_chest");

    @SubscribeEvent
    public static void onCheckSpawn(MobSpawnEvent.SpawnPlacementCheck event) {
        EntityType entity = event.getEntityType();

        if(entity == EntityType.ZOMBIE) {
            // Calculate the position where the block would be placed
            BlockPos placePos = event.getPos();

            // Iterate through all positions within the specified radius
            var xz = LightningStrikeBlockEntity.SCAN_RADIUS_XZ;
            var y = LightningStrikeBlockEntity.SCAN_RADIUS_Y;

            for (BlockPos pos : BlockPos.betweenClosed(
                    placePos.offset(-xz, -y, -xz),
                    placePos.offset(xz, y, xz))) {
                if (pos.equals(placePos)) continue; // Skip the placement position
                BlockState state = event.getLevel().getBlockState(pos);
                if (state.getBlock() == ModBlocks.HIGH_BLOCK.get()) {
                    // Another instance of the block is found within the radius
                    event.setResult(Event.Result.DENY);
                    return;
                }
            }
            }

        //BlockPos eyePosition = BlockPos.containing(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
        if (entity == EntityType.ZOMBIE) {
            event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public static void onCheckSpawnPos(MobSpawnEvent.PositionCheck event) {
        if (event.getEntity() instanceof Zombie zombie) {
            event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(MobSpawnEvent.FinalizeSpawn event) {
        if (event.getEntity() instanceof Zombie zombie || event.getEntity().getType() == EntityType.ZOMBIE) {

        }
    }

    // zombie passive or aggressive
    @SubscribeEvent
    public static void onEntityJoinWorld2(EntityJoinLevelEvent event) {

    }
}
