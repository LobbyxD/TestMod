package com.bruno.testmod.event;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.entity.client.menu.CustomChestMenu;
import com.bruno.testmod.entity.client.menu.ModMenu;
import com.bruno.testmod.entity.client.screen.CustomChestScreen;
import com.bruno.testmod.entity.client.screen.CustomInventoryScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
//        MenuScreens.register(TestMod.CUSTOM_CHEST.get(), CustomChestScreen::new);
        MenuScreens.<CustomChestMenu, CustomChestScreen>register(
                ModMenu.CUSTOM_CHEST.get(),
                (menu, inventory, title) -> new CustomChestScreen(menu, inventory, title)
        );

//        MenuScreens.register(ModMenu.CUSTOM_INVENTORY_MENU.get(), CustomInventoryScreen::new);
    }
}
