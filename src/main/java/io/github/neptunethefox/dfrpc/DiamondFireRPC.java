package io.github.neptunethefox.dfrpc;

import dev.dfonline.flint.Flint;
import dev.dfonline.flint.FlintAPI;
import io.github.neptunethefox.dfrpc.config.DFRPCConfig;
import io.github.neptunethefox.dfrpc.discord.DiscordRPC;
import io.github.neptunethefox.dfrpc.plot.PlotRPC;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import java.util.Objects;

public class DiamondFireRPC implements ClientModInitializer {

    public static final DFRPCConfig CONFIG = DFRPCConfig.createAndLoad();
    public static long CLIENT_ID = 1404367293378592818L;

    @Override
    public void onInitializeClient() {
        FlintAPI.confirmLocationWithLocate();
        FlintAPI.setDebugging(false);
        FlintAPI.registerFeatures(new PlotRPC(), new DiscordRPC(CLIENT_ID));

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            if (Objects.requireNonNull(handler.getServerInfo()).address.contains("diamondfire")) {
                DiscordRPC.informOfDisconnect();
            }
        });
    }
}
