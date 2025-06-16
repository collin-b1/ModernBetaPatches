package me.collinb.modernbetapatches.mixin;

import me.collinb.modernbetapatches.ModernBetaPatches;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "canStartSprinting", at = @At("RETURN"), cancellable = true)
    private void preventSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (ModernBetaPatches.isModernBeta()) {
            if (ModernBetaPatches.getPlayerGameMode() == GameMode.SURVIVAL) {
                cir.setReturnValue(false);
            }
        }
    }
}
