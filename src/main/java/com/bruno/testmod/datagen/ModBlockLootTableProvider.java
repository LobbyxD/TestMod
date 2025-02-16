package com.bruno.testmod.datagen;

import com.bruno.testmod.block.ModBlocks;
import com.bruno.testmod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.BRUNITE_BLOCK.get());
        dropSelf(ModBlocks.RAW_BRUNITE_BLOCK.get());
        dropSelf(ModBlocks.MAGIC_BLOCK.get());

        this.add(ModBlocks.BRUNITE_ORE.get(),
                block -> createOreDrop(ModBlocks.BRUNITE_ORE.get(), ModItems.RAW_BRUNITE.get()));
        this.add(ModBlocks.BRUNITE_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.BRUNITE_DEEPSLATE_ORE.get(), ModItems.RAW_BRUNITE.get(), 2, 5));

        dropSelf(ModBlocks.BRUNITE_STAIRS.get());
        this.add(ModBlocks.BRUNITE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.BRUNITE_SLAB.get()));

        dropSelf(ModBlocks.BRUNITE_BUTTON.get());
        dropSelf(ModBlocks.BRUNITE_FENCE.get());
        dropSelf(ModBlocks.BRUNITE_FENCE_GATE.get());
        dropSelf(ModBlocks.BRUNITE_WALL.get());
        dropSelf(ModBlocks.BRUNITE_TRAP_DOOR.get());
        dropSelf(ModBlocks.BRUNITE_PRESSURE_PLATE.get());

        this.add(ModBlocks.BRUNITE_DOOR.get(),
                block -> createDoorTable(ModBlocks.BRUNITE_DOOR.get()));

        dropSelf(ModBlocks.BRUNITE_LAMP.get());

    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock, this.applyExplosionDecay(
                        pBlock, LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
