package me.collinb.modernbetapatches.mixin;

import com.mojang.authlib.GameProfile;
import me.collinb.modernbetapatches.ModernBetaPatches;
import me.collinb.modernbetapatches.manager.CapeManager;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;
import java.util.function.Supplier;

@Mixin(PlayerListEntry.class)
public abstract class PlayerListEntryMixin {

    @Shadow
    @Final
    private GameProfile profile;

    @Inject(method = "texturesSupplier", at = @At("TAIL"))
    private static void loadModernBetaCape(GameProfile profile, CallbackInfoReturnable<Supplier<SkinTextures>> cir) {
        UUID uuid = profile.getId();
        CapeManager.fetchCape(uuid);
    }

    @Inject(method = "getSkinTextures", at = @At("TAIL"), cancellable = true)
    private void overrideSkinTextures(CallbackInfoReturnable<SkinTextures> cir) {
        if (!ModernBetaPatches.isModernBeta()) return;
        SkinTextures originalSkinTextures = cir.getReturnValue();
        Identifier modernBetaCape = CapeManager.getCape(profile.getId());
        SkinTextures newSkinTextures = new SkinTextures(
                originalSkinTextures.texture(), originalSkinTextures.textureUrl(),
                (modernBetaCape == null ?  originalSkinTextures.capeTexture() : modernBetaCape),
                originalSkinTextures.elytraTexture(),
                originalSkinTextures.model(), originalSkinTextures.secure());
        cir.setReturnValue(newSkinTextures);
    }
}
