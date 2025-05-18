package net.redpix.ecosystem.util.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;

public class ConfigManager {
    private HashMap<Class, String> configs = new HashMap<Class, String>();
    private List<File> config_files = new ArrayList<>();

    private final Plugin plugin;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
    }
    
    public void register_configs() {
        configs.put(StrikeLightingConfig.class, "strike_lighting.yml");
    }

    public void init() {
        register_configs();

        for (Map.Entry<Class, String> set : configs.entrySet()) {
            config_files.add(new File(plugin.getDataFolder(), set.getValue()));
            ConfigurationSerialization.registerClass(set.getKey());
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
}
