package com.bruno.testmod.event.damagefloat;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.event.ModEvents;
import com.bruno.testmod.skills.ModDamageSources;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.function.Supplier;

public class ClientBoundDamageNumberMessage {

    private final int entityId;
    private final float damageAmount;
    private final Holder<DamageType> damageType;
    private final boolean isCrit;
    private final float critMult;

    public ClientBoundDamageNumberMessage(int entityId, float damageAmount, Holder<DamageType> damageType, boolean isCrit, float critMult) {
        this.entityId = entityId;
        this.damageAmount = damageAmount;
        this.damageType = damageType;
        this.isCrit = isCrit;
        this.critMult = critMult;
    }

    // read by order of write
    public ClientBoundDamageNumberMessage(RegistryFriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readFloat(),  DamageType.STREAM_CODEC.decode(buffer), buffer.readBoolean(), buffer.readFloat());
    }

    // Serialize the data to send
    public void encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeFloat(this.damageAmount);
        DamageType.STREAM_CODEC.encode(buffer, damageType);
        buffer.writeBoolean(this.isCrit);
        buffer.writeFloat(this.critMult);
    }

    // Deserialize (Decode) the data when received
    public com.bruno.testmod.network.packets.ZombieLevelSyncPacket decode(FriendlyByteBuf buffer) {
        int entityId = buffer.readInt();
        int level = buffer.readInt();
        boolean isAggro = buffer.readBoolean();
        return new com.bruno.testmod.network.packets.ZombieLevelSyncPacket(entityId, level, isAggro);
    }

    // Handle the packet on the client side
    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().level.getEntity(this.entityId);
            if (entity instanceof LivingEntity livingEntity) {
//                int i = dummy.getNextNumberPos();
                int i = 0;
                spawnNumber(entity, i);
            }
        });
        context.setPacketHandled(true);
    }

    private void spawnNumber(Entity entity, int animationPos) {
        var type = damageType;
        float mult = critMult;

        double z = CritMode.encodeIntFloatToDouble(animationPos, mult);
        int color = getDamageColor(type);

        var res = ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "damage_number_particle");

        entity.level().addParticle(ModParticles.DAMAGE_NUMBER_PARTICLE.get(),
                entity.getX(), entity.getY() + 1, entity.getZ(), damageAmount, color, z);
    }

    // suboptimal but eh
    public static int getDamageColor(Holder<DamageType> damageTypeId) {
        if(damageTypeId.is(ModDamageSources.CUSTOM_DAMAGE))
            return 0x1898E3; // blue

        var values = 0xFF7700;
        return values;
    }
}


//public class ClientBoundDamageNumberMessage (int entityID, float damageAmount, Holder<DamageType> damageType, boolean isCrit, float critMult) {
//
//    public static final CustomPacketPayload.TypeAndCodec<RegistryFriendlyByteBuf, ClientBoundDamageNumberMessage> TYPE =
//            Message.makeType(Dummmmmmy.res("s2c_damage_number"), ClientBoundDamageNumberMessage::of);
//
//
//    public static ClientBoundDamageNumberMessage of(RegistryFriendlyByteBuf buf) {
//        var entityID = buf.readInt();
//        var damageAmount = buf.readFloat();
//        var damageType = DamageType.STREAM_CODEC.decode(buf);
//        var isCrit = buf.readBoolean();
//        var critMult = isCrit ? buf.readFloat() : 0;
//        return new ClientBoundDamageNumberMessage(entityID, damageAmount, damageType, isCrit, critMult);
//    }
//
//    public ClientBoundDamageNumberMessage(int id, float damage, @Nullable DamageSource source, @Nullable CritRecord critical, Level level) {
//        this(id, damage, encodeDamage(source, level), critical != null, critical == null ? 0 : critical.getMultiplier());
//    }
//
//    public static Holder<DamageType> encodeDamage(@Nullable DamageSource source, Level level) {
//        if (source == null) return Dummmmmmy.TRUE_DAMAGE.getHolder(level);
//        //if (critical) return Dummmmmmy.CRITICAL_DAMAGE;
//        var damageType = source.typeHolder();
//        return Preconditions.checkNotNull(damageType);
//    }
//
//    @Override
//    public void write(RegistryFriendlyByteBuf buf) {
//        buf.writeInt(this.entityID);
//        buf.writeFloat(this.damageAmount);
//        DamageType.STREAM_CODEC.encode(buf, damageType);
//        buf.writeBoolean(this.isCrit);
//        if (isCrit) buf.writeFloat(this.critMult);
//    }
//
//    @Override
//    public void handle(Context context) {
//        Entity entity = Minecraft.getInstance().level.getEntity(this.entityID);
//        if (entity instanceof TargetDummyEntity dummy) {
//            if (ClientConfigs.DAMAGE_NUMBERS.get()) {
//                int i = dummy.getNextNumberPos();
//                spawnNumber(entity, i);
//            }
//            if (ClientConfigs.HAY_PARTICLES.get()) {
//                spawnHay(entity);
//            }
//        } else if (entity != null) {
//            spawnNumber(entity, 0);
//        }
//    }
//
//    private void spawnHay(Entity entity) {
//        var random = entity.getRandom();
//        int amount = (int) (1 + Mth.map(this.damageAmount, 0, 40, 0, 10));
//        amount  = Math.min(amount, 10);
//        for (int i = 0; i < amount; i++) {
//            Vec3 pos = new Vec3(entity.getRandomX(0.5),
//                    entity.getY() + 0.75 + random.nextFloat() * 0.85,
//                    entity.getRandomZ(0.5));
//            Vec3 speed = getOutwardSpeed(pos.subtract(entity.position()), random);
//            entity.level().addParticle(Dummmmmmy.HAY_PARTICLE.get(), pos.x, pos.y, pos.z,
//                    speed.x, random.nextFloat() * 0.04,
//                    speed.z);
//        }
//    }
//
//    public static Vec3 getOutwardSpeed(Vec3 position, RandomSource random) {
//
//        // Normalize the vector to get the direction
//        Vec3 direction = position.normalize();
//
//        // Apply random rotation variation
//        float randomLen = 0.02f + random.nextFloat() * 0.04f;
//        float angleVariation = (float) (random.nextGaussian() * 0.3f); // variation up to ±22.5 degrees
//        float sin =  Mth.sin(angleVariation);
//        float cos =  Mth.cos(angleVariation);
//
//        double newX = direction.x * cos - direction.z * sin;
//        double newY = direction.x * sin + direction.z * cos;
//
//        return new Vec3(newX*randomLen,0, newY*randomLen);
//    }
//
//    private void spawnNumber(Entity entity, int animationPos) {
//        var type = damageType;
//        float mult = 0;
//        CritMode critMode = ClientConfigs.CRIT_MODE.get();
//        if (critMode != CritMode.OFF && isCrit) {
//            type = Dummmmmmy.CRITICAL_DAMAGE.getHolder(entity);
//            if (critMode == CritMode.COLOR_AND_MULTIPLIER) {
//                mult = critMult;
//            }
//        }
//        double z = CritMode.encodeIntFloatToDouble(animationPos, mult);
//        int color = ClientConfigs.getDamageColor(type);
//
//        entity.level().addParticle(Dummmmmmy.NUMBER_PARTICLE.get(),
//                entity.getX(), entity.getY() + 1, entity.getZ(), damageAmount, color, z);
//    }
//
//
//    @Override
//    public Type<? extends CustomPacketPayload> type() {
//        return TYPE.type();
//    }
//}