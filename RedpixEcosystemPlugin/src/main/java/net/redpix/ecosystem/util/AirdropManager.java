package net.redpix.ecosystem.util;

import org.bukkit.entity.Player;

import net.redpix.ecosystem.Ecosystem;

public class AirdropManager {  
    private final AirdropInventory inv;
    private final Ecosystem plugin;

    public AirdropManager(Ecosystem plugin) {
        this.inv = new AirdropInventory(plugin);
        this.plugin = plugin;
    }

    public void openMenu(Player player) {
        player.openInventory(inv.getInventory());
    }

    public AirdropInventory getInventory() {
        return inv;
    }

    public void saveToAirdrop() {
        plugin.getConfigManager().getAirdropConfig().saveAirdrop(inv.getInventory().getContents(), inv.getName());
    }
}
