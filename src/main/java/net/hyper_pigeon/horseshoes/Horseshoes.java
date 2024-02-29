package net.hyper_pigeon.horseshoes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.hyper_pigeon.horseshoes.items.HorseshoesItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

public class Horseshoes implements ModInitializer {

    private static final Identifier ALLOWED_ID = new Identifier("horseshoes:allowed_to_wear_horseshoes");
    public static final TagKey<EntityType<?>> ALLOWED = TagKey.of(RegistryKeys.ENTITY_TYPE, ALLOWED_ID);

    public static final EntityAttributeModifier IRON_HORSESHOE_BOOST = new EntityAttributeModifier("IRON_HORSESHOE_BOOST",0.1,
            EntityAttributeModifier.Operation.fromId(0));
    public static final EntityAttributeModifier DIAMOND_HORSESHOE_BOOST = new EntityAttributeModifier("DIAMOND_HORSESHOE_BOOST",0.15,
            EntityAttributeModifier.Operation.fromId(0));
    public static final EntityAttributeModifier GOLD_HORSESHOE_BOOST = new EntityAttributeModifier("GOLD_HORSESHOE_BOOST",0.2,
            EntityAttributeModifier.Operation.fromId(0));

    public static final HorseshoesItem DIAMOND_HORSESHOES_ITEM = new HorseshoesItem(ArmorMaterials.DIAMOND, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1));
    public static final HorseshoesItem IRON_HORSESHOES_ITEM = new HorseshoesItem(ArmorMaterials.IRON, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1));
    public static final HorseshoesItem GOLD_HORSESHOES_ITEM = new HorseshoesItem(ArmorMaterials.GOLD, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1));

    public static final Identifier NETHER_BRIDGE_LOOT_TABLE = Identifier.of("minecraft","loot_tables/chests/nether_bridge");
    public static final Identifier BASTION_LOOT_TABLE = Identifier.of("minecraft","loot_tables/chests/loot_bastion");

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM,new Identifier("horseshoes", "diamond_horseshoes"), DIAMOND_HORSESHOES_ITEM);
        Registry.register(Registries.ITEM,new Identifier("horseshoes", "iron_horseshoes"), IRON_HORSESHOES_ITEM);
        Registry.register(Registries.ITEM,new Identifier("horseshoes", "gold_horseshoes"), GOLD_HORSESHOES_ITEM);

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

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && NETHER_BRIDGE_LOOT_TABLE.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(1.0F, 2.0F)).
                        with(ItemEntry.builder(Horseshoes.GOLD_HORSESHOES_ITEM).weight(8));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && BASTION_LOOT_TABLE.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(3.0F, 4.0F)).
                        with(ItemEntry.builder(Horseshoes.GOLD_HORSESHOES_ITEM).weight(12));
                tableBuilder.pool(poolBuilder);
            }
        });


    }
}
