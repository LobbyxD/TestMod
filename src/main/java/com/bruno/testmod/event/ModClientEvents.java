package com.bruno.testmod.event;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.entity.client.screen.CustomInventoryScreen;
import com.bruno.testmod.item.ModItems;
import com.bruno.testmod.network.PacketHandler;
import com.bruno.testmod.network.packets.RequestZombieLevelPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

    private static final double MAX_RENDER_DISTANCE = 10.0; // Max distance for rendering
    private static final int BAR_LENGTH = 10; // Number of segments in the health bar

    @SubscribeEvent
    public static void onComputerFovModiferEvent(ComputeFovModifierEvent event) {
        if (event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() == ModItems.BRUNITE_BOW.get()) {
            float fovModifer = 1f;
            int ticksUsingItem = event.getPlayer().getTicksUsingItem();
            float deltaTicks = (float)ticksUsingItem / 20f;
            if (deltaTicks > 1f) {
                deltaTicks = 1f;
            } else {
                deltaTicks *= deltaTicks;
            }
            fovModifer *= 1f - deltaTicks * 0.15f;
            event.setNewFovModifier(fovModifer);
        }
    }

    // when client loads zombie, ask server to sync its level
    @SubscribeEvent
    public static void onZombieJoinClient(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() && event.getEntity() instanceof Zombie zombie) {
            System.out.println("### CLIENT: Detected Zombie ID " + zombie.getId() + " joining the world. Requesting level...");
            PacketHandler.sendToServer(new RequestZombieLevelPacket(zombie.getId()));
        }
    }

    // health bar
    @SubscribeEvent
    public static void onRenderZombie(RenderLivingEvent.Post<Zombie, ?> event) {
        if (event.getEntity() instanceof Zombie zombie) {
            PoseStack poseStack = event.getPoseStack();
            MultiBufferSource bufferSource = event.getMultiBufferSource();
            Minecraft mc = Minecraft.getInstance();

            if (mc.player == null || zombie.isInvisible()) return;

            double distance = mc.player.distanceTo(zombie);
            if (distance > MAX_RENDER_DISTANCE) return;

            float health = zombie.getHealth();
            float maxHealth = zombie.getMaxHealth();
            float healthPercentage = health / maxHealth;

            // **Fetch the latest zombie level from PersistentData**
//            CompoundTag persistentData = zombie.getPersistentData().getCompound("ForgeData");
//            int level = persistentData.getInt(ModEvents.LEVEL_TAG); // Ensure this always fetches the latest level
            int level = ModEvents.getEntityLevel(zombie);
            if(level == -1) {
                System.out.println("ERROR in ModClient mob is level -1. Mob ID = " + zombie.getId());
            }
            boolean isAggro = ModEvents.getEntityAggro(zombie);

            // **Generate Health Bar**
            int filledBars = health == 0 ? 0 : Math.max(1, (int) (BAR_LENGTH * healthPercentage));
            int emptyBars = BAR_LENGTH - filledBars;
            String filled = "§a" + "█".repeat(filledBars);
            String empty = "§7" + "▒".repeat(emptyBars);
            String healthText = filled + empty;

            // **Display Entity Name and Level**
            String nameColor = isAggro ? "§c" : "§f";
            String entityName = nameColor + "Lv. " + level + " " + zombie.getDisplayName().getString();

            Font font = mc.font;

            poseStack.pushPose();
            poseStack.translate(0.0D, zombie.getBbHeight() + 0.6F, 0.0D);
            poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            poseStack.scale(-0.025F, -0.025F, 0.025F);

            // **Always Fetch and Render the Latest Level**
            float nameWidth = font.width(entityName) / 2.0F;
            font.drawInBatch(entityName, -nameWidth, -10, 0xFFFFFF, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, event.getPackedLight());

            float textWidth = font.width(healthText) / 2.0F;
            font.drawInBatch(healthText, -textWidth, 0, 0xFFFFFF, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, event.getPackedLight());

            poseStack.popPose();
        }
    }

    // item cannot be dropped
    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getEntity().getItem();
        event.setCanceled(true);
        player.getInventory().add(itemStack);
        player.getInventory().add(itemStack);
        player.displayClientMessage(Component.literal("You cannot drop this item!"), true);
    }
}
