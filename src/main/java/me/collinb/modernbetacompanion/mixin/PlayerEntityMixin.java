package me.collinb.modernbetacompanion.mixin;

import me.collinb.modernbetacompanion.ModernBetaCompanion;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract PlayerAbilities getAbilities();

    protected PlayerEntityMixin(World world) {
        super(EntityType.PLAYER, world);
    }

    @Redirect(method = "updatePose", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;canChangeIntoPose(Lnet/minecraft/entity/EntityPose;)Z"))
    private boolean preventSwimmingAndCrawling(PlayerEntity instance, EntityPose pose) {
        if (ModernBetaCompanion.isModernBeta()) {
            if (this.getPose() == EntityPose.SWIMMING) {
                this.setPose(EntityPose.STANDING);
            }
        }
        return false;
    }
}
