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

    // This shouldn't be seen normally but eh why not!
    public String NOT_ON_DIAMONDFIRE_MESSAGE = "Not on DiamondFire";

    public boolean SHOW_MODE = true;



}
