package net.redpix.ecosystem.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.redpix.ecosystem.Ecosystem;

public class OnPlace implements Listener
{
    private final Ecosystem plugin;

    public OnPlace(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getItemInHand().getType() == Material.CRAFTER) {
            e.setCancelled(true);
        }

        if (e.getItemInHand().getType() == Material.RESPAWN_ANCHOR) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getMaterial() == Material.END_CRYSTAL) {
            e.setCancelled(true);
        }
    }
}
