package com.angelwings.mod;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.NeoForgeMod;

public class AngelWingsItem extends ArmorItem {
    public AngelWingsItem(Holder<ArmorMaterial> material, Properties properties) {
        super(material, Type.CHESTPLATE, properties);
    }

    public static ItemAttributeModifiers createAttributes(ArmorMaterial material) {
        EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(Type.CHESTPLATE.getSlot());
        ResourceLocation modifierId = ResourceLocation.fromNamespaceAndPath(AngelWings.MODID, "angel_wings");
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder()
            .add(Attributes.ARMOR,
                new AttributeModifier(modifierId, material.getDefense(Type.CHESTPLATE), AttributeModifier.Operation.ADD_VALUE),
                slotGroup)
            .add(Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(modifierId, material.toughness(), AttributeModifier.Operation.ADD_VALUE),
                slotGroup);
        if (material.knockbackResistance() > 0.0F) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE,
                new AttributeModifier(modifierId, material.knockbackResistance(), AttributeModifier.Operation.ADD_VALUE),
                slotGroup);
        }
        return builder
            .add(NeoForgeMod.CREATIVE_FLIGHT,
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AngelWings.MODID, "angel_wings_flight"), 1.0,
                    AttributeModifier.Operation.ADD_VALUE),
                slotGroup)
            .build();
    }
}
