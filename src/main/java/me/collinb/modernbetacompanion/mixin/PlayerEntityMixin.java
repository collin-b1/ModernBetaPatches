package me.collinb.modernbetacompanion.mixin;

import me.collinb.modernbetacompanion.ModernBetaCompanion;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(World world) {
        super(EntityType.PLAYER, world);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void preventSwimmingAndCrawling(CallbackInfo ci) {
        if (ModernBetaCompanion.isModernBeta()) {
            if (this.getPose() == EntityPose.SWIMMING) {
                this.setPose(EntityPose.STANDING);
            }
        }
    }

    @Override
    public void setPose(EntityPose pose) {
        if (ModernBetaCompanion.isModernBeta()) {
            if (pose == EntityPose.SWIMMING) {
                return;
            }
        }
        super.setPose(pose);
    }
}
