package net.redpix.ecosystem.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class OnPickup implements Listener {
    private final Plugin plugin;

    public OnPickup(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnEntityPickup(EntityPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();

        if (item.getType() != Material.MACE && e.getEntity().getType() != EntityType.PLAYER) {
            return;
        }
        e.getEntity().getScheduler().run(plugin, task -> {
            e.getEntity().setGlowing(true);
        }, null);
    }
}
