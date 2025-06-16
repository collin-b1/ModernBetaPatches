package me.collinb.modernbetapatches.mixin;

import me.collinb.modernbetapatches.ModernBetaPatches;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TrapdoorBlock.class)
public class TrapdoorBlockMixin {
    @Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
    private void onPlace(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        if (ModernBetaPatches.isModernBeta()) {
            // Cancel if trapdoor was placed directly on ceiling or floor
            if (ctx.getSide() == Direction.UP || ctx.getSide() == Direction.DOWN) {
                cir.setReturnValue(null);
            }

            BlockPos pos = ctx.getBlockPos();
            Direction side = ctx.getSide();
            BlockPos supportPos = pos.offset(side.getOpposite());
            BlockState supportBlock = ctx.getWorld().getBlockState(supportPos);

            // Cancel if placed on transparent block
            if (supportBlock.isTransparent()) {
                cir.setReturnValue(null);
            }
        }
    }

    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void preventUpperBlock(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        if (ModernBetaPatches.isModernBeta()) {
            // Change trapdoor to lower half if necessary
            BlockState state = cir.getReturnValue();
            if (state != null && state.get(TrapdoorBlock.HALF) == BlockHalf.TOP) {
                cir.setReturnValue(state.with(TrapdoorBlock.HALF, BlockHalf.BOTTOM));
            }
        }
    }
}
