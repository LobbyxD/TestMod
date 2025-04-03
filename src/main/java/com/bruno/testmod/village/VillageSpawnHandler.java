package com.bruno.testmod.village;

import com.bruno.testmod.TestMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VillageSpawnHandler {

//    @SubscribeEvent
//    public static void onChunkLoad(ChunkEvent.Load event) {
//        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
//        if (!(event.getChunk() instanceof LevelChunk chunk)) return;
//
//        ChunkPos chunkPos = chunk.getPos();
//        serverLevel.getServer().execute(() -> {
//
//            // Iterate through all structures in this chunk
//            serverLevel.structureManager().startsForStructure(chunkPos, structure -> {
//                // Check if it's a village (villages are Jigsaw-based)
//                if (structure instanceof JigsawStructure) {
//                    ResourceLocation structureName = serverLevel.registryAccess()
//                            .registryOrThrow(Registries.STRUCTURE)
//                            .getKey(structure);
//
//                    if (structureName != null && structureName.getPath().startsWith("village_")) {
//                        return true; // It's a village
//                    }
//                }
//                return false;
//            }).forEach(start -> {
//                BoundingBox box = start.getBoundingBox();
//                BlockPos villageCenter = new BlockPos(box.getCenter().getX(), box.minY(), box.getCenter().getZ());
//
//                // Place a block at the village center
//                //serverLevel.setBlock(villageCenter, Blocks.DIAMOND_BLOCK.defaultBlockState(), 3);
//            });
//        });
//    }
}

