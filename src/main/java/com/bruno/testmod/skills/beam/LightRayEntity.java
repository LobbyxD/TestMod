package com.bruno.testmod.skills.beam;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;

public class LightRayEntity extends Entity {

    public LightRayEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {}

    @Override
    public void tick() {
        super.tick();

        if (level() instanceof ServerLevel serverLevel) {
            // Random sparkles around base
            serverLevel.sendParticles(ParticleTypes.END_ROD,
                    this.getX(), this.getY(), this.getZ(),
                    5, 0.3, 1.0, 0.3, 0.01);

            // Place a light block at base if it's air or replaceable
            var pos = new BlockPos((int)this.getX(), (int)this.getY(), (int)this.getZ());
            if (serverLevel.isEmptyBlock(pos)) {
                serverLevel.setBlock(pos, Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15), 3);
            }

            // Optional: remove the light block after entity is gone
            if (this.tickCount > 19) {
                if (serverLevel.getBlockState(pos).is(Blocks.LIGHT)) {
                    serverLevel.removeBlock(pos, false);
                }
            }
        }

        if (this.tickCount > 20) this.discard(); // remove after 1 second
    }

}
