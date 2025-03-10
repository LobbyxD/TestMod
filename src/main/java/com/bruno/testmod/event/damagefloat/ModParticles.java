package com.bruno.testmod.event.damagefloat;

import com.bruno.testmod.TestMod;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


// in net.minecraft.client -> assets -> minecraft -> textures -> particles
public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TestMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> DAMAGE_NUMBER_PARTICLE =
            PARTICLES.register("damage_number_particle", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> HEALING_PARTICLE =
            PARTICLES.register("healing_particle", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
