package net.redpix.ecosystem.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.redpix.ecosystem.Ecosystem;

public class AirdropInventory implements InventoryHolder {  
    private final Inventory inventory;

    public AirdropInventory(Ecosystem plugin) {
        this.inventory = plugin.getServer().createInventory(this, 27);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
