package com.bruno.testmod.block.entity.custom;

import com.bruno.testmod.block.ModBlocks;
import com.bruno.testmod.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class LightningStrikeBlockEntity extends BlockEntity {

    public static final int SCAN_RADIUS_XZ = 10;
    public static final int SCAN_RADIUS_Y = 5;
    public static final String LIGHTNING_KILL_FLAG = "killed_by_zeus";

    public LightningStrikeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LIGHTNING_STRIKE_BLOCK_ENTITY.get(), pos, state);
    }

    public void scanAndStrike() {
        System.out.println("!!! Striking");
        if (level instanceof ServerLevel serverLevel) {
            AABB scanArea = new AABB(
                    worldPosition.getX() - SCAN_RADIUS_XZ, worldPosition.getY() - SCAN_RADIUS_Y, worldPosition.getZ() - SCAN_RADIUS_XZ,
                    worldPosition.getX() + SCAN_RADIUS_XZ + 1, worldPosition.getY() + SCAN_RADIUS_Y + 1, worldPosition.getZ() + SCAN_RADIUS_XZ + 1
            );

            List<Monster> monsters = serverLevel.getEntitiesOfClass(Monster.class, scanArea);

            for (Monster monster : monsters) {
                Vec3 pos = new Vec3(monster.getX(), monster.getY(), monster.getZ());

                CompoundTag data = monster.getPersistentData();
                data.putBoolean(LIGHTNING_KILL_FLAG, true);

                monster.skipDropExperience();
                monster.kill();

                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(serverLevel);
                if (lightning != null) {
                    lightning.moveTo(pos.x, pos.y, pos.z);
                    lightning.setVisualOnly(true);
                    serverLevel.addFreshEntity(lightning);
                }
            }
        }
    }
}
