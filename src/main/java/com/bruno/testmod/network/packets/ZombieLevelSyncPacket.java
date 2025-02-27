package com.bruno.testmod.network.packets;

import com.bruno.testmod.event.ModEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class ZombieLevelSyncPacket {
    private final int entityId;
    private final int entityLevel;
    private final boolean isAggro;

    public ZombieLevelSyncPacket(int entityId, int level, boolean isAggro) {
        this.entityId = entityId;
        this.entityLevel = level;
        this.isAggro = isAggro;
    }

    // read by order of write
    public ZombieLevelSyncPacket(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readInt(), buffer.readBoolean());
    }

    // Serialize the data to send
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.entityLevel);
        buffer.writeBoolean(this.isAggro);
    }

    // Deserialize (Decode) the data when received
    public ZombieLevelSyncPacket decode(FriendlyByteBuf buffer) {
        int entityId = buffer.readInt();
        int level = buffer.readInt();
        boolean isAggro = buffer.readBoolean();
        return new ZombieLevelSyncPacket(entityId, level, isAggro);
    }

    // Handle the packet on the client side
    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            if(context.isClientSide() && Minecraft.getInstance().level != null) {
                // LocalPlayer player = Minecraft.getInstance().player;
                Entity entity = Minecraft.getInstance().level.getEntity(this.entityId);
                if(entity instanceof Zombie zombie) {
                    //System.out.println("### CLIENT RECEIVED PACKET: Zombie ID " + entity.getId() + " | Level: " + this.entityLevel);
                    zombie.getPersistentData().putInt(ModEvents.LEVEL_TAG, this.entityLevel);
                    zombie.getPersistentData().putBoolean(ModEvents.AGGRO_TAG, this.isAggro);
                }
                else {
                    //System.out.println("### CLIENT RECEIVED PACKET BUT DIDNT FIND: Zombie ID " + entity.getId() + " | Level: " + this.entityLevel);
                }
            } else {
                //System.out.println("### CLIENT ERROR: Could not find zombie with ID " + this.entityId);
                context.setPacketHandled(false);
            }
        });
        context.setPacketHandled(true);
    }
}
