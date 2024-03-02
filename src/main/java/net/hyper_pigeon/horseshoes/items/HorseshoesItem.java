package net.hyper_pigeon.horseshoes.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HorseshoesItem extends ArmorItem {

    private float speedBonus;
    private float armorBonus;

    public HorseshoesItem(float speedBonus, float armorBonus, ArmorMaterial material, Settings settings) {
        super(material, Type.BOOTS, settings);
        this.speedBonus = speedBonus;
        this.armorBonus = armorBonus;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        return TypedActionResult.fail(itemStack);
    }

    public float getSpeedBonus(){
        return speedBonus;
    }

    public float getArmorBonus(){
        return armorBonus;
    }

}
