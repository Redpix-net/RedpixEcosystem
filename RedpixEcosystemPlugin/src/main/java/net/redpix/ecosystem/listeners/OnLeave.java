package net.redpix.ecosystem.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.redpix.ecosystem.Ecosystem;

public class OnLeave implements Listener
{
    private final Ecosystem plugin;

    public OnLeave(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (plugin.getPlayersInCombat().containsKey(e.getPlayer())) {
            e.getPlayer().setHealth(0);
        }
    }
}
