package me.collinb.modernbetacompanion.mixin;

import me.collinb.modernbetacompanion.ModernBetaCompanion;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FenceBlock.class)
public class FenceBlockMixin {
    @Inject(method = "canConnect", at = @At("HEAD"), cancellable = true)
    private void onlyConnectToFences(BlockState state, boolean neighborIsFullSquare, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (ModernBetaCompanion.isModernBeta()) {
            cir.setReturnValue(state.getBlock() instanceof FenceBlock);
        }
    }
}
