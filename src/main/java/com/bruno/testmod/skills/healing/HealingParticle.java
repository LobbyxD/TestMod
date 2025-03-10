package com.bruno.testmod.skills.healing;

import com.bruno.testmod.event.damagefloat.DamageNumberParticle;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class HealingParticle extends TextureSheetParticle {
    private static final Random RANDOM = new Random();

    private final double radius = 1.0;
    private final double angularVelocity = Math.PI / 180;
    private double angle;
    private final double centerX;
    private final double centerZ;

    public HealingParticle(ClientLevel pLevel, double pX, double pY, double pZ,
                           SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);

        this.friction = 0.8f;
        this.lifetime = 20;
        this.quadSize *= 0.65f;

//        this.xd = pXSpeed;
//        this.yd = pYSpeed;
//        this.zd = pZSpeed;

        // Calculate initial angle based on position
        this.centerX = x - radius * Math.cos(0);
        this.centerZ = z - radius * Math.sin(0);
        this.angle = Math.atan2(z - centerZ, x - centerX);

        this.setSpriteFromAge(spriteSet);

        // color
        float rand = RANDOM.nextFloat(0.2f, 1f);
        this.rCol = rand;
        this.gCol = 1f;
        this.bCol = rand;
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
            return new HealingParticle(worldIn, x, y, z, this.spriteSet, xSpeed, ySpeed, zSpeed);
        }
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
        moveUp();
    }

    private void fadeOut() {
        this.alpha = (-(1/(float)lifetime) * age + 1);
    }

    private void moveUp() {
        var pos = this.getPos();
        double upwardMovement = RANDOM.nextDouble(0.05, 0.09) * (1 - (double) this.age / this.lifetime);
        this.setPos(pos.x, pos.y + upwardMovement, pos.z);
    }
}
