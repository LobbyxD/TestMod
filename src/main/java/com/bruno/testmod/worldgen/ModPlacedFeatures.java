package com.bruno.testmod.worldgen;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> BRUNITE_ORE_PLACED_KEY = registerKey("brunite_ore_placed");
    public static final ResourceKey<PlacedFeature> NEHTER_BRUNITE_ORE_PLACED_KEY = registerKey("nehter_brunite_ore_placed");
    public static final ResourceKey<PlacedFeature> END_BRUNITE_ORE_PLACED_KEY = registerKey("end_brunite_ore_placed");

    public static final ResourceKey<PlacedFeature> BRUNE_PLACED_KEY = registerKey("brune_placed");

    public static final ResourceKey<PlacedFeature> HONEY_BERRY_BUSH_PLACED_KEY = registerKey("honey_berry_bush_placed");


    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        // CF -> PF -> BM
        register(context, BRUNITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_BRUNITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        // unifrom / trinagle - lots of math, I don't understand, minute 12:00 episode 34
        register(context, NEHTER_BRUNITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_BRUNITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, END_BRUNITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_BRUNITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        // brune sapling here defines the tree can be generated ONLY where the sapling can be placed on
        // 3 trees with 10% change to put more 2 trees. Check VegetationPlacements to see vanilla trees values examples
        // 1 / 0.1f must create integer !!! or else error will occur
        register(context, BRUNE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BRUNE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
                        ModBlocks.BRUNE_SAPLING.get()));

        register(context, HONEY_BERRY_BUSH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.HONEY_BERRY_BUSH_KEY),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}