package net.redpix.ecosystem.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class OnDrop implements Listener {
    private final Plugin plugin;

    public OnDrop(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnEntityDrop(PlayerDropItemEvent e) {
        ItemStack item = e.getItemDrop().getItemStack();

        if (item.getType() != Material.MACE) {
            return;
        }
        e.getPlayer().getScheduler().run(plugin, task -> {
            e.getPlayer().setGlowing(false);
        }, null);
    }
}
