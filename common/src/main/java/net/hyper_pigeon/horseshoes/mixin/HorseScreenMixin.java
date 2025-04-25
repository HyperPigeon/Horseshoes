package net.hyper_pigeon.horseshoes.mixin;

import net.hyper_pigeon.horseshoes.Constants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseInventoryScreen.class)
public abstract class HorseScreenMixin extends AbstractContainerScreen {

    @Unique
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("horseshoes","textures/gui/container/horse.png");

    @Final
    @Shadow
    private AbstractHorse horse;

    public HorseScreenMixin(AbstractContainerMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Inject(method = "renderBg", at = @At("TAIL"))
    public void drawHorseshoesSlot(GuiGraphics context, float delta, int mouseX, int mouseY, CallbackInfo ci){
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        if(this.horse.getType().is(Constants.ALLOWED)) {
              context.blit(TEXTURE, i + 7, j + 53, 54, this.imageHeight + 54, 18, 18);
        }

    }
}
