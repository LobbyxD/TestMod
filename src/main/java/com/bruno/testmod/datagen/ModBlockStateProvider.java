package com.bruno.testmod.datagen;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.block.ModBlocks;
import com.bruno.testmod.block.custom.BruniteLampBlock;
import com.bruno.testmod.block.custom.HoneyBerryBushBlock;
import com.bruno.testmod.block.custom.KohlrabiCropBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {


    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TestMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.BRUNITE_BLOCK);
        blockWithItem(ModBlocks.RAW_BRUNITE_BLOCK);

        blockWithItem(ModBlocks.BRUNITE_ORE);
        blockWithItem(ModBlocks.BRUNITE_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.BRUNITE_END_ORE);
        blockWithItem(ModBlocks.BRUNITE_NETHER_ORE);

        blockWithItem(ModBlocks.MAGIC_BLOCK);

        stairsBlock(ModBlocks.BRUNITE_STAIRS.get(), blockTexture(ModBlocks.BRUNITE_BLOCK.get()));
        slabBlock(ModBlocks.BRUNITE_SLAB.get(), blockTexture(ModBlocks.BRUNITE_BLOCK.get()), blockTexture(ModBlocks.BRUNITE_BLOCK.get()));
        buttonBlock(ModBlocks.BRUNITE_BUTTON.get(), blockTexture(ModBlocks.BRUNITE_BLOCK.get()));
        pressurePlateBlock(ModBlocks.BRUNITE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.BRUNITE_BLOCK.get()));

        fenceBlock(ModBlocks.BRUNITE_FENCE.get(), blockTexture(ModBlocks.BRUNITE_BLOCK.get()));
        fenceGateBlock(ModBlocks.BRUNITE_FENCE_GATE.get(), blockTexture(ModBlocks.BRUNITE_BLOCK.get()));
        wallBlock(ModBlocks.BRUNITE_WALL.get(), blockTexture(ModBlocks.BRUNITE_BLOCK.get()));

        doorBlockWithRenderType(ModBlocks.BRUNITE_DOOR.get(), modLoc("block/brunite_door_bottom"), modLoc("block/brunite_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.BRUNITE_TRAP_DOOR.get(), modLoc("block/brunite_trap_door"), true, "cutout");


        blockItem(ModBlocks.BRUNITE_STAIRS);
        blockItem(ModBlocks.BRUNITE_SLAB);
        blockItem(ModBlocks.BRUNITE_PRESSURE_PLATE);
        blockItem(ModBlocks.BRUNITE_FENCE_GATE);
        blockItem(ModBlocks.BRUNITE_TRAP_DOOR, "_bottom");

        customLamp();

        makeCrop(((CropBlock) ModBlocks.KOHLRABI_CROP.get()), "kohlrabi_crop_stage", "kohlrabi_crop_stage");
        makeBush(((SweetBerryBushBlock) ModBlocks.HONEY_BERRY_BUSH.get()), "honey_berry_bush_stage", "honey_berry_bush_stage");

        // tree
        logBlock(ModBlocks.BRUNE_LOG.get());
        axisBlock(ModBlocks.BRUNE_WOOD.get(), blockTexture(ModBlocks.BRUNE_LOG.get()), blockTexture(ModBlocks.BRUNE_LOG.get()));
        logBlock(ModBlocks.STRIPPED_BRUNE_LOG.get());
        axisBlock(ModBlocks.STRIPPED_BRUNE_WOOD.get(), blockTexture(ModBlocks.STRIPPED_BRUNE_LOG.get()), blockTexture(ModBlocks.STRIPPED_BRUNE_LOG.get()));

        blockItem(ModBlocks.BRUNE_LOG);
        blockItem(ModBlocks.BRUNE_WOOD);
        blockItem(ModBlocks.STRIPPED_BRUNE_LOG);
        blockItem(ModBlocks.STRIPPED_BRUNE_WOOD);
        blockWithItem(ModBlocks.BRUNE_PLANKS);

        leavesBlock(ModBlocks.BRUNE_LEAVES);
        saplingBlock(ModBlocks.BRUNE_SAPLING);
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void makeBush(SweetBerryBushBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(HoneyBerryBushBlock.AGE),
                ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "block/" + textureName + state.getValue(HoneyBerryBushBlock.AGE))).renderType("cutout"));

        return models;
    }

    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((KohlrabiCropBlock) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "block/" + textureName + state.getValue(((KohlrabiCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

  // optional: enter lamp name in function parameter to make it generic
  private void customLamp() {
      getVariantBuilder(ModBlocks.BRUNITE_LAMP.get()).forAllStates(state -> {
          if(state.getValue(BruniteLampBlock.CLICKED)) {
              return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("brunite_lamp_on",
                      ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "block/" + "brunite_lamp_on")))};
          } else {
              return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("brunite_lamp_off",
                      ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "block/" + "brunite_lamp_off")))};
          }
      });
      simpleBlockItem(ModBlocks.BRUNITE_LAMP.get(), models().cubeAll("brunite_lamp_on",
              ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "block/" + "brunite_lamp_on")));
  }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockItem(RegistryObject<? extends Block> blockRegistryObject){
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("testmod:block/" +
                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockItem(RegistryObject<? extends Block> blockRegistryObject, String appendix){
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("testmod:block/" +
                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + appendix));
    }
}


