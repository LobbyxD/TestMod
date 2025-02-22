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
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BRUNITE.get()))
                    .title(Component.translatable("creativetab.testmod.brunite_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.BRUNITE.get());
                        output.accept(ModItems.RAW_BRUNITE.get());
                        output.accept(ModItems.BRUNO_ASHES.get());
                        output.accept(ModItems.CHISEL.get());
                        output.accept(ModItems.KOHLRABI.get());

                        output.accept(ModBlocks.BRUNITE_ORE.get());
                        output.accept(ModBlocks.BRUNITE_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.BRUNITE_NETHER_ORE.get());
                        output.accept(ModBlocks.BRUNITE_END_ORE.get());

                        output.accept(ModBlocks.BRUNITE_BLOCK.get());
                        output.accept(ModBlocks.RAW_BRUNITE_BLOCK.get());
                        output.accept(ModBlocks.MAGIC_BLOCK.get());

                        output.accept(ModBlocks.BRUNITE_STAIRS.get());
                        output.accept(ModBlocks.BRUNITE_SLAB.get());

                        output.accept(ModBlocks.BRUNITE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.BRUNITE_BUTTON.get());

                        output.accept(ModBlocks.BRUNITE_FENCE.get());
                        output.accept(ModBlocks.BRUNITE_FENCE_GATE.get());
                        output.accept(ModBlocks.BRUNITE_WALL.get());

                        output.accept(ModBlocks.BRUNITE_DOOR.get());
                        output.accept(ModBlocks.BRUNITE_TRAP_DOOR.get());
                        output.accept(ModBlocks.BRUNITE_LAMP.get());

                        output.accept(ModItems.BRUNITE_SWORD.get());
                        output.accept(ModItems.BRUNITE_PICKAXE.get());
                        output.accept(ModItems.BRUNITE_SHOVEL.get());
                        output.accept(ModItems.BRUNITE_AXE.get());
                        output.accept(ModItems.BRUNITE_HOE.get());
                        output.accept(ModItems.BRUNITE_HAMMER.get());

                        output.accept(ModItems.BRUNITE_HELMET.get());
                        output.accept(ModItems.BRUNITE_CHESTPLATE.get());
                        output.accept(ModItems.BRUNITE_LEGGINGS.get());
                        output.accept(ModItems.BRUNITE_BOOTS.get());

                        output.accept(ModItems.BRUNITE_HORSE_ARMOR.get());

                        output.accept(ModItems.KAUPEN_SMITHING_TEMPLATE.get());
                        output.accept(ModItems.BRUNITE_BOW.get());

                        output.accept(ModItems.BAR_BRAWL_MUSIC_DISC.get());

                        output.accept(ModItems.KOHLRABI_SEEDS.get());
                        output.accept(ModItems.HONEY_BERRY.get());

                        output.accept(ModBlocks.BRUNE_LOG.get());
                        output.accept(ModBlocks.BRUNE_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_BRUNE_LOG.get());
                        output.accept(ModBlocks.STRIPPED_BRUNE_WOOD.get());
                        output.accept(ModBlocks.BRUNE_SAPLING.get());
                        output.accept(ModBlocks.BRUNE_LEAVES.get());
                        output.accept(ModBlocks.BRUNE_PLANKS.get());


                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
