package net.hyper_pigeon.horseshoes.mixin;

import net.hyper_pigeon.horseshoes.Horseshoes;
import net.hyper_pigeon.horseshoes.duck_types.HorseshoeWearingMob;
import net.hyper_pigeon.horseshoes.items.HorseshoesItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.ArmorSlot;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseScreenHandler.class)
public abstract class HorseScreenHandlerMixin extends ScreenHandler {

    private SimpleInventory horseshoeInventory;

    protected HorseScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void addHorseshoeSlot(int syncId, PlayerInventory playerInventory, Inventory inventory, AbstractHorseEntity entity, int slotColumnCount, CallbackInfo ci){
        int slot = 0;

        HorseshoeWearingMob horseshoeWearingMob = (HorseshoeWearingMob) entity;
        this.horseshoeInventory = horseshoeWearingMob.getInventory();
        this.addSlot(new ArmorSlot(horseshoeWearingMob.getInventory(), entity, EquipmentSlot.FEET, slot, 8, 52, (Identifier)null) {
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

    @Inject(method = "onClosed", at = @At("TAIL"))
    public void onClosed(PlayerEntity player, CallbackInfo ci) {
        this.horseshoeInventory.onClose(player);
    }

}
