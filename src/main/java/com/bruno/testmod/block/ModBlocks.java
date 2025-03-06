package com.bruno.testmod.block;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.block.custom.*;
import com.bruno.testmod.item.ModItems;

import com.bruno.testmod.sound.ModSounds;
import com.bruno.testmod.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


import java.awt.*;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TestMod.MOD_ID);


    public static final RegistryObject<Block> BRUNITE_BLOCK = registerBlock("brunite_block",
            () -> new Block(BlockBehaviour.Properties.of().
                    strength(1f).
                    requiresCorrectToolForDrops().
                    sound(SoundType.AMETHYST)
            ));

    public static final RegistryObject<Block> RAW_BRUNITE_BLOCK = registerBlock("raw_brunite_block",
            () -> new Block(BlockBehaviour.Properties.of().
                    strength(1f).
                    requiresCorrectToolForDrops().
                    sound(SoundType.AMETHYST)
            ));

    public static final RegistryObject<Block> BRUNITE_ORE = registerBlock("brunite_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.of().
                    strength(1f).
                    requiresCorrectToolForDrops().
                    sound(SoundType.STONE)
            ));

    public static final RegistryObject<Block> BRUNITE_DEEPSLATE_ORE = registerBlock("brunite_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6), BlockBehaviour.Properties.of().
                    strength(1f).
                    requiresCorrectToolForDrops().
                    sound(SoundType.DEEPSLATE)
            ));
    public static final RegistryObject<Block> BRUNITE_END_ORE = registerBlock("brunite_end_ore",
            () -> new DropExperienceBlock(UniformInt.of(5, 9), BlockBehaviour.Properties.of().
                    strength(7f).
                    requiresCorrectToolForDrops()
            ));
    public static final RegistryObject<Block> BRUNITE_NETHER_ORE = registerBlock("brunite_nether_ore",
            () -> new DropExperienceBlock(UniformInt.of(1, 5), BlockBehaviour.Properties.of().
                    strength(3f).
                    requiresCorrectToolForDrops()
            ));




    public static final RegistryObject<Block> MAGIC_BLOCK = registerBlock("magic_block",
            () -> new MagicBlock(BlockBehaviour.Properties.of().
                    strength(2f).
                    requiresCorrectToolForDrops()
                    .sound(ModSounds.MAGIC_BLOCK_SOUNDS)
            ));

    public static final RegistryObject<StairBlock> BRUNITE_STAIRS = registerBlock("brunite_stairs",
            () -> new StairBlock(ModBlocks.BRUNITE_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final RegistryObject<SlabBlock> BRUNITE_SLAB = registerBlock("brunite_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));

    public static final RegistryObject<PressurePlateBlock> BRUNITE_PRESSURE_PLATE = registerBlock("brunite_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.IRON,
                    BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final RegistryObject<ButtonBlock> BRUNITE_BUTTON = registerBlock("brunite_button",
            () -> new ButtonBlock(BlockSetType.IRON, 1, BlockBehaviour.Properties.of().strength(2f)
                    .requiresCorrectToolForDrops().noCollission()));

    public static final RegistryObject<FenceBlock> BRUNITE_FENCE = registerBlock("brunite_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final RegistryObject<FenceGateBlock> BRUNITE_FENCE_GATE = registerBlock("brunite_fence_gate",
            () -> new FenceGateBlock(WoodType.ACACIA, BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final RegistryObject<WallBlock> BRUNITE_WALL = registerBlock("brunite_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));

    public static final RegistryObject<DoorBlock> BRUNITE_DOOR = registerBlock("brunite_door",
            () -> new DoorBlock(BlockSetType.ACACIA,
                    BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<TrapDoorBlock> BRUNITE_TRAP_DOOR = registerBlock("brunite_trap_door",
            () -> new TrapDoorBlock(BlockSetType.IRON,
                    BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> BRUNITE_LAMP = registerBlock("brunite_lamp",
            () -> new BruniteLampBlock(BlockBehaviour.Properties.of().strength(3f)
                    .lightLevel(state -> state.getValue(BruniteLampBlock.CLICKED) ? 15 : 0)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static final RegistryObject<Block> KOHLRABI_CROP = BLOCKS.register("kohlrabi_crop",
            () -> new KohlrabiCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));

    public static final RegistryObject<Block> HONEY_BERRY_BUSH = BLOCKS.register("honey_berry_bush",
            () -> new HoneyBerryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final RegistryObject<Block> HIGH_BLOCK = registerBlock("high_block",
            () -> new HighBlock(BlockBehaviour.Properties.of().
                    strength(1f).
                    requiresCorrectToolForDrops().
                    sound(SoundType.ANVIL).
                    dynamicShape().noOcclusion()
            ));

    // custom tree
    public static final RegistryObject<RotatedPillarBlock> BRUNE_LOG = registerBlock("brune_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> BRUNE_WOOD = registerBlock("brune_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_BRUNE_LOG = registerBlock("stripped_brune_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_BRUNE_WOOD = registerBlock("stripped_brune_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistryObject<Block> BRUNE_PLANKS = registerBlock("brune_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<Block> BRUNE_LEAVES = registerBlock("brune_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });
    // supplier to endstone sets that the sapling can be placed on endstone
    // BUT, minecraft dirt methods turns the block into dirt, so, in ModConfiguredFeatures you add dirt provider with ENDSTONE to the twolayers
    public static final RegistryObject<Block> BRUNE_SAPLING = registerBlock("brune_sapling",
            () -> new ModSaplingBlock(ModTreeGrowers.BRUNE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING), () -> Blocks.END_STONE));


    public static final RegistryObject<Block> PEDESTAL = registerBlock("pedestal",
            () -> new PedestalBlock(BlockBehaviour.Properties.of().noOcclusion()));

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
