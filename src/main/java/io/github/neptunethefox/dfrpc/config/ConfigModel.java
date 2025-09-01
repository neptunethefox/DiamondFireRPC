package io.github.neptunethefox.dfrpc.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RestartRequired;
import io.wispforest.owo.config.annotation.SectionHeader;



@Modmenu(modId = "dfrpc")
@Config(name = "dfrpc-config", wrapperName = "DFRPCConfig")
public class ConfigModel {

    @RestartRequired
    @SectionHeader("Mod")
    public boolean ENABLED = true;

    @SectionHeader("Rich Presence")
    public String IN_SPAWN_MESSAGE = "Vibing in %s spawn";

    public ModeHiding SHOW_MODE = ModeHiding.SHOW;

    public enum ModeHiding {
        SHOW,
        SEMI_HIDE,
        FULL_HIDE
    }
}

