package net.redpix.ecosystem.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.redpix.ecosystem.Ecosystem;

public class CancelCommand implements Listener
{
    private final Ecosystem plugin;

    public CancelCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (plugin.getPlayersInCombat().containsKey(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
