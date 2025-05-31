package net.redpix.ecosystem.util.config;

import java.io.File;
import java.time.Instant;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.redpix.ecosystem.Ecosystem;

public class MutedPlayersConfig {
    private final File file;
    private final Ecosystem plugin;
    
    public MutedPlayersConfig(Ecosystem plugin) {
        this.file = new File(plugin.getDataFolder(), "muted-player.yml");
        this.plugin = plugin;
    }

    public void addPlayer(Player player, Instant time) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(String.format("players.%s", player.getUniqueId()), time.toString());
    
        try {
            config.save(file);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void removePlayer(Player player) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(String.format("players.%s", player.getUniqueId()), null);

        try {
            config.save(file);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public HashMap<Player, Instant> getPlayers() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        HashMap<Player, Instant> muted_players = new HashMap<>();

        if (config.getConfigurationSection("players") == null) {
            return muted_players;
        }

        for (String key : config.getConfigurationSection("players").getKeys(false)) {
            Player p =  Bukkit.getPlayer(key);
            Instant time = Instant.parse(config.getString(String.format("players.%s", key)));

            if (p == null || time == null) continue;

            muted_players.put(p, time);
        }

        return muted_players;
    }
}
