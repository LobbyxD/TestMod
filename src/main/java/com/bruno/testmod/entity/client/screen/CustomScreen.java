package com.bruno.testmod.entity.client.screen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CustomScreen extends Screen {
    public CustomScreen() {
        super(Component.translatable("screen.testmod.custom_screen"));
    }

    @Override
    protected void init() {
        // Initialize GUI components here
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Fill the screen with a solid color (e.g., semi-transparent dark gray)
        guiGraphics.fillGradient(this.width/4, this.height/3, this.width*3/4, this.height*2/3, 0x94949499, 0x94949499);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        int titleWidth = this.font.width(this.title);
        // Draw the title string at the center of the screen
        pGuiGraphics.drawString(this.font, this.title, (this.width - titleWidth) / 2, 20, 0xFFFFFF);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Return true if the screen should pause the game
    }
}
