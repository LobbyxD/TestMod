package com.bruno.testmod.block.custom;

import com.bruno.testmod.block.entity.custom.LightningStrikeBlockEntity;
import com.bruno.testmod.item.ModItems;
import com.bruno.testmod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// CTRL + H on Block class to see all vanilla blocks
public class HighBlock extends Block implements EntityBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public HighBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    private boolean isValidItem(ItemStack item) {
        return item.is(ModTags.Items.TRANSFORMABLE_ITEMS);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(pContext)) {
            return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    // might need to test
    @Override
    public BlockState playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        DoubleBlockHalf half = pState.getValue(HALF);
        BlockPos otherPos = (half == DoubleBlockHalf.LOWER) ? pPos.above() : pPos.below();
        BlockState otherState = pLevel.getBlockState(otherPos);
        if (otherState.is(this) && otherState.getValue(HALF) != half) {
            pLevel.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 35);
            pLevel.levelEvent(pPlayer, 2001, otherPos, Block.getId(otherState));
            ItemStack tool = pPlayer.getMainHandItem();
            if (!pLevel.isClientSide && !pPlayer.isCreative()) {
                Block.dropResources(pState, pLevel, pPos, null, pPlayer, tool);
                Block.dropResources(otherState, pLevel, otherPos, null, pPlayer, tool);
            }
        }

        return super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.block();
    }

    private static final int TICKS_TO_SCAN = 5 * 20;

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);

        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof LightningStrikeBlockEntity) {
            System.out.println("!!! tick and send lightning");
            ((LightningStrikeBlockEntity) blockEntity).scanAndStrike();
        }
        pLevel.scheduleTick(pPos, this, TICKS_TO_SCAN); // Reschedule the next tick
    }

    @Override
    protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
        if (!pLevel.isClientSide) {
            System.out.println("!!! onPlace on server");
            pLevel.scheduleTick(pPos, this, TICKS_TO_SCAN);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LightningStrikeBlockEntity(pPos, pState);
    }
}
