package net.redpix.ecosystem.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.redpix.ecosystem.Ecosystem;

public class OnEnterFight implements Listener
{
    private final Ecosystem plugin;

    public OnEnterFight(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntityType() != EntityType.PLAYER) {
            return;
        }
        
        Player player = (Player) e.getEntity();

        if (plugin.getPlayersInCombat().containsKey(player)) {
            return;
        }

        plugin.getPlayersInCombat().put(player, 30);
        plugin.getCombatTimer().startTimer(player);
    }
}
