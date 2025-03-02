package com.bruno.testmod.entity.client.menu;

import com.bruno.testmod.TestMod;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CustomChestMenu extends AbstractContainerMenu {
    private final Container container;

    public CustomChestMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(27)); // 27 slots for a chest-like container
    }

    public CustomChestMenu(int id, Inventory playerInventory, Container container) {
        super(ModMenu.CUSTOM_CHEST.get(), id);
        this.container = container;

        // Add custom inventory slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(container, j + i * 9, 8 + j * 18, 18 + i * 18));
            }
        }

        // Add player inventory slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + (i * 9) + 9, 8 + (j * 18), 84 + (i * 18)));
            }
        }

        // Add hotbar slots
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + (i * 18), 142));
        }
    }

    // shift + click
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }
}
