package net.hyper_pigeon.horseshoes.mixin;

import net.hyper_pigeon.horseshoes.Horseshoes;
import net.hyper_pigeon.horseshoes.items.HorseshoesItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
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

    @Shadow public abstract boolean hasArmorSlot();

    protected AbstractHorseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean hasHorseshoes() {
        return !items.isEmpty() && (items.getStack(getHorseshoesSlot()).getItem() instanceof HorseshoesItem);
    }

    public int getHorseshoesSlot(){
        if (hasArmorSlot()) {
            return 2;
        }
        return 1;
    }



    @Inject(method = "onInventoryChanged", at = @At("TAIL"))
    public void onInventoryChanged(Inventory sender, CallbackInfo ci) {
        boolean bl = this.hasHorseshoes();
        EntityAttributeInstance entitySpeedAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        EntityAttributeInstance entityArmorAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        if(!this.getWorld().isClient()) {
            assert entitySpeedAttributeInstance != null;
            entitySpeedAttributeInstance.removeModifier(Horseshoes.HORSESHOE_BOOST_UUID);
            assert entityArmorAttributeInstance != null;
            entityArmorAttributeInstance.removeModifier(Horseshoes.HORSESHOE_ARMOR_BONUS_UUID);
            if(this.hasHorseshoes()) {
                  int slot = getHorseshoesSlot();
                  ItemStack stack= items.getStack(slot);
                  this.equipStack(EquipmentSlot.FEET, stack);
                  this.setEquipmentDropChance(EquipmentSlot.FEET, 0.0F);
                  float speedBonus = ((HorseshoesItem)(stack.getItem())).getSpeedBonus();
                  float armorBonus = ((HorseshoesItem)(stack.getItem())).getArmorBonus();
                  entitySpeedAttributeInstance.addTemporaryModifier(new EntityAttributeModifier(Horseshoes.HORSESHOE_BOOST_UUID,"Horseshoes speed bonus", speedBonus, EntityAttributeModifier.Operation.ADDITION));
                  entityArmorAttributeInstance.addTemporaryModifier(new EntityAttributeModifier(Horseshoes.HORSESHOE_ARMOR_BONUS_UUID,"Horse armor bonus", armorBonus, EntityAttributeModifier.Operation.ADDITION));
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
