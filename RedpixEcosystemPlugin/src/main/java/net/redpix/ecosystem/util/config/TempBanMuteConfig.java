package net.redpix.ecosystem.util.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.redpix.ecosystem.Ecosystem;

public class TempBanMuteConfig {
    private final File file;
    private final Ecosystem plugin;
    
    public TempBanMuteConfig(Ecosystem plugin) {
        this.file = new File(plugin.getDataFolder(), "tempban-mute.yml");
        this.plugin = plugin;
    }

    public Component getMessage(String message, Player player, String reason) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        MiniMessage miniMessage = plugin.getMiniMessage();
        String config_message = config.getString(String.format("tempban-mute.%s", message))
        .replace("%player%", player.getName())
        .replace("%reason%", reason);
        Component parse = miniMessage.deserialize(config_message);
        
        return parse;
    }
}
