package net.hyper_pigeon.horseshoes.mixin;

import net.hyper_pigeon.horseshoes.items.HorseshoesItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/enchantment/EnchantmentTarget$2")
public class EnchantmentTargetMixin {
    @Inject(method = "Lnet/minecraft/enchantment/EnchantmentTarget$2;isAcceptableItem(Lnet/minecraft/item/Item;)Z", at = @At("HEAD"), cancellable = true)
    public void acceptHorseshoes(Item item, CallbackInfoReturnable<Boolean> ci){
        if (item instanceof HorseshoesItem) {
            ci.setReturnValue(true);
        }
    }
}
