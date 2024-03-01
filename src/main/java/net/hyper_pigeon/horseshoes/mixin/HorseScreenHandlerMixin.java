package net.hyper_pigeon.horseshoes.mixin;

import net.hyper_pigeon.horseshoes.Horseshoes;
import net.hyper_pigeon.horseshoes.items.HorseshoesItem;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseScreenHandler.class)
public abstract class HorseScreenHandlerMixin extends ScreenHandler {
    protected HorseScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void addHorseshoeSlot(int syncId, PlayerInventory playerInventory, Inventory inventory, AbstractHorseEntity entity, CallbackInfo ci){
        int slot = entity.hasArmorSlot() ? 2 : 1;
        this.addSlot(new Slot(inventory, slot, 8, 54) {
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof HorseshoesItem && !this.hasStack() && entity.getType().isIn(Horseshoes.ALLOWED);
            }

            public boolean isEnabled() {
                return  entity.getType().isIn(Horseshoes.ALLOWED);
            }

            public int getMaxItemCount() {
                return 1;
            }
        });
    }

}
