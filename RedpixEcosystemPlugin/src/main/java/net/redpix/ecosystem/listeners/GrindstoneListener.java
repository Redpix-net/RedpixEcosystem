package net.redpix.ecosystem.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerExpChangeEvent;

import net.redpix.ecosystem.Ecosystem;

public class GrindstoneListener implements Listener
{
    private final Ecosystem plugin;

    public GrindstoneListener(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGrindstoneXP(PlayerExpChangeEvent e) {
        Player p = e.getPlayer();

        if (p.getOpenInventory().getTopInventory().getType() == InventoryType.GRINDSTONE) {
            e.setAmount(0);
        }
    }
}
