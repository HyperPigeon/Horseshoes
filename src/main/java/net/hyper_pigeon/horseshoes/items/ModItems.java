package net.hyper_pigeon.horseshoes.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.hyper_pigeon.horseshoes.Horseshoes;
import net.hyper_pigeon.horseshoes.config.ModConfigs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.hyper_pigeon.horseshoes.Horseshoes.MOD_ID;

public class ModItems {

    public static final HorseshoesItem IRON_HORSESHOES_ITEM = (HorseshoesItem) registerItem("iron_horseshoes",
            new HorseshoesItem((float) ModConfigs.IRON_HORSESHOE_SPEED, (float) ModConfigs.IRON_HORSESHOE_ARMOR,
                    new Item.Settings().maxCount(1), new Identifier(MOD_ID, "textures/entity/horse/armor/iron_horseshoes.png")));

    public static final HorseshoesItem GOLD_HORSESHOES_ITEM = (HorseshoesItem) registerItem("gold_horseshoes",
            new HorseshoesItem((float) ModConfigs.GOLD_HORSESHOE_SPEED, (float) ModConfigs.GOLD_HORSESHOE_ARMOR,
                    new Item.Settings().maxCount(1), new Identifier(MOD_ID, "textures/entity/horse/armor/gold_horseshoes.png")));

    public static final HorseshoesItem DIAMOND_HORSESHOES_ITEM = (HorseshoesItem) registerItem("diamond_horseshoes",
            new HorseshoesItem((float) ModConfigs.DIAMOND_HORSESHOE_SPEED, (float) ModConfigs.DIAMOND_HORSESHOE_ARMOR,
                    new Item.Settings().maxCount(1), new Identifier(MOD_ID, "textures/entity/horse/armor/diamond_horseshoes.png")));

    //TODO DMY: 11/02/2025, Graxum: maybe add a new registry for the horseshoes instead of reusing the item registry, which may cause issues
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Horseshoes.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> itemGroup.add(IRON_HORSESHOES_ITEM));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> itemGroup.add(GOLD_HORSESHOES_ITEM));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> itemGroup.add(DIAMOND_HORSESHOES_ITEM));
    }
}
