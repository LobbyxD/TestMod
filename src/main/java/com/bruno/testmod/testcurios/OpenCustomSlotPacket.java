package com.bruno.testmod.testcurios;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.SimpleChannel;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class OpenCustomSlotPacket {
    public OpenCustomSlotPacket() {}

    public OpenCustomSlotPacket(FriendlyByteBuf buf) {}

    public void encode(FriendlyByteBuf buf) {}

    public static void handle(OpenCustomSlotPacket msg, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                CustomSlotMenuProvider.open(player);
            }
        });
        context.setPacketHandled(true);
    }
}
