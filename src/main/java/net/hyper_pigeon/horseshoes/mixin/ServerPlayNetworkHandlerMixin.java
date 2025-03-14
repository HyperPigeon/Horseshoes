package net.hyper_pigeon.horseshoes.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @ModifyExpressionValue(method = "onVehicleMove", at = @At(value = "CONSTANT", args = "doubleValue=0.0625", ordinal = 1))
    private double neverMoveWrongly(double original, @Local(ordinal = 0) Entity entity){
        return entity instanceof AbstractHorseEntity ? original * 10 : original;
    }
}
