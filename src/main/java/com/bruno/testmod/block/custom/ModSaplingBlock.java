package com.bruno.testmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;

import java.util.function.Supplier;

public class ModSaplingBlock extends SaplingBlock {
    private final Supplier<Block> block;

    public ModSaplingBlock(TreeGrower treeGrower, Properties properties, Supplier<Block> block) {
        super(treeGrower, properties);
        this.block = block;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(block.get());
    }

    // if define to only place sapling on specific block type, prevents from allowing to place on dirt
    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.get("custom");
    }
}
