package net.redpix.ecosystem.listeners;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.redpix.ecosystem.Ecosystem;

public class MutedPlayersListener implements Listener
{
    private final Ecosystem plugin;

    public MutedPlayersListener(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChatMessage(AsyncChatEvent e) {
        if (!plugin.getMutedPlayers().containsKey(e.getPlayer())) {
            return;
        }

        Player p = e.getPlayer();
        HashMap<Player, Instant> mutedPlayers = plugin.getMutedPlayers();

        long time = Duration.between(Instant.now(), mutedPlayers.get(p)).getSeconds();

        if (time >= 0) {
            e.setCancelled(true);
            return;
        }

        mutedPlayers.remove(p);
    }
}
