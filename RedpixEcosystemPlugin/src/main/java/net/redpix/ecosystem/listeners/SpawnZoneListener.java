package net.redpix.ecosystem.listeners;

import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import net.redpix.ecosystem.Ecosystem;

public class SpawnZoneListener implements Listener
{
    private final Ecosystem plugin;

    public SpawnZoneListener(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        Location loc = e.getLocation();
        int x = loc.getBlockX();
        int z = loc.getBlockZ();

        Configuration config = plugin.getConfig();
        Location loc_1 = config.getLocation("loc_1_spawn_protect");
        Location loc_2 = config.getLocation("loc_2_spawn_protect");
        
        if (loc_1 == null || loc_2 == null) {
            return;
        }

        int x1 = loc_1.getBlockX();
        int x2 = loc_2.getBlockX();
        int z1 = loc_1.getBlockZ();
        int z2 = loc_2.getBlockZ();

        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) &&
        z >= Math.min(z1, z2) && z <= Math.max(z1, z2)) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        Location loc = e.getEntity().getLocation();
        int x = loc.getBlockX();
        int z = loc.getBlockZ();

        Configuration config = plugin.getConfig();
        Location loc_1 = config.getLocation("loc_1_spawn_protect");
        Location loc_2 = config.getLocation("loc_2_spawn_protect");
        
        if (loc_1 == null || loc_2 == null) {
            return;
        }

        int x1 = loc_1.getBlockX();
        int x2 = loc_2.getBlockX();
        int z1 = loc_1.getBlockZ();
        int z2 = loc_2.getBlockZ();

        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) &&
        z >= Math.min(z1, z2) && z <= Math.max(z1, z2)) {
            e.setCancelled(true);
        }
    }
}
