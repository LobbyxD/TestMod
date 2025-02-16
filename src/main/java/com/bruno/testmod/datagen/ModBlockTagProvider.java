package com.bruno.testmod.datagen;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.block.ModBlocks;
import com.bruno.testmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TestMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.BRUNITE_BLOCK.get())
                .add(ModBlocks.RAW_BRUNITE_BLOCK.get())
                .add(ModBlocks.BRUNITE_ORE.get())
                .add(ModBlocks.BRUNITE_DEEPSLATE_ORE.get())
                .add(ModBlocks.MAGIC_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.RAW_BRUNITE_BLOCK.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.RAW_BRUNITE_BLOCK.get());

        tag(BlockTags.FENCES).add(ModBlocks.BRUNITE_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.BRUNITE_FENCE_GATE.get());
        tag(BlockTags.WALLS).add(ModBlocks.BRUNITE_WALL.get());

        // brunite tool acn mine as good as iron tools + mine raw brunite block
        tag(ModTags.Blocks.NEEDS_BRUNITE_TOOL)
                .add(ModBlocks.RAW_BRUNITE_BLOCK.get())
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        // cannot mine with brunite tools what cannot be mined with iron ones (except NEEDS_BRUNITE_TOOL)
        tag(ModTags.Blocks.INCORRECT_FOR_BRUNITE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_BRUNITE_TOOL);
    }
}
