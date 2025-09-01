package io.github.neptunethefox.dfrpc.discord;

import dev.dfonline.flint.Flint;
import dev.dfonline.flint.FlintAPI;
import dev.dfonline.flint.feature.core.FeatureTraitType;
import dev.dfonline.flint.feature.trait.TickedFeature;
import dev.dfonline.flint.hypercube.Mode;
import dev.dfonline.flint.hypercube.Plot;
import io.github.neptunethefox.dfrpc.DiamondFireRPC;
import io.github.neptunethefox.dfrpc.config.ConfigModel;
import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;

import java.util.Objects;

public class DiscordRPC implements TickedFeature {
    public static final RichPresence richPresence = new RichPresence();
    private final long clientID;
    private int updateTick = 0;

    public DiscordRPC(long clientID) {
        richPresence.setLargeImage("diamondfire", "DiamondFire");
        this.clientID = clientID;

        if (DiamondFireRPC.CONFIG.ENABLED())
            DiscordIPC.start(clientID, null);
    }



    @Override
    public void tick() {
        updateTick++;
        if (updateTick == 24) {
            updateTick = 0;
            richPresence.setLargeImage("diamondfire", "DiamondFire");
            if (Flint.getUser().getNode() == null || !DiamondFireRPC.CONFIG.ENABLED()) {
                DiscordIPC.stop();
                return;
            }

            if (!DiscordIPC.isConnected()) {
                DiscordIPC.start(this.clientID, null);
            }

            if (Flint.getUser().getPlot() == null && Flint.getUser().getMode() == Mode.SPAWN) {
                richPresence.setDetails(DiamondFireRPC.CONFIG.IN_SPAWN_MESSAGE().formatted(Flint.getUser().getNode().getName()));
                richPresence.setSmallImage(null, null);
            } else {
                Plot currentPlot = Flint.getUser().getPlot();
                richPresence.setLargeImage("plot", Flint.getUser().getPlot().getName().getString());

                if (DiamondFireRPC.CONFIG.SHOW_MODE() != ConfigModel.ModeHiding.FULL_HIDE) {
                    if (!Objects.equals(Flint.getUser().getMode().getName(), "Dev"))
                        richPresence.setDetails(Flint.getUser().getMode().getName() + "ing on " + currentPlot.getName().getString());
                    else
                        richPresence.setDetails("Coding on " + currentPlot.getName().getString());
                } else {
                    richPresence.setDetails("???");
                }

                if (DiamondFireRPC.CONFIG.SHOW_MODE() != ConfigModel.ModeHiding.SEMI_HIDE || DiamondFireRPC.CONFIG.SHOW_MODE() != ConfigModel.ModeHiding.FULL_HIDE)
                    richPresence.setSmallImage(Flint.getUser().getMode().getName().toLowerCase(), Flint.getUser().getMode().getName());
                else
                    richPresence.setSmallImage(null, null);
            }
            DiscordIPC.setActivity(richPresence);
        }
    }

    /**
     * Called when the player leaves DF.
     */
    public static void informOfDisconnect() {
        DiscordIPC.stop();
    }

}

