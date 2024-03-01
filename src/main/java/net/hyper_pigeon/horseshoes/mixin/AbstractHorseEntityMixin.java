package net.hyper_pigeon.horseshoes.mixin;

import net.hyper_pigeon.horseshoes.Horseshoes;
import net.hyper_pigeon.horseshoes.items.HorseshoesItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends AnimalEntity {

    @Shadow
    protected SimpleInventory items;

    protected AbstractHorseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean hasHorseshoes() {
        return !items.isEmpty() && (items.getStack(2).getItem() instanceof HorseshoesItem);
    }

    private void addHorseshoeBoost(EntityAttributeInstance entityAttributeInstance, HorseshoesItem horseshoesItem){
        if(horseshoesItem.equals(Horseshoes.IRON_HORSESHOES_ITEM) && !entityAttributeInstance.hasModifier(Horseshoes.IRON_HORSESHOE_BOOST)){
            entityAttributeInstance.addTemporaryModifier(Horseshoes.IRON_HORSESHOE_BOOST);
        }
        else if(horseshoesItem.equals(Horseshoes.DIAMOND_HORSESHOES_ITEM) && !entityAttributeInstance.hasModifier(Horseshoes.DIAMOND_HORSESHOE_BOOST)){
            entityAttributeInstance.addTemporaryModifier(Horseshoes.DIAMOND_HORSESHOE_BOOST);
        }
        else if(horseshoesItem.equals(Horseshoes.GOLD_HORSESHOES_ITEM) && !entityAttributeInstance.hasModifier(Horseshoes.GOLD_HORSESHOE_BOOST)){
            entityAttributeInstance.addTemporaryModifier(Horseshoes.GOLD_HORSESHOE_BOOST);
        }
    }

    private void removeHorseshoeBoost(EntityAttributeInstance entityAttributeInstance){
        if(entityAttributeInstance.hasModifier(Horseshoes.IRON_HORSESHOE_BOOST)){
            entityAttributeInstance.removeModifier(Horseshoes.IRON_HORSESHOE_BOOST);
        }
        else if(entityAttributeInstance.hasModifier(Horseshoes.GOLD_HORSESHOE_BOOST)){
            entityAttributeInstance.removeModifier(Horseshoes.GOLD_HORSESHOE_BOOST);
        }
        else if(entityAttributeInstance.hasModifier(Horseshoes.DIAMOND_HORSESHOE_BOOST)){
            entityAttributeInstance.removeModifier(Horseshoes.DIAMOND_HORSESHOE_BOOST);
        }
    }



    @Inject(method = "onInventoryChanged", at = @At("TAIL"))
    public void onInventoryChanged(Inventory sender, CallbackInfo ci) {
        boolean bl = this.hasHorseshoes();
        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

        if(!this.getWorld().isClient()) {
            assert entityAttributeInstance != null;
            removeHorseshoeBoost(entityAttributeInstance);
            if(this.hasHorseshoes()) {
                this.equipStack(EquipmentSlot.FEET, sender.getStack(2));
                addHorseshoeBoost(entityAttributeInstance, (HorseshoesItem) items.getStack(2).getItem());
                if (this.age > 20 && !bl) {
                    this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5F, 1.0F);
                }
            }
        }

    }


    @Inject(method = "getInventorySize", at = @At("HEAD"), cancellable = true)
    protected void getInventorySizeWithHorseShoes(CallbackInfoReturnable<Integer> cir) {
        if(this.getType().isIn(Horseshoes.ALLOWED)){
            cir.setReturnValue(3);
            cir.cancel();
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    protected void writeHorseshoesToNbt(NbtCompound nbt, CallbackInfo ci){
        if (this.hasHorseshoes() && !this.items.getStack(2).isEmpty()) {
            nbt.put("HorseshoesItem", this.items.getStack(2).writeNbt(new NbtCompound()));
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    protected void readHorseshoesFromNbt(NbtCompound nbt, CallbackInfo ci){
        if (nbt.contains("HorseshoesItem", 10)) {
            ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("HorseshoesItem"));
            if (itemStack.getItem() instanceof HorseshoesItem) {
                this.items.setStack(2, itemStack);
            }
        }
    }
}
