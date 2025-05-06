package me.collinb.modernbetacompanion.mixin;

import me.collinb.modernbetacompanion.ModernBetaCompanion;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TrapdoorBlock.class)
public class TrapdoorBlockMixin {
    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void preventUpperBlock(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        if (ModernBetaCompanion.isModernBeta()) {
            BlockState state = cir.getReturnValue();
            if (state != null && state.get(TrapdoorBlock.HALF) == BlockHalf.TOP) {
                cir.setReturnValue(state.with(TrapdoorBlock.HALF, BlockHalf.BOTTOM));
            }
        }
    }
}
