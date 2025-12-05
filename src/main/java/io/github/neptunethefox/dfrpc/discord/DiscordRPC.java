package io.github.neptunethefox.dfrpc.discord;

import dev.dfonline.flint.Flint;
import dev.dfonline.flint.feature.trait.TickedFeature;
import dev.dfonline.flint.hypercube.Mode;
import dev.dfonline.flint.hypercube.Plot;
import io.github.neptunethefox.dfrpc.DiamondFireRPC;
import io.github.neptunethefox.dfrpc.config.ConfigModel;
import io.github.neptunethefox.dfrpc.plot.PlotRPC;
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
        if (updateTick >= 64) {
            updateTick = 0;

            if (Flint.getUser().getNode() == null || !DiamondFireRPC.CONFIG.ENABLED()) {
                DiscordIPC.stop();
                return;
            }

            if (!DiscordIPC.isConnected()) {
                DiscordIPC.start(this.clientID, null);
            }

            if (Flint.getUser().getPlot() == null && Flint.getUser().getMode() == Mode.SPAWN) {
                richPresence.setDetails(DiamondFireRPC.CONFIG.IN_SPAWN_MESSAGE().formatted(Flint.getUser().getNode().getName()));
                richPresence.setLargeImage("diamondfire", "DiamondFire");
                richPresence.setSmallImage(null, null);
            } else {
                Plot currentPlot = Flint.getUser().getPlot();

                if (DiamondFireRPC.CONFIG.SHOW_MODE() != ConfigModel.ModeHiding.FULL_HIDE) {
                    if (!Objects.equals(Flint.getUser().getMode().getName(), "Dev"))
                        richPresence.setDetails(Flint.getUser().getMode().getName() + "ing on " + currentPlot.getName().getString());
                    else
                        richPresence.setDetails("Coding on " + currentPlot.getName().getString());
                } else {
                    richPresence.setDetails("???");
                }

                if (DiamondFireRPC.CONFIG.ALLOW_PLOT_CONTROL() && PlotRPC.active) {
                    richPresence.setDetails(PlotRPC.details);

                    if (!Objects.equals(PlotRPC.largeImage, "plot")) {
                        richPresence.setLargeImage(PlotRPC.largeImage, PlotRPC.largeImageText);
                    } else if (!Objects.equals(PlotRPC.largeImageText, "")) {
                        richPresence.setLargeImage("plot", PlotRPC.largeImageText);
                    }
                } else {
                    richPresence.setLargeImage("plot", Flint.getUser().getPlot().getName().getString());
                }

                if (!Objects.equals(PlotRPC.state, "") && PlotRPC.active) {
                    richPresence.setState(PlotRPC.state);
                } else {
                    richPresence.setState(null);
                }

                // the worst line of java ever made.
                boolean isModeHidden = DiamondFireRPC.CONFIG.SHOW_MODE() == ConfigModel.ModeHiding.SEMI_HIDE || DiamondFireRPC.CONFIG.SHOW_MODE() == ConfigModel.ModeHiding.FULL_HIDE;
                if (!isModeHidden || !Objects.equals(PlotRPC.smallImage, "")) {
                    if (!Objects.equals(PlotRPC.smallImage, "") && DiamondFireRPC.CONFIG.ALLOW_PLOT_CONTROL() && PlotRPC.active) {
                        richPresence.setSmallImage(PlotRPC.smallImage, PlotRPC.smallImageText);
                    } else {
                        richPresence.setSmallImage(Flint.getUser().getMode().getName().toLowerCase(), Flint.getUser().getMode().getName());
                    }
                }
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

