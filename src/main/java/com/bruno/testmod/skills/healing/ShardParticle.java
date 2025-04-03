package com.bruno.testmod.skills.healing;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ShardParticle extends TextureSheetParticle {
    private static final Random RANDOM = new Random();
    private final double velocityMultiplier = 0.1; // Controls outward spread speed

    public ShardParticle(ClientLevel pLevel, double pX, double pY, double pZ,
                         SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);

        this.friction = 0.85f;
        this.lifetime = 2 + RANDOM.nextInt(3);
        this.quadSize *= 0.75f;

        // Random outward velocity
        double angle = RANDOM.nextDouble() * 2 * Math.PI;
        double speed = 1 + RANDOM.nextDouble() * 0.3;
        this.xd = Math.cos(angle) * speed;
        this.yd = 0.1 + RANDOM.nextDouble() * 0.1; // Slight upward movement
        this.zd = Math.sin(angle) * speed;

        this.setSpriteFromAge(spriteSet);

        // Color variation (reddish-orange theme for shards)
        float rand = RANDOM.nextFloat(0.4f, 1f);
        this.rCol = 1f;
        this.gCol = rand * 0.5f;
        this.bCol = rand * 0.3f;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ShardParticle(worldIn, x, y, z, this.spriteSet, xSpeed, ySpeed, zSpeed);
        }
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
    }

    private void fadeOut() {
        this.alpha = (-(1/(float)lifetime) * age + 1);
    }
}
