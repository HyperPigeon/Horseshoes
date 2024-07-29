package net.hyper_pigeon.horseshoes.mixin;

import net.hyper_pigeon.horseshoes.Horseshoes;
import net.hyper_pigeon.horseshoes.duck_types.HorseshoeWearingMob;
import net.hyper_pigeon.horseshoes.items.HorseshoesItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends AnimalEntity implements HorseshoeWearingMob, InventoryChangedListener {
    protected SimpleInventory horseshoe = new SimpleInventory(1);

    protected AbstractHorseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean hasHorseshoes() {
        return !horseshoe.isEmpty() && (horseshoe.getStack(getHorseshoesSlot()).getItem() instanceof HorseshoesItem);
    }

    public int getHorseshoesSlot(){
        return 0;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void handleHorseshoes(CallbackInfo ci){
        applyHorseshoesBonus();
    }



    public void applyHorseshoesBonus() {
        boolean bl = this.hasHorseshoes();
        EntityAttributeInstance entitySpeedAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        EntityAttributeInstance entityArmorAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        if(!this.getWorld().isClient()) {
            if(this.hasHorseshoes() && !entitySpeedAttributeInstance.hasModifier(Horseshoes.HORSESHOE_BOOST) && !entityArmorAttributeInstance.hasModifier(Horseshoes.HORSESHOE_ARMOR_BONUS)) {
                  int slot = getHorseshoesSlot();
                  ItemStack stack= horseshoe.getStack(slot);
                  float speedBonus = ((HorseshoesItem)(stack.getItem())).getSpeedBonus();
                  float armorBonus = ((HorseshoesItem)(stack.getItem())).getArmorBonus();
                  entitySpeedAttributeInstance.addTemporaryModifier(new EntityAttributeModifier(Horseshoes.HORSESHOE_BOOST, speedBonus, EntityAttributeModifier.Operation.ADD_VALUE));
                  entityArmorAttributeInstance.addTemporaryModifier(new EntityAttributeModifier(Horseshoes.HORSESHOE_ARMOR_BONUS, armorBonus, EntityAttributeModifier.Operation.ADD_VALUE));
                if (this.age > 20 && !bl) {
                    this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5F, 1.0F);
                }
            }
            else {
                entitySpeedAttributeInstance.removeModifier(Horseshoes.HORSESHOE_BOOST);
                entityArmorAttributeInstance.removeModifier(Horseshoes.HORSESHOE_ARMOR_BONUS);
            }
        }

    }


    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    protected void writeHorseshoesToNbt(NbtCompound nbt, CallbackInfo ci){
        if (this.hasHorseshoes() && !this.horseshoe.getStack(getHorseshoesSlot()).isEmpty()) {
            nbt.put("HorseshoesItem", ItemStack.CODEC.encodeStart(getServer().getRegistryManager().getOps(NbtOps.INSTANCE),this.horseshoe.getStack(getHorseshoesSlot())).getPartialOrThrow());
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    protected void readHorseshoesFromNbt(NbtCompound nbt, CallbackInfo ci){
        if (nbt.contains("HorseshoesItem", 10)) {
            ItemStack itemStack = ItemStack.CODEC.parse(getServer().getRegistryManager().getOps(NbtOps.INSTANCE), nbt.getCompound("HorseshoesItem")).getPartialOrThrow();
            if (itemStack.getItem() instanceof HorseshoesItem) {
                this.horseshoe.setStack(getHorseshoesSlot(), itemStack);
            }
        }
    }

    public SimpleInventory getInventory() {
        return horseshoe;
    }
}
