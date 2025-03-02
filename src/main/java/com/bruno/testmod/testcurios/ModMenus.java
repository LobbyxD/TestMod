package com.bruno.testmod.testcurios;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, "testmod");

    public static final RegistryObject<MenuType<CustomSlotMenu>> CUSTOM_SLOT_MENU =
            MENUS.register("custom_slot_menu",
                    () -> IForgeMenuType.create(CustomSlotMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
