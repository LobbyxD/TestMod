package com.bruno.testmod.item;

import com.bruno.testmod.TestMod;
import com.bruno.testmod.item.custom.ChiselItem;
import com.bruno.testmod.item.custom.FuelItem;
import com.bruno.testmod.item.custom.HammerItem;

import com.bruno.testmod.item.custom.ModArmorItem;
import com.bruno.testmod.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TestMod.MOD_ID);

    public static final RegistryObject<Item> BRUNITE = ITEMS.register("brunite",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_BRUNITE = ITEMS.register("raw_brunite",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CHISEL = ITEMS.register("chisel",
            () -> new ChiselItem(new Item.Properties().durability(32)));

    public static final RegistryObject<Item> KOHLRABI = ITEMS.register("kohlrabi",
            () -> new Item(new Item.Properties().food(ModFoodProperties.KOHLRABI)) {
                @Override
                public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlas) {
                    pTooltipComponents.add(Component.translatable("tooltip.testmod.kohlrabi"));
                    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlas);
                }
            });

    public static final RegistryObject<Item> BRUNO_ASHES = ITEMS.register("bruno_ashes",
            () -> new FuelItem(new Item.Properties(), 1200));

    public static final RegistryObject<Item> BRUNITE_SWORD = ITEMS.register("brunite_sword",
            () -> new SwordItem(ModToolTiers.BRUNITE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BRUNITE, 3, -2.4f))));

    public static final RegistryObject<Item> BRUNITE_PICKAXE = ITEMS.register("brunite_pickaxe",
            () -> new PickaxeItem(ModToolTiers.BRUNITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.BRUNITE, 1, -2.8f))));

    public static final RegistryObject<Item> BRUNITE_SHOVEL = ITEMS.register("brunite_shovel",
            () -> new ShovelItem(ModToolTiers.BRUNITE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.BRUNITE, 1.5f, -3.0f))));

    public static final RegistryObject<Item> BRUNITE_AXE = ITEMS.register("brunite_axe",
            () -> new AxeItem(ModToolTiers.BRUNITE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.BRUNITE, 6, -3.2f))));

    public static final RegistryObject<Item> BRUNITE_HOE = ITEMS.register("brunite_hoe",
            () -> new HoeItem(ModToolTiers.BRUNITE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.BRUNITE, 0, -3.0f))));

    public static final RegistryObject<Item> BRUNITE_HAMMER = ITEMS.register("brunite_hammer",
            () -> new HammerItem(ModToolTiers.BRUNITE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.BRUNITE, 7, -3.5f))));

    public static final RegistryObject<Item> BRUNITE_HELMET = ITEMS.register("brunite_helmet",
            () -> new ModArmorItem(ModArmorMaterials.BRUNITE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));
    public static final RegistryObject<Item> BRUNITE_CHESTPLATE = ITEMS.register("brunite_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BRUNITE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))));
    public static final RegistryObject<Item> BRUNITE_LEGGINGS = ITEMS.register("brunite_leggings",
            () -> new ArmorItem(ModArmorMaterials.BRUNITE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(18))));
    public static final RegistryObject<Item> BRUNITE_BOOTS = ITEMS.register("brunite_boots",
            () -> new ArmorItem(ModArmorMaterials.BRUNITE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))));

    public static final RegistryObject<Item> BRUNITE_HORSE_ARMOR = ITEMS.register("brunite_horse_armor",
            () -> new AnimalArmorItem(ModArmorMaterials.BRUNITE_ARMOR_MATERIAL, AnimalArmorItem.BodyType.EQUESTRIAN,
                    false, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> KAUPEN_SMITHING_TEMPLATE = ITEMS.register("kaupen_armor_trim_smithing_template",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "kaupen")));

    public static final RegistryObject<Item> BRUNITE_BOW = ITEMS.register("brunite_bow",
            () -> new BowItem(new Item.Properties().durability(500)));

    public static final RegistryObject<Item> BAR_BRAWL_MUSIC_DISC = ITEMS.register("bar_brawl_music_disc",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.BAR_BRAWL_KEY).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}


