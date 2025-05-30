package net.redpix.ecosystem.util;

import org.bukkit.entity.Player;

import net.redpix.ecosystem.Ecosystem;

public class AirdropManager {  
    private final AirdropInventory inv;

    public AirdropManager(Ecosystem plugin) {
        this.inv = new AirdropInventory(plugin);
    }

    public void openMenu(Player player) {
        player.openInventory(inv.getInventory());
    }
}
