package com.bruno.testmod.event;

import com.bruno.testmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockPlacementHandler {
    private static final int RADIUS = 20;

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) {
            // Handle client-side logic
            if (shouldCancelPlacement(event)) {
                event.setCancellationResult(InteractionResult.FAIL);
                event.setCanceled(true);
            }
        } else {
            // Handle server-side logic
            if (shouldCancelPlacement(event)) {
                event.setCancellationResult(InteractionResult.FAIL);
                event.setCanceled(true);
                // Notify the player
                if (event.getEntity() instanceof ServerPlayer player) {
                    player.displayClientMessage(Component.literal("You cannot place this block near another instance."), true);
                }
            }
        }
    }

    private static boolean shouldCancelPlacement(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        ItemStack heldItem = player.getItemInHand(event.getHand());
        if (heldItem.getItem() instanceof BlockItem blockItem && blockItem.getBlock() == ModBlocks.HIGH_BLOCK.get()) {
            Block blockToPlace = blockItem.getBlock();
            BlockPos clickedPos = event.getPos();

            // Calculate the position where the block would be placed
            BlockHitResult hitResult = event.getHitVec();
            BlockPos placePos = clickedPos.relative(hitResult.getDirection());

            // Iterate through all positions within the specified radius
            for (BlockPos pos : BlockPos.betweenClosed(
                    placePos.offset(-RADIUS, -RADIUS, -RADIUS),
                    placePos.offset(RADIUS, RADIUS, RADIUS))) {
                if (pos.equals(placePos)) continue; // Skip the placement position
                BlockState state = event.getLevel().getBlockState(pos);
                if (state.getBlock() == blockToPlace) {
                    // Another instance of the block is found within the radius
                    return true;
                }
            }
        }
        return false;
    }
}