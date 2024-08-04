package net.hyper_pigeon.horseshoes.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HorseshoesItem extends Item {

    private float speedBonus;
    private float armorBonus;
    private final Identifier entityTexture;

    public HorseshoesItem(float speedBonus, float armorBonus, Settings settings, Identifier entityTexture) {
        super(settings);
        this.speedBonus = speedBonus;
        this.armorBonus = armorBonus;
        this.entityTexture = entityTexture;
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

    public Identifier getEntityTexture(){return entityTexture;}

    public boolean isEnchantable(ItemStack stack) {
        return stack.getMaxCount() == 1 && stack.contains(DataComponentTypes.MAX_DAMAGE);
    }


}
