package io.github.neptunethefox.dfrpc.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.RegexConstraint;
import io.wispforest.owo.config.annotation.RestartRequired;
import io.wispforest.owo.config.annotation.SectionHeader;

@Config(name = "dfrpc-config", wrapperName = "DFRPCConfig")
public class ConfigModel {

    @RestartRequired
    @SectionHeader("Mod")
    public static boolean enabled = true;

    @SectionHeader("Rich Presence")
    public static String IN_SPAWN_MESSAGE = "Vibing in %s spawn";

    // This shouldn't be seen normally but eh why not!
    public static String NOT_ON_DIAMONDFIRE_MESSAGE = "Not on DiamondFire";



}
