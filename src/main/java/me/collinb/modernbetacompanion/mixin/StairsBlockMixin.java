package me.collinb.modernbetacompanion.mixin;

import me.collinb.modernbetacompanion.ModernBetaCompanion;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StairsBlock.class)
public class StairsBlockMixin {
    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void preventUpperBlock(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        if (ModernBetaCompanion.isModernBeta()) {
            BlockState state = cir.getReturnValue();
            if (state != null && state.get(StairsBlock.HALF) == BlockHalf.TOP) {
                cir.setReturnValue(state.with(StairsBlock.HALF, BlockHalf.BOTTOM));
            }
        }
    }

    @Inject(method = "getStairShape", at = @At("HEAD"), cancellable = true)
    private static void preventCornerStairs(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<StairShape> cir) {
        if (ModernBetaCompanion.isModernBeta()) {
            cir.setReturnValue(StairShape.STRAIGHT);
        }
    }
}
