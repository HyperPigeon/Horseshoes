package net.hyper_pigeon.horseshoes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.hyper_pigeon.horseshoes.register.ItemRegistry;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class HorseshoesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonClass.init();


        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER,
                2,
                factories -> factories.add(new VillagerTrades.ItemsForEmeralds(ItemRegistry.IRON_HORSESHOES_ITEM.get(), 8, 1, 15)));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER,
                5,
                factories -> factories.add(new VillagerTrades.ItemsForEmeralds(ItemRegistry.DIAMOND_HORSESHOES_ITEM.get(), 32, 1, 30)));

        LootTableEvents.MODIFY.register((key, tableBuilder, source, holderProvider) -> {
            if (source.isBuiltin()) {
                if(BuiltInLootTables.NETHER_BRIDGE.equals(key)) {
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .setRolls(UniformGenerator.between(0, 1F)).
                            add(LootItem.lootTableItem(ItemRegistry.GOLD_HORSESHOES_ITEM.get()).setWeight(2));
                    tableBuilder.withPool(poolBuilder);
                }
                if(BuiltInLootTables.BASTION_BRIDGE.equals(key) || BuiltInLootTables.BASTION_HOGLIN_STABLE.equals(key) || BuiltInLootTables.BASTION_OTHER.equals(key) || BuiltInLootTables.BASTION_TREASURE.equals(key)) {
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .setRolls(UniformGenerator.between(0, 1F)).
                            add(LootItem.lootTableItem(ItemRegistry.GOLD_HORSESHOES_ITEM.get()).setWeight(2));
                    tableBuilder.withPool(poolBuilder);
                }
                if(BuiltInLootTables.VILLAGE_ARMORER.equals(key) || BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(key) || BuiltInLootTables.VILLAGE_TOOLSMITH.equals(key)) {
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .setRolls(UniformGenerator.between(0, 1))
                            .add(LootItem.lootTableItem(ItemRegistry.IRON_HORSESHOES_ITEM.get()).setWeight(2));
                    tableBuilder.withPool(poolBuilder);
                }
                if(BuiltInLootTables.DESERT_PYRAMID.equals(key)){
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .setRolls(UniformGenerator.between(0, 1))
                            .add(LootItem.lootTableItem(ItemRegistry.IRON_HORSESHOES_ITEM.get()).setWeight(2))
                            .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_HORSESHOES_ITEM.get()).setWeight(1))
                            .add(LootItem.lootTableItem(ItemRegistry.GOLD_HORSESHOES_ITEM.get()).setWeight(1));
                    tableBuilder.withPool(poolBuilder);
                }

                if(BuiltInLootTables.SIMPLE_DUNGEON.equals(key)){
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(ItemRegistry.IRON_HORSESHOES_ITEM.get()).setWeight(2))
                            .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_HORSESHOES_ITEM.get()).setWeight(1));
                    tableBuilder.withPool(poolBuilder);
                }

                if(BuiltInLootTables.END_CITY_TREASURE.equals(key)){
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .setRolls(UniformGenerator.between(0F, 1.0F))
                            .add(LootItem.lootTableItem(ItemRegistry.GOLD_HORSESHOES_ITEM.get()))
                            .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_HORSESHOES_ITEM.get()));
                    tableBuilder.withPool(poolBuilder);
                }

                if(BuiltInLootTables.ANCIENT_CITY.equals(key)){
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .setRolls(UniformGenerator.between(0F, 2.0F))
                            .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_HORSESHOES_ITEM.get()).setWeight(2));
                    tableBuilder.withPool(poolBuilder);
                }

                if(BuiltInLootTables.WOODLAND_MANSION.equals(key)){
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .setRolls(UniformGenerator.between(0F, 2.0F))
                            .add(LootItem.lootTableItem(ItemRegistry.IRON_HORSESHOES_ITEM.get()).setWeight(2))
                            .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_HORSESHOES_ITEM.get()).setWeight(1));
                    tableBuilder.withPool(poolBuilder);
                }

            }
        });
    }
}
