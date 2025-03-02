package com.bruno.testmod.entity.client.container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.SimpleContainer;

public class CustomPlayerInventory extends SimpleContainer {
    public static final int SLOT_COUNT = 1; // Number of custom slots

    public CustomPlayerInventory() {
        super(SLOT_COUNT);
    }

    @Override
    public boolean stillValid(Player player) {
        return true; // Adjust validation as needed
    }
}

