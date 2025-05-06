package me.collinb.modernbetacompanion.mixin;

import me.collinb.modernbetacompanion.ModernBetaCompanion;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "canStartSprinting", at = @At("RETURN"), cancellable = true)
    private void preventSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (ModernBetaCompanion.isModernBeta()) {
            if (ModernBetaCompanion.getPlayerGameMode() == GameMode.SURVIVAL) {
                cir.setReturnValue(false);
            }
        }
    }
}
