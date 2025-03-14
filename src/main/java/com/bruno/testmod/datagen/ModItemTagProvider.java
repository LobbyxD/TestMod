package com.bruno.testmod.datagen;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.block.ModBlocks;
import com.bruno.testmod.item.ModItems;
import com.bruno.testmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                              CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, TestMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.BRUNITE.get())
                .add(ModItems.RAW_BRUNITE.get())
                .add(Items.COAL)
                .add(Items.STICK)
                .add(Items.COMPASS);

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.BRUNITE_HELMET.get())
                .add(ModItems.BRUNITE_CHESTPLATE.get())
                .add(ModItems.BRUNITE_LEGGINGS.get())
                .add(ModItems.BRUNITE_BOOTS.get());

        tag(ItemTags.TRIM_MATERIALS)
                .add(ModItems.BRUNITE.get());

        tag(ItemTags.TRIM_TEMPLATES)
                .add(ModItems.KAUPEN_SMITHING_TEMPLATE.get());

        // tree
        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.BRUNE_LOG.get().asItem())
                .add(ModBlocks.BRUNE_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_BRUNE_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_BRUNE_WOOD.get().asItem());

        tag(ItemTags.PLANKS)
                .add(ModBlocks.BRUNE_PLANKS.get().asItem());
    }
}
