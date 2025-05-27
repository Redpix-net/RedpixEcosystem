package net.redpix.ecosystem.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExpEvent;

import net.redpix.ecosystem.Ecosystem;

public class GrindstoneListener implements Listener
{
    private final Ecosystem plugin;

    public GrindstoneListener(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockXP(BlockExpEvent e) {
        if(e.getBlock().getType() == Material.GRINDSTONE) {
            e.setExpToDrop(0);
        }
    }
}
