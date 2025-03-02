package com.bruno.testmod.entity.client.slot;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;

public class CustomSlot extends Slot {
    public CustomSlot(Container inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        // Define logic to determine if the item can be placed in this slot
        return true; // Allow all items for now
    }

    @Override
    public boolean mayPickup(Player player) {
        // Define logic for whether the player can pick up items from this slot
        return true;
    }
}

