package me.collinb.modernbetacompanion.mixin;

import me.collinb.modernbetacompanion.ModernBetaCompanion;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "isSwimming", at = @At("HEAD"), cancellable = true)
    private void preventSwimming(CallbackInfoReturnable<Boolean> cir) {
        if (ModernBetaCompanion.isModernBeta()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isCrawling", at = @At("HEAD"), cancellable = true)
    private void preventCrawling(CallbackInfoReturnable<Boolean> cir) {
        if (ModernBetaCompanion.isModernBeta()) {
            cir.setReturnValue(false);
        }
    }
}
