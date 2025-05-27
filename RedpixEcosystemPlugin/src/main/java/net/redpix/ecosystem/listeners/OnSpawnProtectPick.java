package net.redpix.ecosystem.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import net.redpix.ecosystem.Ecosystem;

public class OnSpawnProtectPick implements Listener
{
    private final Ecosystem plugin;

    public OnSpawnProtectPick(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand() == null) return;

        ItemMeta holding = e.getPlayer().getInventory().getItemInMainHand().getItemMeta();

        if (holding == null) return;

        if (!(holding.getPersistentDataContainer().has(plugin.getZonePickKey(), PersistentDataType.STRING))) return;

        if (e.getClickedBlock() == null) return;

        if (e.getAction().isLeftClick()) {
            Location loc = e.getClickedBlock().getLocation();
            
            plugin.getConfig().set("loc_1_spawn_protect", loc);

            e.getPlayer().sendMessage("Pos 1 set");
        }

        if (e.getAction().isRightClick()) {
            Location loc = e.getClickedBlock().getLocation();

            plugin.getConfig().set("loc_2_spawn_protect", loc);

            e.getPlayer().sendMessage("Pos 2 set");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand() == null) return;

        ItemMeta holding = e.getPlayer().getInventory().getItemInMainHand().getItemMeta();

        if (holding == null) return;

        if (!(holding.getPersistentDataContainer().has(plugin.getZonePickKey(), PersistentDataType.STRING))) return;

        e.setCancelled(true);
    }
}
