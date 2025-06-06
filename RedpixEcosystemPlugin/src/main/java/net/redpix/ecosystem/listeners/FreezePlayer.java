package net.redpix.ecosystem.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.redpix.ecosystem.Ecosystem;

public class FreezePlayer implements Listener
{
    private final Ecosystem plugin;

    public FreezePlayer(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (plugin.getPlayerCheck().contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
