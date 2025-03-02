package com.bruno.testmod.entity.client.menu;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class CustomChestProvider implements MenuProvider {
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.custom_chest");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new CustomChestMenu(id, playerInventory);
    }
}

// Somewhere in your code, e.g., in an event handler or item interaction:
//Player player = ...;
//        NetworkHooks.openScreen((ServerPlayer) player, new CustomChestProvider(), buf -> {});
