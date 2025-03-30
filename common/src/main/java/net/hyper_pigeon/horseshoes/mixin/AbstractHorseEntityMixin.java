package net.hyper_pigeon.horseshoes.mixin;

import net.hyper_pigeon.horseshoes.Constants;
import net.hyper_pigeon.horseshoes.duck_types.HorseshoeWearingMob;
import net.hyper_pigeon.horseshoes.items.HorseshoesItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseEntityMixin extends Animal implements HorseshoeWearingMob, ContainerListener {

    @Unique
    protected SimpleContainer horseshoe = new SimpleContainer(1);

    protected AbstractHorseEntityMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Unique
    public boolean hasHorseshoes() {
        return !horseshoe.isEmpty() && (horseshoe.getItem(getHorseshoesSlot()).getItem() instanceof HorseshoesItem);
    }

    @Unique
    public int getHorseshoesSlot(){
        return 0;
    }

    @Inject(method = "createInventory", at = @At("TAIL"))
    public void registerInventory(CallbackInfo ci) {
        horseshoe.addListener(this);
    }

    @Inject(method = "containerChanged", at = @At("TAIL"))
    public void handleHorseshoes(CallbackInfo ci){
        applyHorseshoesBonus();
    }

    public void applyHorseshoesBonus() {
        boolean bl = this.hasHorseshoes();
        AttributeInstance entitySpeedAttributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance entityArmorAttributeInstance = this.getAttribute(Attributes.ARMOR);
        if(!this.level().isClientSide()) {
            entitySpeedAttributeInstance.removeModifier(Constants.HORSESHOE_BOOST);
            entityArmorAttributeInstance.removeModifier(Constants.HORSESHOE_ARMOR_BONUS);
            if(this.hasHorseshoes() && !entitySpeedAttributeInstance.hasModifier(Constants.HORSESHOE_BOOST) && !entityArmorAttributeInstance.hasModifier(Constants.HORSESHOE_ARMOR_BONUS)) {
                  int slot = getHorseshoesSlot();
                  ItemStack stack= horseshoe.getItem(slot);
                  this.setItemSlot(EquipmentSlot.FEET, stack);
                  this.setDropChance(EquipmentSlot.FEET, 0.0F);
                  float speedBonus = ((HorseshoesItem)(stack.getItem())).getSpeedBonus();
                  float armorBonus = ((HorseshoesItem)(stack.getItem())).getArmorBonus();
                  entitySpeedAttributeInstance.addTransientModifier(new AttributeModifier(Constants.HORSESHOE_BOOST, speedBonus, AttributeModifier.Operation.ADD_VALUE));
                  entityArmorAttributeInstance.addTransientModifier(new AttributeModifier(Constants.HORSESHOE_ARMOR_BONUS, armorBonus, AttributeModifier.Operation.ADD_VALUE));
                if (this.tickCount > 20 && !bl) {
                    this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
                }
            }
        }

    }


    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    protected void writeHorseshoesToNbt(CompoundTag nbt, CallbackInfo ci){
        if (this.hasHorseshoes() && !this.horseshoe.getItem(getHorseshoesSlot()).isEmpty()) {
            nbt.put("HorseshoesItem", ItemStack.CODEC.encodeStart(getServer().registryAccess().createSerializationContext(NbtOps.INSTANCE),this.horseshoe.getItem(getHorseshoesSlot())).getPartialOrThrow());
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    protected void readHorseshoesFromNbt(CompoundTag nbt, CallbackInfo ci){
        if (nbt.contains("HorseshoesItem", 10)) {
            ItemStack itemStack = ItemStack.CODEC.parse(getServer().registryAccess().createSerializationContext(NbtOps.INSTANCE), nbt.getCompound("HorseshoesItem")).getPartialOrThrow();
            if (itemStack.getItem() instanceof HorseshoesItem) {
                this.horseshoe.setItem(getHorseshoesSlot(), itemStack);
            }
        }
    }

    public SimpleContainer getInventory() {
        return horseshoe;
    }
}
