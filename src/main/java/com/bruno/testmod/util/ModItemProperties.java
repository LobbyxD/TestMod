package com.bruno.testmod.util;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.component.ModDataComponentTypes;
import com.bruno.testmod.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.CHISEL.get(), ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "used"),
                (itemStack, clientLevel, livingEntity, i) -> itemStack.get(ModDataComponentTypes.COORDINATES.get()) != null ? 1f : 0f);

        makeCustomBow(ModItems.BRUNITE_BOW.get());
    }

    private static void makeCustomBow(Item item) {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"),
                (itemStack, clientLevel, useItem, i) -> {
            if (useItem == null) {
                return 0.0F;
            } else {
                return useItem.getUseItem() != itemStack ? 0.0F : (float) (itemStack.getUseDuration(useItem) - useItem.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pulling"),
                (itemStack, clientLevel, livingEntity, i)
                        -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F
        );
    }
}
