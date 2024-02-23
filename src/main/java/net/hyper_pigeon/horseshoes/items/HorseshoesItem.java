package net.hyper_pigeon.horseshoes.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HorseshoesItem extends ArmorItem {
    public HorseshoesItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        return TypedActionResult.fail(itemStack);
    }

}
