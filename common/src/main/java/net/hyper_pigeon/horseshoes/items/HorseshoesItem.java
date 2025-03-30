package net.hyper_pigeon.horseshoes.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HorseshoesItem extends Item {

    private float speedBonus;
    private float armorBonus;
    private final ResourceLocation entityTexture;

    public HorseshoesItem(float speedBonus, float armorBonus, Properties settings, ResourceLocation entityTexture) {
        super(settings);
        this.speedBonus = speedBonus;
        this.armorBonus = armorBonus;
        this.entityTexture = entityTexture;
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        return InteractionResultHolder.fail(itemStack);
    }

    public float getSpeedBonus(){
        return speedBonus;
    }

    public float getArmorBonus(){
        return armorBonus;
    }

    public ResourceLocation getEntityTexture(){return entityTexture;}

    public boolean isEnchantable(ItemStack stack) {
        return stack.getMaxStackSize() == 1 && stack.has(DataComponents.MAX_DAMAGE);
    }


}
