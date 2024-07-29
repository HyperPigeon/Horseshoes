package net.hyper_pigeon.horseshoes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.hyper_pigeon.horseshoes.items.HorseshoesItem;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

import java.util.UUID;

public class Horseshoes implements ModInitializer {

    private static final Identifier ALLOWED_ID = Identifier.of("horseshoes","allowed_to_wear_horseshoes");
    public static final TagKey<EntityType<?>> ALLOWED = TagKey.of(RegistryKeys.ENTITY_TYPE, ALLOWED_ID);

    public static final Identifier HORSESHOE_BOOST = Identifier.of("horseshoes", "speed_boost");
    public static final Identifier HORSESHOE_ARMOR_BONUS = Identifier.of("horseshoes","armor_bonus");
    //    public static final EntityAttributeModifier IRON_HORSESHOE_BOOST = new EntityAttributeModifier("IRON_HORSESHOE_BOOST",0.1,
//            EntityAttributeModifier.Operation.fromId(0));
//    public static final EntityAttributeModifier DIAMOND_HORSESHOE_BOOST = new EntityAttributeModifier("DIAMOND_HORSESHOE_BOOST",0.15,
//            EntityAttributeModifier.Operation.fromId(0));
//    public static final EntityAttributeModifier GOLD_HORSESHOE_BOOST = new EntityAttributeModifier("GOLD_HORSESHOE_BOOST",0.2,
//            EntityAttributeModifier.Operation.fromId(0));

    public static final HorseshoesItem DIAMOND_HORSESHOES_ITEM = new HorseshoesItem(0.15F, 5.5F, new Item.Settings().maxCount(1)
    ,Identifier.of("horseshoes", "textures/entity/horse/armor/diamond_horseshoes.png"));
    public static final HorseshoesItem IRON_HORSESHOES_ITEM = new HorseshoesItem(0.1F,3.5F,new Item.Settings().maxCount(1)
            ,Identifier.of("horseshoes", "textures/entity/horse/armor/iron_horseshoes.png"));
    public static final HorseshoesItem GOLD_HORSESHOES_ITEM = new HorseshoesItem(0.2F, 2.5F, new Item.Settings().maxCount(1)
            ,Identifier.of("horseshoes", "textures/entity/horse/armor/gold_horseshoes.png"));

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM,Identifier.of("horseshoes", "diamond_horseshoes"), DIAMOND_HORSESHOES_ITEM);
        Registry.register(Registries.ITEM,Identifier.of("horseshoes", "iron_horseshoes"), IRON_HORSESHOES_ITEM);
        Registry.register(Registries.ITEM,Identifier.of("horseshoes", "gold_horseshoes"), GOLD_HORSESHOES_ITEM);

        ItemGroupEvents
                .modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(Horseshoes.DIAMOND_HORSESHOES_ITEM));

        ItemGroupEvents
                .modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(Horseshoes.GOLD_HORSESHOES_ITEM));

        ItemGroupEvents
                .modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(Horseshoes.IRON_HORSESHOES_ITEM));

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER,
                2,
                factories -> factories.add(new TradeOffers.SellItemFactory(IRON_HORSESHOES_ITEM, 8, 1, 15)));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER,
                5,
                factories -> factories.add(new TradeOffers.SellItemFactory(DIAMOND_HORSESHOES_ITEM, 32, 1, 30)));

        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (source.isBuiltin()) {
                if(LootTables.NETHER_BRIDGE_CHEST.equals(key)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(UniformLootNumberProvider.create(1.0F, 2.0F)).
                            with(ItemEntry.builder(Horseshoes.GOLD_HORSESHOES_ITEM).weight(4));
                    tableBuilder.pool(poolBuilder);
                }
                if(LootTables.BASTION_BRIDGE_CHEST.equals(key) || LootTables.BASTION_HOGLIN_STABLE_CHEST.equals(key) || LootTables.BASTION_OTHER_CHEST.equals(key) || LootTables.BASTION_TREASURE_CHEST.equals(key)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(UniformLootNumberProvider.create(2.0F, 3.0F)).
                            with(ItemEntry.builder(Horseshoes.GOLD_HORSESHOES_ITEM).weight(8));
                    tableBuilder.pool(poolBuilder);
                }
                if(LootTables.VILLAGE_ARMORER_CHEST.equals(key) || LootTables.VILLAGE_WEAPONSMITH_CHEST.equals(key) || LootTables.VILLAGE_TOOLSMITH_CHEST.equals(key)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(UniformLootNumberProvider.create(1.0F, 2.0F))
                            .with(ItemEntry.builder(Horseshoes.IRON_HORSESHOES_ITEM).weight(8));
                    tableBuilder.pool(poolBuilder);
                }
                if(LootTables.DESERT_PYRAMID_CHEST.equals(key)){
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(UniformLootNumberProvider.create(1.0F, 2.0F))
                            .with(ItemEntry.builder(Horseshoes.IRON_HORSESHOES_ITEM).weight(8))
                            .with(ItemEntry.builder(Horseshoes.DIAMOND_HORSESHOES_ITEM).weight(4))
                            .with(ItemEntry.builder(Horseshoes.GOLD_HORSESHOES_ITEM).weight(1));
                    tableBuilder.pool(poolBuilder);
                }

                if(LootTables.SIMPLE_DUNGEON_CHEST.equals(key)){
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1.0F))
                            .with(ItemEntry.builder(Horseshoes.IRON_HORSESHOES_ITEM).weight(4))
                            .with(ItemEntry.builder(Horseshoes.DIAMOND_HORSESHOES_ITEM).weight(1));
                    tableBuilder.pool(poolBuilder);
                }

                if(LootTables.END_CITY_TREASURE_CHEST.equals(key)){
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(UniformLootNumberProvider.create(1.0F, 2.0F))
                            .with(ItemEntry.builder(Horseshoes.IRON_HORSESHOES_ITEM))
                            .with(ItemEntry.builder(Horseshoes.GOLD_HORSESHOES_ITEM))
                            .with(ItemEntry.builder(Horseshoes.DIAMOND_HORSESHOES_ITEM));
                    tableBuilder.pool(poolBuilder);
                }

                if(LootTables.ANCIENT_CITY_CHEST.equals(key)){
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1.0F))
                            .with(ItemEntry.builder(Horseshoes.DIAMOND_HORSESHOES_ITEM).weight(8));
                    tableBuilder.pool(poolBuilder);
                }

                if(LootTables.WOODLAND_MANSION_CHEST.equals(key)){
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1.0F))
                            .with(ItemEntry.builder(Horseshoes.IRON_HORSESHOES_ITEM).weight(8))
                            .with(ItemEntry.builder(Horseshoes.DIAMOND_HORSESHOES_ITEM).weight(4));
                    tableBuilder.pool(poolBuilder);
                }

            }

        });
    }
}
