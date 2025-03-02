package com.bruno.testmod.entity.client.menu;

import com.bruno.testmod.entity.client.slot.CustomSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.Container;

public class CustomInventoryMenu extends InventoryMenu {
    private static final int CUSTOM_SLOT_INDEX = 46; // Index after the default 45 slots

    public CustomInventoryMenu(Inventory playerInventory, boolean localWorld, Player player) {
        super(playerInventory, localWorld, player);

        // Add custom slot at desired position
        int xPosition = 176; // X position outside the normal inventory bounds
        int yPosition = 8;   // Y position at the top right corner
        this.addSlot(new CustomSlot(playerInventory, CUSTOM_SLOT_INDEX, xPosition, yPosition));
    }
}

