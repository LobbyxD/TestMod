package com.bruno.testmod.item;

import com.bruno.testmod.TestMod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TestMod.MOD_ID);

//    public static final RegistryObject<Item> BRUNRITE = ITEMS.register("brunrite",
//            () -> new Item(new Item.Properties().setId(ITEMS.key("brunite"))));

    public static final Map<String, RegistryObject<Item>> ITEM_MAP = new HashMap<>();

    static {
        Map<String, String> itemsFromJson = loadItemsFromJson();
        if(itemsFromJson == null)
            System.out.println("[ModItems] Error creating items !!!");

        for (Map.Entry<String, String> entry : itemsFromJson.entrySet()) {
            String name = entry.getKey();

            ITEM_MAP.put(name, ITEMS.register(name,
                    () -> new Item(new Item.Properties())));
        }
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private static Map<String, String> loadItemsFromJson() {
        Gson gson = new Gson();
        File itemCreatorFile = new File("../src/main/resources/assets/itemCreator.json");
        if (!itemCreatorFile.exists()) {
            System.out.println("[ModItems] itemCreator.json not found!");
            return null;
        }
        try {
            Map<String, String> newEntries = gson.fromJson(new FileReader(itemCreatorFile), new TypeToken<Map<String, String>>() {}.getType());
            return newEntries;
        } catch (IOException e) {
////            LOGGER.error("Failed to read items JSON file: {}", JSON_FILE_PATH, e);
        }

        return null;
    }
}
