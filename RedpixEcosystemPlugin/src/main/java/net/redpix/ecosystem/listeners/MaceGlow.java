package net.redpix.ecosystem.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import net.redpix.ecosystem.Ecosystem;

public class MaceGlow implements Listener
{
    private final Ecosystem plugin;

    public MaceGlow(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChestItemTake(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }

        if (e.isShiftClick() && e.getClickedInventory() == e.getView().getBottomInventory() && e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.MACE) {
            e.setCancelled(true);
        }

        if (
            e.getClickedInventory().getType() != InventoryType.CHEST && 
            e.getClickedInventory().getType() != InventoryType.ENDER_CHEST &&
            e.getClickedInventory().getType() != InventoryType.HOPPER &&
            e.getClickedInventory().getType() != InventoryType.SHULKER_BOX
        ) {
            return;
        }

        if (e.getClickedInventory() == e.getView().getTopInventory() && e.getCursor() != null && e.getCursor().getType() == Material.MACE) {
            e.setCancelled(true);
        }

        if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.MACE) {
            e.getWhoClicked().setGlowing(true);
            return;
        }
    }
}
