package io.github.neptunethefox.dfrpc.plot;

import dev.dfonline.flint.Flint;
import dev.dfonline.flint.feature.trait.PacketListeningFeature;
import dev.dfonline.flint.util.Logger;
import dev.dfonline.flint.util.result.EventResult;
import io.github.neptunethefox.dfrpc.DiamondFireRPC;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

import java.util.ArrayList;
import java.util.Objects;

public class PlotRPC implements PacketListeningFeature {

    public static boolean active = false;
    public static String details = "";
    public static String state = "";

    public static String largeImage = "plot";
    public static String largeImageText = "";

    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.of(PlotRPC.class);

    public void reset() {
        largeImage = "plot";
        largeImageText = "";
        details = "";
        state = "";
        active = false;
    }

    @Override
    public boolean isEnabled() {
        return DiamondFireRPC.CONFIG.ALLOW_PLOT_CONTROL();
    }

    @Override
    public EventResult onReceivePacket(Packet<?> packet) {
        if (Flint.getUser().getPlot() == null) {
            reset();
            return EventResult.PASS;
        }
        if (!DiamondFireRPC.CONFIG.ALLOW_PLOT_CONTROL()) return EventResult.PASS;

        if (packet instanceof OverlayMessageS2CPacket(Text text)) {
            if (Text.empty().contains(text)) {
                return EventResult.PASS;
            }

            var siblings = text.getSiblings();
            if (siblings.isEmpty()) {
                return EventResult.PASS;
            }

            var command = siblings.getFirst();
            if (!Objects.equals(command.getStyle().getColor(), TextColor.fromRgb(0xFF9D14))) {
                return EventResult.PASS;
            }

            if (command.getContent().equals(new PlainTextContent.Literal("activate"))) {
                active = true;
                return EventResult.CANCEL;
            }

            if (command.getContent().equals(new PlainTextContent.Literal("deactivate"))) {
                reset();
                return EventResult.CANCEL;
            }

            if (command.getContent().equals(new PlainTextContent.Literal("details"))) {
                boolean first = true;
                var textBuilder = new ArrayList<String>();

                for (var part : siblings) {
                    if (first) {
                        first = false;
                        continue;
                    }

                    textBuilder.add(part.getString());
                }
                details = String.join(" ", textBuilder).strip();
                return EventResult.CANCEL;
            }

            if (command.getContent().equals(new PlainTextContent.Literal("state"))) {
                boolean first = true;
                var textBuilder = new ArrayList<String>();

                for (var part : siblings) {
                    if (first) {
                        first = false;
                        continue;
                    }

                    textBuilder.add(part.getString());
                }
                state = String.join(" ", textBuilder).strip();
                return EventResult.CANCEL;
            }

            if (command.getContent().equals(new PlainTextContent.Literal("largeImage"))) {
                boolean first = true;
                var textBuilder = new ArrayList<String>();

                for (var part : siblings) {
                    if (first) {
                        first = false;
                        continue;
                    }

                    textBuilder.add(part.getString());
                }

                largeImage = String.join("", textBuilder).strip();
                return EventResult.CANCEL;
            }

            if (command.getContent().equals(new PlainTextContent.Literal("largeImageText"))) {
                boolean first = true;
                var textBuilder = new ArrayList<String>();

                for (var part : siblings) {
                    if (first) {
                        first = false;
                        continue;
                    }

                    textBuilder.add(part.getString());
                }

                largeImageText = String.join(" ", textBuilder).strip();
                return EventResult.CANCEL;
            }

            return EventResult.PASS;
        }

        return EventResult.PASS;
    }
}
