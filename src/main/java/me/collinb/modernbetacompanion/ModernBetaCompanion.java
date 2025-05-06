package me.collinb.modernbetacompanion;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.GameMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModernBetaCompanion implements ClientModInitializer {
    public static final String MOD_ID = "modernbetacompanion";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static String currentServer = null;

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((clientPlayNetworkHandler, packetSender, minecraftClient) -> {
            if (minecraftClient.getCurrentServerEntry() != null) {
                currentServer = minecraftClient.getCurrentServerEntry().address;
                LOGGER.info("Connected to {}", currentServer);
            } else {
                currentServer = null;
            }
        });

        ClientPlayConnectionEvents.DISCONNECT.register(((clientPlayNetworkHandler, minecraftClient) -> {
            currentServer = null;
            LOGGER.info("Disconnected from {}", currentServer);
        }));
    }

    public static GameMode getPlayerGameMode() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.interactionManager != null) {
            return client.interactionManager.getCurrentGameMode();
        } else {
            return null;
        }
    }

    public static boolean isModernBeta() {
        return currentServer != null && currentServer.contains(".modernbeta.org");
    }
}
