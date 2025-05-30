package net.redpix.ecosystem.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import net.redpix.ecosystem.Ecosystem;
import net.redpix.ecosystem.util.AirdropInventory;

public class AirdropListener implements Listener
{
    private final Ecosystem plugin;

    public AirdropListener(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();

        if (inv == null || !(inv.getHolder(false) instanceof AirdropInventory)) {
            return;
        }

        plugin.getConfigManager().getAirdropConfig().saveAirdrop(inv.getContents(), "test-1");

        inv.clear();
    }
}
