package com.bruno.testmod;

import com.bruno.testmod.block.ModBlocks;
import com.bruno.testmod.block.entity.ModBlockEntities;
import com.bruno.testmod.component.ModDataComponentTypes;
import com.bruno.testmod.effect.ModEffects;
import com.bruno.testmod.enchantment.ModEnchantmentEffects;
import com.bruno.testmod.entity.ModEntities;
import com.bruno.testmod.entity.client.BrunoRenderer;
import com.bruno.testmod.entity.client.menu.ModMenu;
import com.bruno.testmod.event.damagefloat.DamageNumberParticle;
import com.bruno.testmod.event.damagefloat.ModParticles;
import com.bruno.testmod.item.ModCreativeModeTabs;
import com.bruno.testmod.item.ModItems;
import com.bruno.testmod.network.PacketHandler;
import com.bruno.testmod.potion.ModPotions;
import com.bruno.testmod.skills.beam.LightRayRenderer;
import com.bruno.testmod.skills.healing.HealingParticle;
import com.bruno.testmod.skills.healing.ShardParticle;
import com.bruno.testmod.sound.ModSounds;
import com.bruno.testmod.util.ModItemProperties;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TestMod.MOD_ID)
public class TestMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "testmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final KeyMapping OPEN_JOB_GUI_KEY = new KeyMapping(
            "key.testmod.open_custom_gui", // The translation key for the keybinding
            GLFW.GLFW_KEY_G, // The default key (G in this case)
            "key.categories.testmod" // The category for your keybinding
    );
    public static final KeyMapping OPEN_CUSTOM_SCREEN = new KeyMapping(
            "key.testmod.open_custom_screen", // The translation key of the keybinding's name
            GLFW.GLFW_KEY_H, // The keycode of the key
            "key.categories.inventory" // The translation key of the keybinding's category
    );
    public static final KeyMapping OPEN_CUSTOM_INVENTORY_KEY = new KeyMapping(
            "key.testmod.open_custom_inventory",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_E,
            "key.categories.inventory"
    );
    public static final KeyMapping CHANGE_JOB_KEY = new KeyMapping(
            "key.testmod.change_job",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            "key.categories.testmod"
    );





    public TestMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModSounds.register(modEventBus);

        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);

        ModDataComponentTypes.register(modEventBus);

        ModEnchantmentEffects.register(modEventBus);

        ModEntities.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        ModMenu.register(modEventBus);
//        ModMenus.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        ModBlockEntities.register(modEventBus);
        ModParticles.register(modEventBus);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.KOHLRABI.get(), 0.4f);
            ComposterBlock.COMPOSTABLES.put(ModItems.KOHLRABI_SEEDS.get(), 0.15f);
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // Items
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.BRUNITE);
            event.accept(ModItems.RAW_BRUNITE);
            event.accept(ModItems.BRUNO_ASHES);
            event.accept(ModItems.KOHLRABI);
            event.accept(ModItems.CHISEL);

//            for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.ITEM_MAP.entrySet()) {
//                RegistryObject<Item> itemRegistryObject = entry.getValue();
//                event.accept(itemRegistryObject);
//            }
        }

        // Building Blocks
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.BRUNITE_BLOCK);
            event.accept(ModBlocks.RAW_BRUNITE_BLOCK);
            event.accept(ModBlocks.BRUNITE_ORE);
            event.accept(ModBlocks.BRUNITE_DEEPSLATE_ORE);
            event.accept(ModBlocks.MAGIC_BLOCK);
            event.accept(ModBlocks.HIGH_BLOCK);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModItemProperties.addCustomItemProperties();
            EntityRenderers.register(ModEntities.BRUNO.get(), BrunoRenderer::new);
            EntityRenderers.register(ModEntities.LIGHT_RAY.get(), LightRayRenderer::new);
            PacketHandler.register();
        }

        @SubscribeEvent
        public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
            event.registerSpecial(ModParticles.DAMAGE_NUMBER_PARTICLE.get(), new DamageNumberParticle.Factory(null));
            event.registerSpriteSet(ModParticles.HEALING_PARTICLE.get(), HealingParticle.Provider::new);
            event.registerSpriteSet(ModParticles.SHARD_PARTICLE.get(), ShardParticle.Provider::new);
        }
    }

    @SubscribeEvent
    public void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_JOB_GUI_KEY);
        event.register(OPEN_CUSTOM_SCREEN);
        event.register(CHANGE_JOB_KEY);
    }
}