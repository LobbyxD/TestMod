package com.bruno.testmod.testcurios;

import com.bruno.testmod.network.PacketHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InventoryButtonHandler {
    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen inventoryScreen) {
            int x = inventoryScreen.getGuiLeft() + 130; // Adjust position
            int y = inventoryScreen.getGuiTop() + 5;

            Button customButton = Button.builder(Component.literal("S"), btn ->
                    PacketHandler.sendToServer(new OpenCustomSlotPacket())
            ).pos(x, y).size(20, 20).build();


            event.addListener(customButton);
        }
    }
}

