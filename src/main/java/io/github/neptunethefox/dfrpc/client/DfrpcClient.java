package io.github.neptunethefox.dfrpc.client;

import dev.dfonline.flint.FlintAPI;
import io.github.neptunethefox.dfrpc.discord.DiscordRPC;
import net.fabricmc.api.ClientModInitializer;

public class DfrpcClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FlintAPI.registerFeatures(new DiscordRPC(1404367293378592818L));
    }
}
