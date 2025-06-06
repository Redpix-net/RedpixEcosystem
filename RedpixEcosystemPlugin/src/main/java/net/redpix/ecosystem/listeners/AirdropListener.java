package net.redpix.ecosystem.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.redpix.ecosystem.Ecosystem;
import net.redpix.ecosystem.util.AirdropInventory;
import net.redpix.ecosystem.util.AirdropInventory.InventoryState;
import net.redpix.ecosystem.util.AirdropManager;

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

        ItemStack item = e.getCurrentItem();

        if (item == null) {
            return;
        }
        
        // this not only bad code but also not really expandable, pls stop... OR DONT I GUESS
        // dirty nesting .-.
        if (item.getPersistentDataContainer().has(plugin.getAirdropKey(), PersistentDataType.STRING)) {
            switch (item.getPersistentDataContainer().get(plugin.getAirdropKey(), PersistentDataType.STRING)) {
                case "save":
                    inv.close();
                    manager.saveToAirdrop();
                    manager.getInventory().reset();
                    break;
                case "name":
                    inv.close();
                    manager.getInventory().setState(InventoryState.SETTING_NAME);
                    break;
                case "content":
                    inv.close();
                    manager.getInventory().setState(InventoryState.SETTING_CONTENTS);
                    manager.openMenu(p);
                    break;
                case "back":
                    inv.close();
                    manager.getInventory().setState(InventoryState.DEFAULT);
                    manager.openMenu(p);
                    break;
                case "chance":
                    inv.close();
                    manager.getInventory().setState(InventoryState.SETTING_CHANCES);
                    manager.openMenu(p);
                    break;
                case "preview":
                    if (e.getClick().isLeftClick()) {
                        manager.getInventory().changeChance(e.getSlot(), 10);
                    } else if (e.getClick().isRightClick()) {
                        manager.getInventory().changeChance(e.getSlot(), -10);
                    } else if (e.getClick().isShiftClick()) {
                        // TODO!    
                    }
                    break;
                default:
                    break;
            }

            e.setCancelled(true);
        }

        if (manager.getInventory().getState() == InventoryState.SETTING_CONTENTS) {
            return;
        }

        e.setCancelled(true);
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
                return;
            case SETTING_CONTENTS:
                return;
            case DEFAULT:
                break;
            case SETTING_CHANCES:
                break;
        }

        e.setCancelled(true);
    }

    public boolean isAirdropInv(Inventory inv) {
        if (inv == null || !(inv.getHolder(false) instanceof AirdropInventory)) {
            return false;
        }
        
        return true;
    }

    @EventHandler
    public void playerChatEvent(AsyncChatEvent e) {
        Player p = e.getPlayer();

        AirdropManager manager = plugin.getAirdropManager();
        
        if (manager.getInventory().getState() != InventoryState.SETTING_NAME) {
            return;
        }

        String message = LegacyComponentSerializer.legacyAmpersand().serialize(e.message());

        manager.getInventory().setName(message);

        e.setCancelled(true);
    }
}
