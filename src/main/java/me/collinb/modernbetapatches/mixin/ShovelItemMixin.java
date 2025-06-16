package me.collinb.modernbetapatches.mixin;

import me.collinb.modernbetapatches.ModernBetaPatches;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void preventFlattening(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (ModernBetaPatches.isModernBeta()) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
