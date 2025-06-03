package net.redpix.ecosystem.util.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import net.redpix.ecosystem.Ecosystem;

public class AirdropSettingsConfig {
    private final File file;
    
    public AirdropSettingsConfig(Ecosystem plugin) {
        this.file = new File(plugin.getDataFolder(), "airdrop-config.yml");
    }

    public YamlConfiguration getConfig() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config;
    }
}
