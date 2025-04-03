package net.hyper_pigeon.horseshoes.mixin;

import net.hyper_pigeon.horseshoes.Constants;
import net.hyper_pigeon.horseshoes.duck_types.HorseshoeWearingMob;
import net.hyper_pigeon.horseshoes.items.HorseshoesItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HorseInventoryMenu.class)
public abstract class HorseScreenHandlerMixin extends AbstractContainerMenu {

    @Unique
    private SimpleContainer horseshoeInventory;

    protected HorseScreenHandlerMixin(@Nullable MenuType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void addHorseshoeSlot(int syncId, Inventory playerInventory, Container inventory, AbstractHorse entity, int slotColumnCount, CallbackInfo ci){
        int slot = 0;

        HorseshoeWearingMob horseshoeWearingMob = (HorseshoeWearingMob) entity;
        this.horseshoeInventory = horseshoeWearingMob.getInventory();
        this.addSlot(new ArmorSlot(horseshoeWearingMob.getInventory(), entity, EquipmentSlot.FEET, slot, 8, 53, (ResourceLocation) null) {
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof HorseshoesItem && !this.hasItem() && entity.getType().is(Constants.ALLOWED);
            }

            public boolean isActive() {
                return entity.getType().is(Constants.ALLOWED);
            }

            public int getMaxStackSize() {
                return 1;
            }

        });
    }

    @Inject(method = "removed", at = @At("TAIL"))
    public void onClosed(Player player, CallbackInfo ci) {
        this.horseshoeInventory.stopOpen(player);
    }

    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true)
    public void horseshoeQuickMove(Player player, int slot, CallbackInfoReturnable<ItemStack> cir) {
        Slot slot2 = this.slots.get(slot);
        if (slot2 != null && slot2.hasItem()) {
            ItemStack itemStack2 = slot2.getItem();
            ItemStack itemStack = itemStack2.copy();
            if (slot == 38) {
                if (!this.moveItemStackTo(itemStack2, 2, this.slots.size(), true)) {
                    cir.setReturnValue(ItemStack.EMPTY);
                } else {
                    cir.setReturnValue(itemStack);
                    if (itemStack2.isEmpty()) {
                        slot2.setByPlayer(ItemStack.EMPTY);
                    } else {
                        slot2.setChanged();
                    }
                }
                cir.cancel();
            }
            else if (this.getSlot(38).mayPlace(itemStack2) && !this.getSlot(38).hasItem()) {
                if (!this.moveItemStackTo(itemStack2, 38, 39, false)) {
                    cir.setReturnValue(ItemStack.EMPTY);
                } else {
                    cir.setReturnValue(itemStack);
                    if (itemStack2.isEmpty()) {
                        slot2.setByPlayer(ItemStack.EMPTY);
                    } else {
                        slot2.setChanged();
                    }
                }
                cir.cancel();
            }
        }
    }

}
