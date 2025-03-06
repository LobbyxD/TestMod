package com.bruno.testmod.entity.client.screen;
import com.bruno.testmod.TestMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

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
        pGuiGraphics.drawString(this.font, this.title, (this.width - titleWidth) / 2, this.height/3 + 20, 0xFFFFFF);

        Player player = Minecraft.getInstance().player;
        String job = "null class";
        if(player != null)
            job = getPlayerJob(player);

        int jobTitleWidth = this.font.width(job);
        pGuiGraphics.drawString(this.font, job, (this.width - jobTitleWidth) / 2, this.height / 3 + 40, 0xFFFFFF);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Return true if the screen should pause the game
    }

    private String getPlayerJob(Player player) {
        CompoundTag persistentData = player.getPersistentData();
        return persistentData.contains("PLAYER_CLASS") ? persistentData.getString("PLAYER_CLASS") : "Beginner";
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (TestMod.OPEN_CUSTOM_GUI_KEY.matches(keyCode, scanCode)) {
            this.onClose(); // Close the GUI
            return true; // Indicate that the key press was handled
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

}
