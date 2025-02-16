package com.bruno.testmod.datagen;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.block.ModBlocks;
import com.bruno.testmod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> pRegisteries) {
        super(packOutput, pRegisteries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        List<ItemLike> BRUNITE_SMELTABLES = List.of(ModItems.RAW_BRUNITE.get(),
                ModBlocks.BRUNITE_ORE.get(), ModBlocks.BRUNITE_DEEPSLATE_ORE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BRUNITE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.BRUNITE.get())
                .unlockedBy(getHasName(ModItems.BRUNITE.get()), has(ModItems.BRUNITE.get())).save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRUNITE.get(), 9)
                .requires(ModBlocks.BRUNITE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.BRUNITE_BLOCK.get()), has(ModBlocks.BRUNITE_BLOCK.get())).save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRUNITE.get(), 32)
                .requires(ModBlocks.MAGIC_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.BRUNITE_BLOCK.get()), has(ModBlocks.BRUNITE_BLOCK.get()))
                .save(pRecipeOutput, TestMod.MOD_ID + ":brunite_from_magic_block");

        oreSmelting(pRecipeOutput, BRUNITE_SMELTABLES, RecipeCategory.MISC, ModItems.BRUNITE.get(), 0.25f, 200, "brunite");
        oreBlasting(pRecipeOutput, BRUNITE_SMELTABLES, RecipeCategory.MISC, ModItems.BRUNITE.get(), 0.25f, 100, "brunite");

        stairBuilder(ModBlocks.BRUNITE_STAIRS.get(), Ingredient.of(ModItems.BRUNITE.get())).group("brunite")
                .unlockedBy(getHasName(ModBlocks.BRUNITE_BLOCK.get()), has(ModBlocks.BRUNITE_BLOCK.get())).save(pRecipeOutput);
        slab(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRUNITE_SLAB.get(), ModItems.BRUNITE.get());

        buttonBuilder(ModBlocks.BRUNITE_BUTTON.get(), Ingredient.of(ModItems.BRUNITE.get())).group("brunite")
                .unlockedBy(getHasName(ModBlocks.BRUNITE_BLOCK.get()), has(ModBlocks.BRUNITE_BLOCK.get())).save(pRecipeOutput);
        pressurePlate(pRecipeOutput, ModBlocks.BRUNITE_PRESSURE_PLATE.get(), ModItems.BRUNITE.get());

        fenceBuilder(ModBlocks.BRUNITE_FENCE.get(), Ingredient.of(ModItems.BRUNITE.get())).group("brunite")
                .unlockedBy(getHasName(ModBlocks.BRUNITE_BLOCK.get()), has(ModBlocks.BRUNITE_BLOCK.get())).save(pRecipeOutput);
        fenceGateBuilder(ModBlocks.BRUNITE_FENCE_GATE.get(), Ingredient.of(ModItems.BRUNITE.get())).group("brunite")
                .unlockedBy(getHasName(ModBlocks.BRUNITE_BLOCK.get()), has(ModBlocks.BRUNITE_BLOCK.get())).save(pRecipeOutput);
        wall(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRUNITE_WALL.get(), ModItems.BRUNITE.get());

        doorBuilder(ModBlocks.BRUNITE_DOOR.get(), Ingredient.of(ModItems.BRUNITE.get())).group("brunite")
                .unlockedBy(getHasName(ModBlocks.BRUNITE_BLOCK.get()), has(ModBlocks.BRUNITE_BLOCK.get())).save(pRecipeOutput);
        trapdoorBuilder(ModBlocks.BRUNITE_TRAP_DOOR.get(), Ingredient.of(ModItems.BRUNITE.get())).group("brunite")
                .unlockedBy(getHasName(ModBlocks.BRUNITE_BLOCK.get()), has(ModBlocks.BRUNITE_BLOCK.get())).save(pRecipeOutput);

        trimSmithing(pRecipeOutput, ModItems.KAUPEN_SMITHING_TEMPLATE.get(), ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "kaupen"));
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, TestMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
