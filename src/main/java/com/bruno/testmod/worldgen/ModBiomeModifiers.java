package com.bruno.testmod.worldgen;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.entity.ModEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_BRUNITE_ORE = registerKey("add_brunite_ore");
    public static final ResourceKey<BiomeModifier> ADD_NEHTER_BRUNITE_ORE = registerKey("add_nether_brunite_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_BRUNITE_ORE = registerKey("add_end_brunite_ore");

    public static final ResourceKey<BiomeModifier> ADD_BRUNE_TREE = registerKey("add_tree_brune");

    public static final ResourceKey<BiomeModifier> ADD_HONEY_BERRY_BUSH = registerKey("add_honey_berry_bush");

    public static final ResourceKey<BiomeModifier> SPAWN_ZOMBIES_MMO = registerKey("zombie_plains");


    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_BRUNITE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.BRUNITE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
//        Specific Biomes
//        context.register(ADD_BRUNITE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
//                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.BAMBOO_JUNGLE)),
//                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.BRUNITE_ORE_PLACED_KEY)),
//                GenerationStep.Decoration.UNDERGROUND_ORES
//        ));
        context.register(ADD_NEHTER_BRUNITE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.NEHTER_BRUNITE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_END_BRUNITE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.END_BRUNITE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));


        // tree
        context.register(ADD_BRUNE_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.SAVANNA)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.BRUNE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        // bush
        context.register(ADD_HONEY_BERRY_BUSH, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.FOREST), biomes.getOrThrow(Biomes.BIRCH_FOREST)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.HONEY_BERRY_BUSH_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        // spawn zombies
//        context.register(SPAWN_ZOMBIES_MMO, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
//                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
//                List.of(new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 25, 3, 5))
//        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, name));
    }
}
