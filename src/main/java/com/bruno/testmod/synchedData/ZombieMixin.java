package com.bruno.testmod.synchedData;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Zombie.class)
public abstract class ZombieMixin {

    @Unique
    private static final EntityDataAccessor<Integer> CUSTOM_INT =
            SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.INT);

    // Modify `defineSynchedData()` to register our new data field
    @Inject(method = "defineSynchedData", at = @At("RETURN"))
    private void injectCustomData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(CUSTOM_INT, 0); // Default value: 0
    }

    // Helper methods for modifying data
    @Unique
    public void setCustomValue(int value) {
        ((Zombie) (Object) this).getEntityData().set(CUSTOM_INT, value);
    }

    @Unique
    public int getCustomValue() {
        return ((Zombie) (Object) this).getEntityData().get(CUSTOM_INT);
    }
}
