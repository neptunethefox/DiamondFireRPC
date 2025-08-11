package io.github.neptunethefox.dfrpc.discord;

import dev.dfonline.flint.Flint;
import dev.dfonline.flint.feature.trait.TickedFeature;
import dev.dfonline.flint.hypercube.Plot;
import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;

import java.util.Objects;

public class DiscordRPC implements TickedFeature {
    public static final RichPresence richPresence = new RichPresence();
    private long clientID;
    private int updateTick = 0;

    public DiscordRPC(long clientID) {
        richPresence.setLargeImage("diamondfire", "Starting/Not on DF");
        this.clientID = clientID;
        DiscordIPC.start(clientID, null);
    }


    @Override
    public void tick() {
        updateTick++;
        if (updateTick == 24) {
            updateTick = 0;
            if (Flint.getUser().getNode() == null) {
                richPresence.setLargeImage("diamondfire", "DiamondFire");
                richPresence.setDetails("Not on DiamondFire");

                DiscordIPC.stop();
                return;
            }

            if (!DiscordIPC.isConnected()) {
                DiscordIPC.start(this.clientID, null);
            }

            richPresence.setLargeImage("diamondfire", "DiamondFire");

            if (Flint.getUser().getPlot() == null) {
                richPresence.setDetails("Vibing in %s spawn".formatted(Flint.getUser().getNode().getName()));
                richPresence.setSmallImage(null, null);
            } else {
                Plot currentPlot = Flint.getUser().getPlot();
                richPresence.setLargeImage("plot", Flint.getUser().getPlot().getName().getString());

                if (!Objects.equals(Flint.getUser().getMode().getName(), "Dev"))
                    richPresence.setDetails(Flint.getUser().getMode().getName()+"ing on "+currentPlot.getName().getString());
                else
                    richPresence.setDetails("Coding on "+currentPlot.getName().getString());

                richPresence.setSmallImage(Flint.getUser().getMode().getName().toLowerCase(), Flint.getUser().getMode().getName());
            }
            DiscordIPC.setActivity(richPresence);
        }
    }
}

