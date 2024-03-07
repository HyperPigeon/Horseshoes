package net.hyper_pigeon.horseshoes.mixin;

import net.hyper_pigeon.horseshoes.items.HorseshoesItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @Final
    @Shadow
    public EnchantmentTarget target;

    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    public void acceptIfHorseshoes(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if(stack.getItem() instanceof HorseshoesItem && target.equals(EnchantmentTarget.ARMOR_FEET) ) {
            cir.setReturnValue(true);
        }
    }
}
