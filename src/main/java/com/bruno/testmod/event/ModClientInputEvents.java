package com.bruno.testmod.event;
import com.bruno.testmod.TestMod;
import com.bruno.testmod.entity.client.screen.CustomChestScreen;
import com.bruno.testmod.screen.custom.JobScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, value = Dist.CLIENT)
public class ModClientInputEvents {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft minecraft = Minecraft.getInstance();

            if (TestMod.OPEN_JOB_GUI_KEY.consumeClick()) {
                if (minecraft.screen instanceof JobScreen) {
                    minecraft.setScreen(null); // Close the current screen
                } else {
                    minecraft.setScreen(new JobScreen()); // Open the custom screen
                }
            }

            if (TestMod.CHANGE_JOB_KEY.consumeClick()) {
                Player player = Minecraft.getInstance().player;
                CompoundTag persistentData = player.getPersistentData();
                String job = persistentData.contains("PLAYER_CLASS") ? persistentData.getString("PLAYER_CLASS") : "Beginner";
                if(job.equals("Beginner"))
                    persistentData.putString("PLAYER_CLASS", "Scientist");
                else if(job.equals("Scientist"))
                    persistentData.putString("PLAYER_CLASS", "Ninja");
                else if(job.equals("Ninja"))
                    persistentData.putString("PLAYER_CLASS", "Archer");
                else if(job.equals("Archer"))
                    persistentData.putString("PLAYER_CLASS", "Mage");
                else if(job.equals("Mage"))
                    persistentData.putString("PLAYER_CLASS", "Swordman");
                else
                    persistentData.putString("PLAYER_CLASS", "Beginner");
            }

            if (TestMod.OPEN_CUSTOM_SCREEN.consumeClick()) {
                if (minecraft.screen instanceof CustomChestScreen) {
                    minecraft.setScreen(null); // Close the current screen
                } else {
                    minecraft.setScreen(new CustomChestScreen()); // Open the custom chest screen
                }
                System.out.println("@@@@@@@@@@@@@@ Pressed H");
            }
        }
    }
}

