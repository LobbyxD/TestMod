package com.bruno.testmod.screen.custom;
import com.bruno.testmod.TestMod;
import com.bruno.testmod.screen.button.CustomButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class JobScreen extends Screen {

    private int activeTab = 0; // Default to the first tab
    final int bgTextureWidth = 975 / 2;
    final int bgTextureHeight = 466 / 2;
    private final int COLOR_DEFAULT = 0xFFFFFF;
    int screenX;
    int screenY;

    public JobScreen() {
        super(Component.translatable("screen.testmod.custom_screen"));
    }

    @Override
    protected void init() {
        super.init();
        screenX = (this.width - bgTextureWidth) / 2;
        screenY = (this.height - bgTextureHeight) / 2;

        // Create Tab 1 Button
        Button tab1Button = Button.builder(Component.literal("Tab 1"), button -> switchTab(1))
                .pos(screenX, screenY - 20)
                .size(50, 20)
                .build();
        this.addRenderableWidget(tab1Button);

        // Create Tab 2 Button
        Button tab2Button = Button.builder(Component.literal("Tab 2"), button -> switchTab(2))
                .pos(screenX + 60, screenY - 20) // Adjust position as needed
                .size(50, 20)
                .build();
        this.addRenderableWidget(tab2Button);

        ResourceLocation defaultTexture = ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/gui/buttons/default_button.png");
        ResourceLocation hoverTexture = ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/gui/buttons/hover_button.png");
        ResourceLocation focusedTexture = ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/gui/buttons/focused_button.png");
        Button customButton = new CustomButton(screenX + 120, screenY - 20,
                50, 20,
                Component.literal("Tab 1"),
                button -> switchTab(1), defaultTexture, hoverTexture, focusedTexture);
        this.addRenderableWidget(customButton);

        // set default to 1
        switchTab(1);
    }

    private void switchTab(int tabIndex) {
        activeTab = tabIndex;
        // Refresh or update GUI elements based on the active tab
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Fill the screen with a solid color (e.g., semi-transparent dark gray)
        // start at - 480, 360     ends at - 1440, 720

        ResourceLocation bgTexture = ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/gui/screens/job_main_screen.png");
        Minecraft.getInstance().getTextureManager().bindForSetup(bgTexture);
        //pGuiGraphics.blitSprite(classImage);
        // pGuiGraphics.blit(classImage, imageX, imageY, 0, 0, imageWidth, imageHeight, 128, 128);
        guiGraphics.blit(bgTexture, screenX, screenY, 0, 0, bgTextureWidth, bgTextureHeight, bgTextureWidth, bgTextureHeight);
        //guiGraphics.fillGradient(screenX, screenY, screenXEnd, screenYEnd, 0x94949499, 0x94949499);
    }

//    @Override
//    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
//        ResourceLocation backgroundTexture = ResourceLocation.fromNamespaceAndPath("testmod", "textures/gui/custom_background.png");
//        guiGraphics.bindTexture(backgroundTexture);
//        guiGraphics.blit(0, 0, this.width, this.height, 0, 0, 256, 256, 256, 256);
//    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        Player player = Minecraft.getInstance().player;
        String playerClass = getPlayerClass(player);
        if (activeTab == 1) {
            renderPlayerIcon(pGuiGraphics, playerClass);
            renderText(pGuiGraphics, playerClass, screenX + 70, screenY + 110, COLOR_DEFAULT);
        }
        else if (activeTab == 2) {
            int titleWidth = this.font.width(this.title);
            pGuiGraphics.drawString(this.font, "tab 2", (this.width - titleWidth) / 2, this.height/3 + 20, 0xFFFFFF);
        }
    }

    private void renderPlayerIcon(GuiGraphics pGuiGraphics, String playerClass) {
        ResourceLocation classImage = getClassImage(playerClass);
        Minecraft.getInstance().getTextureManager().bindForSetup(classImage);
        //pGuiGraphics.blitSprite(classImage);
        int imageWidth = 128 / 2 - 10;
        int imageHeight = 128 / 2 - 10;
        pGuiGraphics.blit(classImage, screenX + 40, screenY + 45, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
    }

    private void renderText(GuiGraphics pGuiGraphics, String text, int pX, int pY, int color) {
        int titleWidth = this.font.width(text);
        pGuiGraphics.drawString(this.font, text, pX - (titleWidth / 2), pY, color);
    }



    //@Override
    public void render2(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        // title
        int titleWidth = this.font.width(this.title);
        pGuiGraphics.drawString(this.font, this.title, (this.width - titleWidth) / 2, this.height/3 + 20, 0xFFFFFF);

        Player player = Minecraft.getInstance().player;

        // player class image
        String playerClass = getPlayerClass(player);
        ResourceLocation classImage = getClassImage(playerClass);
        Minecraft.getInstance().getTextureManager().bindForSetup(classImage);
        //pGuiGraphics.blitSprite(classImage);
        int imageWidth = 128;
        int imageHeight = 128;
        int imageX = screenX;
        int imageY = screenY;
        pGuiGraphics.blit(classImage, imageX, imageY, 0, 0, imageWidth, imageHeight, 128, 128);

        // player title and icon
        int textX = imageX + imageWidth + 10;
        int textY = imageY;
        pGuiGraphics.drawString(this.font, playerClass, textX, textY, 0xFFFFFF);

//        ResourceLocation classIcon = getClassIcon(playerClass);
//        pGuiGraphics.bindTexture(classIcon);
//        int iconSize = 16;
//        pGuiGraphics.blit(textX + this.font.width(playerClass) + 5, textY, iconSize, iconSize, 0, 0, iconSize, iconSize, iconSize, iconSize);


        // player health and armor
        float currentHealth = player.getHealth();
        float maxHealth = player.getMaxHealth();
        int currentArmor = player.getArmorValue();
        int maxArmor = 20; // Default maximum armor value

        pGuiGraphics.drawString(this.font, "Health: " + currentHealth + " / " + maxHealth, textX, textY + 20, 0xFFFFFF);
        pGuiGraphics.drawString(this.font, "Armor: " + currentArmor + " / " + maxArmor, textX, textY + 40, 0xFFFFFF);


        // player stats
        int statsY = textY + 60;
        pGuiGraphics.drawString(this.font, "Strength: " + getPlayerAttribute(player, "strength"), textX, statsY, 0xFFFFFF);
        pGuiGraphics.drawString(this.font, "Accuracy: " + getPlayerAttribute(player, "accuracy"), textX, statsY + 20, 0xFFFFFF);

//        int jobTitleWidth = this.font.width(playerClass);
//        pGuiGraphics.drawString(this.font, playerClass, (this.width - jobTitleWidth) / 2, this.height / 3 + 40, 0xFFFFFF);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    private String getPlayerAttribute(Player player, String strength) {
        return "100";
    }

    private String getPlayerClass(Player player) {
        String job = "null class";
        if(player != null)
            job = getPlayerJob(player);
        return job;
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
        if (TestMod.OPEN_JOB_GUI_KEY.matches(keyCode, scanCode)) {
            this.onClose(); // Close the GUI
            return true; // Indicate that the key press was handled
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private ResourceLocation getClassImage(String playerClass) {
        //System.out.println("looking for textures/gui/classes/" + playerClass.toLowerCase() + ".png");
        return ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/gui/classes/" + playerClass.toLowerCase() + ".png");
    }

    private ResourceLocation getClassIcon(String playerClass) {
        return ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/gui/icons/" + playerClass.toLowerCase() + "_icon.png");
    }

}
