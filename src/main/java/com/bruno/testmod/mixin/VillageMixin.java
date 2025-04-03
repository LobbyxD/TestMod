package com.bruno.testmod.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Mixin(PoolElementStructurePiece.class)
public class VillageMixin {

    // Track village bounding boxes using a single village ID
    private static final Map<Integer, BoundingBox> villageBoundingBoxes = new HashMap<>();
    private static final Map<Integer, Integer> villagePieceCounts = new HashMap<>();
    private static final Set<Integer> completedVillages = new HashSet<>();
    private static int villageIDCounter = 0;
    private static final Map<ChunkPos, Integer> chunkToVillageID = new HashMap<>();

    @Inject(method = "postProcess", at = @At("TAIL"))
    private void onVillageGenerated(
            WorldGenLevel world, StructureManager structureManager, ChunkGenerator generator,
            RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos, CallbackInfo ci) {

        // 🚀 1. Try to find an existing village ID from nearby chunks
        int villageID = findOrAssignVillageID(chunkPos);

        // 🚀 2. Track the largest BoundingBox per entire village
        villageBoundingBoxes.merge(villageID, box, (existingBox, newBox) ->
                isLargerBoundingBox(newBox, existingBox) ? newBox : existingBox);

        // 🚀 3. Count total pieces for this village
        villagePieceCounts.put(villageID, villagePieceCounts.getOrDefault(villageID, 0) + 1);

        // 🚀 4. Delay execution to allow all pieces to finish processing
        if (!completedVillages.contains(villageID)) {
            completedVillages.add(villageID);

            world.getServer().execute(() -> {
                // Ensure all pieces are processed before placing the block
                if (villagePieceCounts.getOrDefault(villageID, 0) < 10) {
                    return; // 🚨 Not enough pieces counted, village still generating!
                }

                BoundingBox villageBox = villageBoundingBoxes.get(villageID);
                if (villageBox != null) {
                    int centerX = villageBox.getCenter().getX();
                    int centerZ = villageBox.getCenter().getZ();
                    int highestY = world.getHeightmapPos(
                            net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE,
                            new BlockPos(centerX, 0, centerZ)).getY();
                    BlockPos villageCenter = new BlockPos(centerX, highestY, centerZ);

                    // 🚀 5. Place the Diamond Block **only once per entire village**
//                    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//                    executor.schedule(() -> execute(world.getLevel(), villageCenter), 100, TimeUnit.MILLISECONDS);

                    world.getLevel().setBlock(villageCenter, Blocks.DIAMOND_BLOCK.defaultBlockState(), 3);
                    System.out.println("✅ Village center block placed at " + villageCenter + " in world " + world.toString());



                    // 🚀 6. Remove tracking after placement
                    villageBoundingBoxes.remove(villageID);
                    villagePieceCounts.remove(villageID);
                    completedVillages.remove(villageID);
                }
            });
        }
    }

    public void execute(ServerLevel world, BlockPos villageCenter) {
        world.setBlock(villageCenter, Blocks.DIAMOND_BLOCK.defaultBlockState(), 3);
        System.out.println("✅ Village center block placed at " + villageCenter + " in world " + world.toString());
    }

    // 🚀 **New Helper Function: Find or Merge Nearby Villages**
    private int findOrAssignVillageID(ChunkPos chunkPos) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkPos neighbor = new ChunkPos(chunkPos.x + dx, chunkPos.z + dz);
                if (chunkToVillageID.containsKey(neighbor)) {
                    int existingID = chunkToVillageID.get(neighbor);
                    chunkToVillageID.put(chunkPos, existingID);
                    return existingID; // ✅ Use the existing ID to merge villages
                }
            }
        }
        // 🚀 If no nearby village ID found, assign a new one
        int newVillageID = villageIDCounter++;
        chunkToVillageID.put(chunkPos, newVillageID);
        return newVillageID;
    }

    // 🚀 Helper: Check if a BoundingBox is larger
    private boolean isLargerBoundingBox(BoundingBox newBox, BoundingBox existingBox) {
        int newSize = newBox.getXSpan() * newBox.getZSpan();
        int existingSize = existingBox.getXSpan() * existingBox.getZSpan();
        return newSize > existingSize;
    }
}