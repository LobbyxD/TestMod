package com.bruno.testmod.potion;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, TestMod.MOD_ID);

    // name slimey_potion is under minecraft, so if any other mod shares the same potion name they might clash
    // you can add mod name to the potion name (testmode_slimey_potion) - it doesn't show anywhere in the game
    public static final RegistryObject<Potion> SLIMEY_POTION = POTIONS.register("slimey_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SLIMEY_EFFECT.getHolder().get(), 200, 0)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
