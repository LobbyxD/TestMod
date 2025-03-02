package com.bruno.testmod.network;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.network.packets.RequestZombieLevelPacket;
import com.bruno.testmod.network.packets.ZombieLevelSyncPacket;
import com.bruno.testmod.testcurios.OpenCustomSlotPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class PacketHandler {

    private static final int PROTOCOL_VERSION = 1;
    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
            ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "main"))
            .serverAcceptedVersions((status, version) -> true)
            .clientAcceptedVersions((status, version) -> true)
            .networkProtocolVersion(PROTOCOL_VERSION)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(ZombieLevelSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ZombieLevelSyncPacket::encode)
                .decoder(ZombieLevelSyncPacket::new)
                .consumerMainThread(ZombieLevelSyncPacket::handle)
                .add();

        INSTANCE.messageBuilder(RequestZombieLevelPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(RequestZombieLevelPacket::encode)
                .decoder(RequestZombieLevelPacket::new)
                .consumerMainThread(RequestZombieLevelPacket::handle)
                .add();

        INSTANCE.messageBuilder(OpenCustomSlotPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(OpenCustomSlotPacket::encode)
                .decoder(OpenCustomSlotPacket::new)
                .consumerMainThread(OpenCustomSlotPacket::handle)
                .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(msg, PacketDistributor.PLAYER.with(player));
    }

    public static void sendToAllClients(Object msg) {
        INSTANCE.send(msg, PacketDistributor.ALL.noArg());
    }
}
