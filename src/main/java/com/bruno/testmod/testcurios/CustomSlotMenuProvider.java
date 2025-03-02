package com.bruno.testmod.testcurios;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

import javax.annotation.Nullable;

public class CustomSlotMenuProvider implements MenuProvider {
    @Override
    public Component getDisplayName() {
        return Component.literal("Custom Slot");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new CustomSlotMenu(id, playerInventory, new net.minecraftforge.items.ItemStackHandler(1));
    }

    // Fix: Directly open the screen using player.openMenu()
    public static void open(ServerPlayer player) {
        player.openMenu(new CustomSlotMenuProvider());
    }
}
