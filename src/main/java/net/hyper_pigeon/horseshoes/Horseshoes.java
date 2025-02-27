package net.hyper_pigeon.horseshoes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.hyper_pigeon.horseshoes.config.ModConfigs;
import net.hyper_pigeon.horseshoes.items.ModItems;
import net.minecraft.entity.EntityType;
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

import static net.hyper_pigeon.horseshoes.items.ModItems.*;

public class Horseshoes implements ModInitializer {

    public static final String MOD_ID = "horseshoes";

    private static final Identifier ALLOWED_ID = new Identifier(MOD_ID, "allowed_to_wear_horseshoes");
    public static final TagKey<EntityType<?>> ALLOWED = TagKey.of(RegistryKeys.ENTITY_TYPE, ALLOWED_ID);

    public static final UUID HORSESHOE_BOOST_UUID = UUID.fromString("edde09bc-c052-43ed-b7ae-b6fa1bae989a");
    public static final UUID HORSESHOE_ARMOR_BONUS_UUID = UUID.fromString("92a08c42-4405-497a-8fbf-736915bd665b");
    //    public static final EntityAttributeModifier IRON_HORSESHOE_BOOST = new EntityAttributeModifier("IRON_HORSESHOE_BOOST",0.1,
//            EntityAttributeModifier.Operation.fromId(0));
//    public static final EntityAttributeModifier DIAMOND_HORSESHOE_BOOST = new EntityAttributeModifier("DIAMOND_HORSESHOE_BOOST",0.15,
//            EntityAttributeModifier.Operation.fromId(0));
//    public static final EntityAttributeModifier GOLD_HORSESHOE_BOOST = new EntityAttributeModifier("GOLD_HORSESHOE_BOOST",0.2,
//            EntityAttributeModifier.Operation.fromId(0));

    @Override
    public void onInitialize() {

        ModConfigs.registerConfigs();

        ModItems.registerModItems();

        //For debugging purposes
        //System.out.println("iron horseshoe stats:" + IRON_HORSESHOES_ITEM.getArmorBonus() + ", " + IRON_HORSESHOES_ITEM.getSpeedBonus() + "; target values in config: " + ModConfigs.IRON_HORSESHOE_ARMOR + ", " + ModConfigs.IRON_HORSESHOE_SPEED);
        //System.out.println("gold horseshoe stats:" + GOLD_HORSESHOES_ITEM.getArmorBonus() + ", " + GOLD_HORSESHOES_ITEM.getSpeedBonus() + "; target values in config: " + ModConfigs.GOLD_HORSESHOE_ARMOR + ", " + ModConfigs.GOLD_HORSESHOE_SPEED);
        //System.out.println("diamond horseshoe stats:" + DIAMOND_HORSESHOES_ITEM.getArmorBonus() + ", " + DIAMOND_HORSESHOES_ITEM.getSpeedBonus() + "; target values in config: " + ModConfigs.DIAMOND_HORSESHOE_ARMOR + ", " + ModConfigs.DIAMOND_HORSESHOE_SPEED);

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER,
                2,
                factories -> factories.add(new TradeOffers.SellItemFactory(IRON_HORSESHOES_ITEM, 8, 1, 15)));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER,
                5,
                factories -> factories.add(new TradeOffers.SellItemFactory(DIAMOND_HORSESHOES_ITEM, 32, 1, 30)));

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin()) {
                if (LootTables.NETHER_BRIDGE_CHEST.equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(UniformLootNumberProvider.create(1.0F, 2.0F)).
                            with(ItemEntry.builder(ModItems.GOLD_HORSESHOES_ITEM).weight(4));
                    tableBuilder.pool(poolBuilder);
                }
                if (LootTables.BASTION_BRIDGE_CHEST.equals(id) || LootTables.BASTION_HOGLIN_STABLE_CHEST.equals(id) || LootTables.BASTION_OTHER_CHEST.equals(id) || LootTables.BASTION_TREASURE_CHEST.equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(UniformLootNumberProvider.create(2.0F, 3.0F)).
                            with(ItemEntry.builder(ModItems.GOLD_HORSESHOES_ITEM).weight(8));
                    tableBuilder.pool(poolBuilder);
                }
                if (LootTables.VILLAGE_ARMORER_CHEST.equals(id) || LootTables.VILLAGE_WEAPONSMITH_CHEST.equals(id) || LootTables.VILLAGE_TOOLSMITH_CHEST.equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(UniformLootNumberProvider.create(1.0F, 2.0F))
                            .with(ItemEntry.builder(ModItems.IRON_HORSESHOES_ITEM).weight(8));
                    tableBuilder.pool(poolBuilder);
                }
                if (LootTables.DESERT_PYRAMID_CHEST.equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(UniformLootNumberProvider.create(1.0F, 2.0F))
                            .with(ItemEntry.builder(ModItems.IRON_HORSESHOES_ITEM).weight(8))
                            .with(ItemEntry.builder(ModItems.DIAMOND_HORSESHOES_ITEM).weight(4))
                            .with(ItemEntry.builder(ModItems.GOLD_HORSESHOES_ITEM).weight(1));
                    tableBuilder.pool(poolBuilder);
                }

                if (LootTables.SIMPLE_DUNGEON_CHEST.equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1.0F))
                            .with(ItemEntry.builder(ModItems.IRON_HORSESHOES_ITEM).weight(4))
                            .with(ItemEntry.builder(ModItems.DIAMOND_HORSESHOES_ITEM).weight(1));
                    tableBuilder.pool(poolBuilder);
                }

                if (LootTables.END_CITY_TREASURE_CHEST.equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(UniformLootNumberProvider.create(1.0F, 2.0F))
                            .with(ItemEntry.builder(ModItems.IRON_HORSESHOES_ITEM))
                            .with(ItemEntry.builder(ModItems.GOLD_HORSESHOES_ITEM))
                            .with(ItemEntry.builder(ModItems.DIAMOND_HORSESHOES_ITEM));
                    tableBuilder.pool(poolBuilder);
                }

                if (LootTables.ANCIENT_CITY_CHEST.equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1.0F))
                            .with(ItemEntry.builder(ModItems.DIAMOND_HORSESHOES_ITEM).weight(8));
                    tableBuilder.pool(poolBuilder);
                }

                if (LootTables.WOODLAND_MANSION_CHEST.equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1.0F))
                            .with(ItemEntry.builder(ModItems.IRON_HORSESHOES_ITEM).weight(8))
                            .with(ItemEntry.builder(ModItems.DIAMOND_HORSESHOES_ITEM).weight(4));
                    tableBuilder.pool(poolBuilder);
                }

            }

        });
    }
}
