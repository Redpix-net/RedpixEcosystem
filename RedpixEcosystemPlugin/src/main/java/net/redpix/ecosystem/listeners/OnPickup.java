package net.redpix.ecosystem.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class OnPickup implements Listener {
    private final Plugin plugin;

    public OnPickup(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnEntityPickup(PlayerPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();

        if (item.getType() != Material.MACE) {
            return;
        }

        e.getPlayer().getScheduler().run(plugin, task -> {
            e.getPlayer().setGlowing(true);
        }, null);
    }
}
