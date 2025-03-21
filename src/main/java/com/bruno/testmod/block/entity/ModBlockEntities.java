package com.bruno.testmod.block.entity;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.block.ModBlocks;
import com.bruno.testmod.block.entity.custom.LightningStrikeBlockEntity;
import com.bruno.testmod.block.entity.custom.PedestalBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TestMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BE =
            BLOCK_ENTITIES.register("pedestal_be", () -> BlockEntityType.Builder.of(
                    PedestalBlockEntity::new, ModBlocks.PEDESTAL.get()).build(null));

    public static final RegistryObject<BlockEntityType<LightningStrikeBlockEntity>> LIGHTNING_STRIKE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("lightning_strike_block_entity", () -> BlockEntityType.Builder.of(
                    LightningStrikeBlockEntity::new, ModBlocks.HIGH_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}