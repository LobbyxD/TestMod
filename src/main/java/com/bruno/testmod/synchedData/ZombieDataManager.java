package com.bruno.testmod.synchedData;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ZombieDataManager {
    // Correct way to register EntityDataAccessor
    private static final EntityDataAccessor<Byte> CUSTOM_INT =
            SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.BYTE);

    // Attach data when the zombie spawns
    @SubscribeEvent
    public static void onZombieSpawn(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Zombie zombie) {
            SynchedEntityData data = zombie.getEntityData();
            try {
                data.get(CUSTOM_INT); // Check if it already exists
            } catch (NullPointerException e) {
                data.set(CUSTOM_INT, (byte) 1); // Define default value if not set
            }
        }
    }

    // Set custom data for a zombie
    public static void setCustomValue(Zombie zombie, byte value) {
        zombie.getEntityData().set(CUSTOM_INT, value);
    }

    // Get custom data from a zombie
    public static int getCustomValue(Zombie zombie) {
        return (int)zombie.getEntityData().get(CUSTOM_INT);
    }
}
