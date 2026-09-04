package com.angelwings.mod;

import java.util.EnumMap;
import java.util.List;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AngelWings.MODID)
public class AngelWings {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "angelwings";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Items which will all be registered under the "angelwings" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold ArmorMaterials which will all be registered under the "angelwings" namespace
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, MODID);

    // The material the Angel Wings chestplate is made of; kept as a plain constant so it can be referenced during registration
    private static final ArmorMaterial ANGEL_WINGS_MATERIAL_VALUE = new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> map.put(ArmorItem.Type.CHESTPLATE, 8)),
            15,
            SoundEvents.ARMOR_EQUIP_ELYTRA,
            () -> Ingredient.of(Items.NETHERITE_INGOT),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(MODID, "angel_wings"))),
            3.0F,
            0.1F);

    // Creates a new ArmorMaterial with the id "angelwings:angel_wings"
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ANGEL_WINGS_MATERIAL = ARMOR_MATERIALS.register("angel_wings", () -> ANGEL_WINGS_MATERIAL_VALUE);

    // Creates the Angel Wings chestplate with the id "angelwings:angel_wings", granting creative flight while worn
    public static final DeferredItem<AngelWingsItem> ANGEL_WINGS = ITEMS.registerItem("angel_wings",
            properties -> new AngelWingsItem(ANGEL_WINGS_MATERIAL, properties),
            new Item.Properties()
                    .durability(ArmorItem.Type.CHESTPLATE.getDurability(37))
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .attributes(AngelWingsItem.createAttributes(ANGEL_WINGS_MATERIAL_VALUE)));

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public AngelWings(IEventBus modEventBus) {
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so armor materials get registered
        ARMOR_MATERIALS.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    // Add the Angel Wings to the combat tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ANGEL_WINGS);
        }
    }
}
