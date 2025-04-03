package com.bruno.testmod.skills.beam;

import com.bruno.testmod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LightRayEvent {

    @SubscribeEvent
    public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level world = event.getLevel();

        // Ensure it's on the server and the player is holding a wooden sword
        if (world instanceof ServerLevel serverLevel && player.getMainHandItem().is(Items.WOODEN_SWORD)) {
            BlockPos targetPos = event.getPos(); // Position of the block clicked
            summonLightRay(serverLevel, targetPos);
        }
    }

    private static void summonLightRay(ServerLevel world, BlockPos pos) {
        LightRayEntity ray = new LightRayEntity(ModEntities.LIGHT_RAY.get(), world);
        ray.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        world.addFreshEntity(ray);

    }

    private static void summonLightRay2(ServerLevel world, BlockPos pos) {
        int maxHeight = world.getMaxBuildHeight(); // Get the top of the world
        BlockPos start = new BlockPos(pos.getX(), maxHeight, pos.getZ()); // Start from the sky

        // Create a visual effect (lightning bolt without fire or sound)
        world.setBlock(pos.above(), net.minecraft.world.level.block.Blocks.LIGHT.defaultBlockState(), 3);
        world.sendParticles(net.minecraft.core.particles.ParticleTypes.END_ROD, pos.getX(), pos.getY(), pos.getZ(), 50, 0.5, 10, 0.5, 0.1);

        // Damage all mobs in the path
        damageMobsInRay(world, start, pos);
    }

    private static void damageMobsInRay(ServerLevel world, BlockPos start, BlockPos end) {
//        List<Entity> entities = world.getEntities(null, new net.minecraft.world.phys.AABB(start, end));
//
//        for (Entity entity : entities) {
//            if (entity instanceof Mob mob) {
//                mob.hurt(net.minecraft.world.damagesource.DamageSource., 10.0F); // Deal magic damage
//            }
//        }
    }
}
