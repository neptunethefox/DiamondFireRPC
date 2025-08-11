package io.github.neptunethefox.dfrpc;

import dev.dfonline.flint.FlintAPI;
import io.github.neptunethefox.dfrpc.config.ConfigModel;
import io.github.neptunethefox.dfrpc.config.DFRPCConfig;
import io.github.neptunethefox.dfrpc.discord.DiscordRPC;
import net.fabricmc.api.ClientModInitializer;

public class DiamondFireRPC implements ClientModInitializer {

    public static final DFRPCConfig CONFIG = DFRPCConfig.createAndLoad();

    @Override
    public void onInitializeClient() {
        FlintAPI.registerFeatures(new DiscordRPC(ConfigModel.CLIENT_ID));
    }
}
