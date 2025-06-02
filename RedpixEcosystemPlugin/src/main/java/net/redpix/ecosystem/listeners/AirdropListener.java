package net.redpix.ecosystem.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.redpix.ecosystem.Ecosystem;
import net.redpix.ecosystem.util.AirdropInventory;
import net.redpix.ecosystem.util.AirdropManager;
import net.redpix.ecosystem.util.AirdropInventory.InventoryState;

public class AirdropListener implements Listener
{
    private final Ecosystem plugin;

    public AirdropListener(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void airdropMenuClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player p = (Player) e.getWhoClicked();

        AirdropManager manager = plugin.getAirdropManager();

        if (!isAirdropInv(inv)) {
            return;
        }

        switch (manager.getInventory().getState()) {
            case SETTING_NAME:
                break;
            case SETTING_CONTENTS:
                break;
            case DEFAULT:
                e.setCancelled(true);
                break;
        }

        ItemStack item = e.getCurrentItem();

        if (item == null) {
            return;
        }

        switch (item.getType()) {
            case CHEST:
                inv.close();
                manager.getInventory().setState(InventoryState.SETTING_CONTENTS);
                manager.openMenu(p);
                break;
            case OAK_SIGN:
                inv.close();
                manager.getInventory().setState(InventoryState.SETTING_NAME);
                break;
            default:
                return;
        }
    }

    @EventHandler
    public void airdropMenuDrag(InventoryDragEvent e) {
        Inventory inv = e.getInventory();
        Player p = (Player) e.getWhoClicked();

        AirdropManager manager = plugin.getAirdropManager();

        if (!isAirdropInv(inv)) {
            return;
        }

        switch (manager.getInventory().getState()) {
            case SETTING_NAME:
                break;
            case SETTING_CONTENTS:
                break;
            case DEFAULT:
                e.setCancelled(true);
                break;
        }
    }

    public boolean isAirdropInv(Inventory inv) {
        if (inv == null || !(inv.getHolder(false) instanceof AirdropInventory)) {
            return false;
        }
        
        return true;
    }
}
