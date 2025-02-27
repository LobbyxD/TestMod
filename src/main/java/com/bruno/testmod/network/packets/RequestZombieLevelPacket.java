package com.bruno.testmod.network.packets;

import com.bruno.testmod.event.ModEvents;
import com.bruno.testmod.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.function.Supplier;

public class RequestZombieLevelPacket {
    private final int entityId;

    public RequestZombieLevelPacket(int entityId) {
        this.entityId = entityId;
    }

    public RequestZombieLevelPacket(RegistryFriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public static void encode(RequestZombieLevelPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.entityId);
    }

    public static RequestZombieLevelPacket decode(FriendlyByteBuf buffer) {
        return new RequestZombieLevelPacket(buffer.readInt());
    }

    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            Entity entity = player.level().getEntity(this.entityId);
            if (entity instanceof Zombie zombie) {
                int level = zombie.getPersistentData().getInt(ModEvents.LEVEL_TAG);
                boolean isAggro = zombie.getPersistentData().getBoolean(ModEvents.AGGRO_TAG);

                //System.out.println("### SERVER: Sending Level " + level + " for Zombie ID " + zombie.getId() + " to " + player.getName().getString());

                // Send the level back to the client that requested it
                PacketHandler.sendToPlayer(new ZombieLevelSyncPacket(zombie.getId(), level, isAggro), player);
            }
        });
        context.setPacketHandled(true);
    }
}
