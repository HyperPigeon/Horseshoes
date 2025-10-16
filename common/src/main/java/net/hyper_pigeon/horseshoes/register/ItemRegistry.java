package net.hyper_pigeon.horseshoes.register;

import net.hyper_pigeon.horseshoes.Constants;
import net.hyper_pigeon.horseshoes.items.HorseshoesItem;
import net.hyper_pigeon.horseshoes.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public final class ItemRegistry {

    public static final Supplier <HorseshoesItem> NETHERITE_HORSESHOES_ITEM = registerItem("netherite_horseshoes", () -> new HorseshoesItem(0.25F, 6.5F, new Item.Properties().stacksTo(1)
            , ResourceLocation.fromNamespaceAndPath("horseshoes", "textures/entity/horse/armor/netherite_horseshoes.png")));
    public static final Supplier <HorseshoesItem> DIAMOND_HORSESHOES_ITEM = registerItem("diamond_horseshoes", () -> new HorseshoesItem(0.15F, 5.5F, new Item.Properties().stacksTo(1)
            , ResourceLocation.fromNamespaceAndPath("horseshoes", "textures/entity/horse/armor/diamond_horseshoes.png")));
    public static final Supplier <HorseshoesItem> IRON_HORSESHOES_ITEM = registerItem("iron_horseshoes", () -> new HorseshoesItem(0.1F,3.5F,new Item.Properties().stacksTo(1)
            ,ResourceLocation.fromNamespaceAndPath("horseshoes", "textures/entity/horse/armor/iron_horseshoes.png")));
    public static final Supplier <HorseshoesItem> GOLD_HORSESHOES_ITEM =  registerItem("gold_horseshoes", () -> new HorseshoesItem(0.2F, 2.5F, new Item.Properties().stacksTo(1)
            ,ResourceLocation.fromNamespaceAndPath("horseshoes", "textures/entity/horse/armor/gold_horseshoes.png")));

    public static final Supplier<CreativeModeTab> HORSESHOES_TAB = Services.PLATFORM.registerCreativeModeTab("chickensaurs_items", () -> Services.PLATFORM.newCreativeTabBuilder()
            .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".horseshoe_items"))
            .icon(() -> new ItemStack(GOLD_HORSESHOES_ITEM.get()))
            .displayItems((enabledFeatures, entries) -> {
                entries.accept(IRON_HORSESHOES_ITEM.get());
                entries.accept(GOLD_HORSESHOES_ITEM.get());
                entries.accept(DIAMOND_HORSESHOES_ITEM.get());
                entries.accept(NETHERITE_HORSESHOES_ITEM.get());
            })
            .build());

    public static void init(){
    }

    private static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return Services.PLATFORM.registerItem(id, item);
    }
}
