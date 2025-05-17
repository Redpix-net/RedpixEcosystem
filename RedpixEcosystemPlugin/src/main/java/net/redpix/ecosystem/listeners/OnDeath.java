package net.redpix.ecosystem.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

public class OnDeath implements Listener
{
    private final Plugin plugin;

    public OnDeath(Plugin plugin) {
        this.plugin = plugin;
    }

    // TODO! put the entity in a seperate variable for readablilite stuff lol
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntityType() != EntityType.PLAYER) {
            return;
        }

        Location loc = e.getEntity().getLocation();
        
        e.getEntity().getScheduler().run(plugin, task -> {
            // can just use the entity cause we already checked if it is a player or not :P
            Entity player = e.getEntity();
            player.getWorld().strikeLightning(loc);
        }, null);
    }
}
