package com.bruno.testmod.entity.client.menu;

import com.bruno.testmod.TestMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenu {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, TestMod.MOD_ID);
    public static final RegistryObject<MenuType<CustomChestMenu>> CUSTOM_CHEST = MENUS.register("custom_chest", () ->
            IForgeMenuType.create((id, inv, data) -> new CustomChestMenu(id, inv))
    );

//    public static final RegistryObject<MenuType<CustomInventoryMenu>> CUSTOM_INVENTORY_MENU =
//            MENUS.register("custom_inventory_menu", () -> new MenuType<>(CustomInventoryMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
