package net.redpix.ecosystem.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import net.redpix.ecosystem.Ecosystem;

public class OnDeath implements Listener
{
    private final Ecosystem plugin;

    public OnDeath(Ecosystem plugin) {
        this.plugin = plugin;
    }

    // TODO! put the entity in a seperate variable for readablilite stuff lol
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntityType() != EntityType.PLAYER) {
            return;
        }
        
        if (plugin.getPlayersInCombat().containsKey((Player) e.getEntity())) {
            plugin.getPlayersInCombat().remove((Player) e.getEntity());
        }

        e.getEntity().setGlowing(false);

        Location loc = e.getEntity().getLocation();
        
        e.getEntity().getScheduler().run(plugin, task -> {
            // can just use the entity cause we already checked if it is a player or not :P
            Entity player = e.getEntity();
            player.getWorld().strikeLightning(loc);
        }, null);
    }
}
