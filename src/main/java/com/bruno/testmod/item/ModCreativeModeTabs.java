package com.bruno.testmod.item;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TestMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BRUNITE_ITEMS_TAB = CREATIVE_MODE_TABS.register("brunite_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ITEM_MAP.get("brunite").get()))
                    .title(Component.translatable("creativetab.testmod.brunite_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.ITEM_MAP.entrySet()) {
                            output.accept(entry.getValue().get());
                        }
                        output.accept(ModBlocks.MAGIC_BLOCK.get());

                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
