package net.redpix.ecosystem.listeners;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.redpix.ecosystem.Ecosystem;

public class SupportInvListener implements Listener
{
    private final Ecosystem plugin;

    public SupportInvListener(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSupInvOpen(InventoryClickEvent e) {
        if (!e.getInventory().equals(plugin.getSupInv())) {
            return;
        }

        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }
    
        // FIX THIS SHIT 
        if (e.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS_PANE) {
            plugin.getSupportTickets().put((Player) e.getWhoClicked(), "");
        }

        if (e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
            plugin.getSupportTickets().remove((Player) e.getWhoClicked());
        }

        if (e.getCurrentItem().getType() == Material.PAPER) {
            e.getWhoClicked().closeInventory();
            plugin.getTicketInv().clear();
            int i = 0;
            ItemStack ticket = new ItemStack(Material.PAPER);

            for (Map.Entry<Player, String> set: plugin.getSupportTickets().entrySet()) {
                if (!plugin.getSupportTickets().containsKey(set.getKey())) {
                    continue;
                }

                ItemMeta tMeta = ticket.getItemMeta();
                tMeta.displayName(set.getKey().displayName());
                ticket.setItemMeta(tMeta);
                plugin.getTicketInv().setItem(i, ticket);
                i++;
            }

            e.getWhoClicked().openInventory(plugin.getTicketInv());
        }
    }

    @EventHandler
    public void onTicketInvOpen(InventoryClickEvent e) {
        if (!e.getInventory().equals(plugin.getTicketInv())) {
            return;
        }

        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        ItemMeta meta = e.getCurrentItem().getItemMeta();
        String name = meta.getDisplayName();

        Player player = (Player) e.getWhoClicked();

        if (name == null) {
            return;
        }

        Player target = Bukkit.getPlayerExact(name);

        if (target == null) {
            return;
        }

        if (!plugin.getSupportTickets().containsKey(target)) {
            plugin.getTicketInv().setItem(e.getSlot(), null);
            return;
        }

        player.teleportAsync(target.getLocation());
    }
}
