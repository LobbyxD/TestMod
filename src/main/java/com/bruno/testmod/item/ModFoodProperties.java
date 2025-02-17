package com.bruno.testmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;


//Click SHIFT twice and search for FOOD and check on top right, you'll see full food properties
public class ModFoodProperties {
    public static final FoodProperties KOHLRABI = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(new MobEffectInstance(MobEffects.INVISIBILITY, 400), 0.20f).build();

    public static final FoodProperties HONEY_BERRY = new FoodProperties.Builder().nutrition(2)
            .saturationModifier(0.15f).fast().build();

}
