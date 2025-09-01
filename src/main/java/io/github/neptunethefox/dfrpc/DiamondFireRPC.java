package io.github.neptunethefox.dfrpc;

import dev.dfonline.flint.FlintAPI;
import io.github.neptunethefox.dfrpc.config.DFRPCConfig;
import io.github.neptunethefox.dfrpc.discord.DiscordRPC;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import java.util.Objects;

public class DiamondFireRPC implements ClientModInitializer {

    public static final DFRPCConfig CONFIG = DFRPCConfig.createAndLoad();
    public static long CLIENT_ID = 1404367293378592818L;

    @Override
    public void onInitializeClient() {
        FlintAPI.registerFeatures(new DiscordRPC(CLIENT_ID));

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            if (Objects.requireNonNull(handler.getServerInfo()).address.contains("diamondfire")) {
                DiscordRPC.informOfDisconnect();
            }
        });
    }
}
