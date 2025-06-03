package net.redpix.ecosystem.util.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import net.redpix.ecosystem.Ecosystem;

public class ConfigManager {
    private HashMap<Class, String> configs = new HashMap<Class, String>();
    private List<File> config_files = new ArrayList<>();

    private final AirdropConfig airdropConfig;
    private final TempBanMuteConfig tempBanMuteConfig;
    private final MutedPlayersConfig mutedPlayersConfig;
    private final AirdropSettingsConfig airdropSettingsConfig;

    private final Ecosystem plugin;

    public ConfigManager(Ecosystem plugin) {
        this.plugin = plugin;
        this.airdropConfig = new AirdropConfig(plugin);
        this.tempBanMuteConfig = new TempBanMuteConfig(plugin);
        this.mutedPlayersConfig = new MutedPlayersConfig(plugin);
        this.airdropSettingsConfig = new AirdropSettingsConfig(plugin);
    }
    
    public void register_configs() {
        configs.put(StrikeLightingConfig.class, "strike_lighting.yml");
        configs.put(AirdropOption.class, "airdrop.yml");
        configs.put(TempBanMuteOption.class, "tempban-mute.yml");
        configs.put(MutedPlayersOption.class, "muted-player.yml");
        configs.put(AirdropSettingsOption.class, "airdrop-config.yml");
    }

    public void init() {
        register_configs();

        for (Map.Entry<Class, String> set : configs.entrySet()) {
            config_files.add(new File(plugin.getDataFolder(), set.getValue()));
            ConfigurationSerialization.registerClass(set.getKey());
            plugin.saveResource(set.getValue(), false);
        }
    }

    public void save_configs() {
        for (File config_file : config_files) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(config_file);
            try {
                config.save(config_file);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public AirdropConfig getAirdropConfig() {
        return airdropConfig;
    }

    public TempBanMuteConfig getTempBanMuteConfig() {
        return tempBanMuteConfig;
    }

    public MutedPlayersConfig getMutedPlayersConfig() {
        return mutedPlayersConfig;
    }

    public AirdropSettingsConfig getAirdropSettingsConfig() {
        return airdropSettingsConfig;
    }
}
