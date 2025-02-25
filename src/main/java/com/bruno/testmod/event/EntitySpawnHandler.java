package com.bruno.testmod.event;

import com.bruno.testmod.TestMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.living.MobSpawnEvent;


@Mod.EventBusSubscriber(modid = TestMod.MOD_ID)
public class EntitySpawnHandler {

    @SubscribeEvent
    public static void onCheckSpawn(MobSpawnEvent.SpawnPlacementCheck event) {
        EntityType entity = event.getEntityType();
        //BlockPos eyePosition = BlockPos.containing(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
        if (entity == EntityType.ZOMBIE) {
            System.out.println("@@@ SpawnPlacementCheck @@@");
            System.out.println(event.getResult() + ", " + event.isCanceled());
            System.out.println(event.getPos());
            event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public static void onCheckSpawnPos(MobSpawnEvent.PositionCheck event) {
        if (event.getEntity() instanceof Zombie zombie) {
            System.out.println("@@@ PositionCheck @@@");
            System.out.println(event.getResult() + ", " + event.isCanceled());
            event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(MobSpawnEvent.FinalizeSpawn event) {
        if (event.getEntity() instanceof Zombie zombie || event.getEntity().getType() == EntityType.ZOMBIE) {
            System.out.println("@@@ FinalizeSpawn @@@");
            System.out.println(event.getResult() + ", " + event.isSpawnCancelled() + ", " + event.isCanceled());
            //event.setResult(Event.Result.ALLOW);

        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld2(EntityJoinLevelEvent event) {
        if(event.getEntity() instanceof Zombie zombie) {
            System.out.println("$$ Zombie joining world $$");
            System.out.println(zombie.getX() + ", " + zombie.getY() + ", " + zombie.getZ());
        }
    }
}
