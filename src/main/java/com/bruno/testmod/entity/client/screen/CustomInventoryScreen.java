package com.bruno.testmod.entity.client.screen;

import com.bruno.testmod.TestMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class CustomInventoryScreen extends InventoryScreen {
    private static final ResourceLocation CUSTOM_INVENTORY_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/gui/custom_inventory.png");

    public CustomInventoryScreen(Player player) {
        super(player);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTicks, mouseX, mouseY);

        // Bind your custom texture
        guiGraphics.blit(CUSTOM_INVENTORY_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // Draw the custom slot background at the desired position
        int xPosition = this.leftPos + 176; // Adjust based on your layout
        int yPosition = this.topPos + 8;    // Adjust based on your layout
        guiGraphics.blit(CUSTOM_INVENTORY_TEXTURE, xPosition, yPosition, 0, 0, 16, 16); // Assuming the slot texture is 18x18
    }
}

