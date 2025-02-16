package com.bruno.testmod.item;

import com.bruno.testmod.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModToolTiers {
    public static final Tier BRUNITE = new ForgeTier(1400, 4, 3f, 20,
            ModTags.Blocks.NEEDS_BRUNITE_TOOL, () -> Ingredient.of(ModItems.BRUNITE.get()),
            ModTags.Blocks.INCORRECT_FOR_BRUNITE_TOOL) ;
}
