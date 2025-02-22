package com.bruno.testmod.worldgen;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    //CF(Create feature) -> PF(Place Feature) -> BM(Biome Feature)

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_BRUNITE_ORE_KEY = registerKey("brunite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_BRUNITE_ORE_KEY = registerKey("nehter_brunite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_BRUNITE_ORE_KEY = registerKey("end_brunite_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BRUNE_KEY = registerKey("brune");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest nehterrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldBrunite = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.BRUNITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.BRUNITE_DEEPSLATE_ORE.get().defaultBlockState()));
        register(context, OVERWORLD_BRUNITE_ORE_KEY, Feature.ORE, new OreConfiguration(overworldBrunite, 9));

        register(context, NETHER_BRUNITE_ORE_KEY, Feature.ORE, new OreConfiguration(nehterrackReplaceables,
                ModBlocks.BRUNITE_NETHER_ORE.get().defaultBlockState(), 9));

        register(context, END_BRUNITE_ORE_KEY, Feature.ORE, new OreConfiguration(endReplaceables,
                ModBlocks.BRUNITE_END_ORE.get().defaultBlockState(), 9));

        // what log and leaves will be, trunk and foliage placer changes types (play with numbers)
        // check tree features to determine height and more
        register(context, BRUNE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.BRUNE_LOG.get()),
                new ForkingTrunkPlacer(4, 4, 3),

                BlockStateProvider.simple(ModBlocks.BRUNE_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(3), 3),

                new TwoLayersFeatureSize(1, 0, 2)).build());


    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
