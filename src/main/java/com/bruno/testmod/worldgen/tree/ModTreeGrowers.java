package com.bruno.testmod.worldgen.tree;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower BRUNE = new TreeGrower(TestMod.MOD_ID + ":brune",
            Optional.empty(), Optional.of(ModConfiguredFeatures.BRUNE_KEY), Optional.empty());
}
