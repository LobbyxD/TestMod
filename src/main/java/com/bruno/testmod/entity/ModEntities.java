package com.bruno.testmod.entity;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.entity.custom.BrunoEntity;
import com.bruno.testmod.skills.beam.LightRayEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TestMod.MOD_ID);

    public static final RegistryObject<EntityType<BrunoEntity>> BRUNO =
            ENTITY_TYPES.register("bruno", () -> EntityType.Builder.of(BrunoEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.85F)
                    .eyeHeight(0.68F)
                    .build("bruno"));

    public static final RegistryObject<EntityType<LightRayEntity>> LIGHT_RAY =
            ENTITY_TYPES.register("light_beam", () -> EntityType.Builder.of(LightRayEntity::new, MobCategory.MISC)
                    .sized(1F, 1F)
                    .build("light_ray"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
