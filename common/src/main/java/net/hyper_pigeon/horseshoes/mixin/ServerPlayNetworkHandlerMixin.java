package net.hyper_pigeon.horseshoes.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin {
    @ModifyExpressionValue(method = "handleMoveVehicle", at = @At(value = "CONSTANT", args = "doubleValue=0.0625", ordinal = 1))
    private double neverMoveWrongly(double original, @Local(ordinal = 0) Entity entity){
        return entity instanceof AbstractHorse ? original * 10 : original;
    }
}
