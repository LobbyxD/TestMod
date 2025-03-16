package com.bruno.testmod.screen.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CustomButton extends Button {
    private final ResourceLocation defaultTexture;
    private final ResourceLocation hoverTexture;
    private final ResourceLocation focusedTexture;

    public CustomButton(int x, int y, int width, int height, Component message, OnPress onPress,
                        ResourceLocation defaultTexture, ResourceLocation hoverTexture, ResourceLocation focusedTexture) {
        super(x, y, width, height, message, onPress, Button.DEFAULT_NARRATION);
        this.defaultTexture = defaultTexture;
        this.hoverTexture = hoverTexture;
        this.focusedTexture = focusedTexture;
    }


    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);

        ResourceLocation textureToBind;
        if (this.isFocused()) {
            textureToBind = this.focusedTexture;
        } else if (this.isHoveredOrFocused()) {
            textureToBind = this.hoverTexture;
        } else {
            textureToBind = this.defaultTexture;
        }

        Minecraft.getInstance().getTextureManager().bindForSetup(textureToBind);
        guiGraphics.blit(textureToBind, this.getX(), this.getY(), 0, 0, this.width, this.height, this.width, this.height);
        int textColor = getFGColor();
        this.renderString(guiGraphics, Minecraft.getInstance().font, textColor | Mth.ceil(this.alpha * 255.0F) << 24);

        //guiGraphics.blit(this.texture, this.getX(), this.getY(), 0, 0, this.width, this.height, this.width, this.height);
        //guiGraphics.blit(this.getX(), this.getY(), 0, 0, this.width, this.height, this.width, this.height);

    }
}