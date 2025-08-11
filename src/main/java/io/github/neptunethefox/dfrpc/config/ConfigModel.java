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

}
