package com.bruno.testmod.testcurios;

import com.bruno.testmod.entity.client.menu.ModMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CustomSlotMenu extends AbstractContainerMenu {
    private final ItemStackHandler inventory;

    public CustomSlotMenu(int id, Inventory playerInv, FriendlyByteBuf data) {
        this(id, playerInv, new ItemStackHandler(1));
    }

    public CustomSlotMenu(int id, Inventory playerInv, ItemStackHandler inv) {
        super(ModMenus.CUSTOM_SLOT_MENU.get(), id);
        this.inventory = inv;

        // Add a single slot
        this.addSlot(new SlotItemHandler(inventory, 0, 80, 35));

        // Player inventory slots
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Player hotbar
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY; // Prevents shift-click moving
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
