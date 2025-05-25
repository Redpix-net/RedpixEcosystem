package net.redpix.ecosystem.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import net.redpix.ecosystem.Ecosystem;

public class OnCraft implements Listener
{
    private final Ecosystem plugin;

    public OnCraft(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent e) {
        ItemStack result = e.getInventory().getResult();

        if (result == null) {
            return;
        }

        // TODO! FUCKING HELL FIX THIS MESS!!
        if (result.getType() == Material.MACE) {
            e.setCancelled(true);
        }

        if (result.getType() == Material.CRAFTER) {
            e.setCancelled(true);
        }
    }
}
