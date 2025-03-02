package com.bruno.testmod.event;
import com.bruno.testmod.TestMod;
import com.bruno.testmod.entity.client.screen.CustomChestScreen;
import com.bruno.testmod.entity.client.screen.CustomScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, value = Dist.CLIENT)
public class ModClientInputEvents {

//    @SubscribeEvent
//    public static void onScreenOpen(ScreenEvent.Opening event) {
//        if (event.getScreen() instanceof InventoryScreen) {
//            Minecraft mc = Minecraft.getInstance();
//            event.setNewScreen(new CustomPlayerScreen(
//                    new CustomPlayerMenu(mc.player.getInventory(), !mc.level.isClientSide, mc.player),
//                    mc.player.getInventory(),
//                    mc.player.getDisplayName()
//            ));
//        }
//    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (TestMod.OPEN_CUSTOM_GUI_KEY.consumeClick()) {
                Minecraft.getInstance().setScreen(new CustomScreen());
            }

            if (TestMod.OPEN_CUSTOM_SCREEN.consumeClick()) {
                Minecraft.getInstance().setScreen(new CustomChestScreen());
                System.out.println("@@@@@@@@@@@@@@ Pressed H");
            }
        }
    }
}

