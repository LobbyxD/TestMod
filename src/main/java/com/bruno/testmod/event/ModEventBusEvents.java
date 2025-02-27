package com.bruno.testmod.event;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.entity.ModEntities;
import com.bruno.testmod.entity.client.BrunoAnimations;
import com.bruno.testmod.entity.client.BrunoModel;
import com.bruno.testmod.entity.custom.BrunoEntity;
import com.bruno.testmod.network.PacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static net.minecraft.world.entity.Mob.checkMobSpawnRules;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BrunoModel.LAYER_LOCATION, BrunoModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BRUNO.get(), BrunoEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        // Register custom spawn placement for zombies
        event.register(EntityType.ZOMBIE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ModEventBusEvents::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    public static boolean checkMonsterSpawnRules(
            EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom
    ) {
//        if (pType.equals(EntityType.ZOMBIE)) {
//            System.out.println("@@@@@@@@@@ ZOMBIE HERE @@@@@@@@@@");
//            System.out.println(pType);
//            System.out.println(pLevel.toString());
//            System.out.println(pSpawnType.toString());
//            System.out.println(pPos.toString());
//            System.out.println(pLevel.getDifficulty() != Difficulty.PEACEFUL
//                    && checkMobSpawnRules(pType, pLevel, pSpawnType, pPos, pRandom));
//        }
        return pLevel.getDifficulty() != Difficulty.PEACEFUL
                && checkMobSpawnRules(pType, pLevel, pSpawnType, pPos, pRandom);
    }

    private static boolean customZombieSpawnRules(EntityType<? extends Zombie> type, ServerLevelAccessor world, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        // Allow spawning in any light level and ensure it's not peaceful difficulty
        return true;
        //return world.getDifficulty() != Difficulty.PEACEFUL && Zombie.checkMobSpawnRules(type, world, spawnType, pos, random);
    }

//    @SubscribeEvent
//    public static void commonSetup(FMLCommonSetupEvent event) {
//        event.enqueueWork(() -> {
//            PacketHandler.register();
//        });
//    }
}
