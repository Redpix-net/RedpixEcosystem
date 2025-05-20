package net.redpix.ecosystem.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.redpix.ecosystem.Ecosystem;
import net.redpix.ecosystem.util.CombatTimer;

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
        
        if (plugin.getPlayersInCombat().containsKey(e.getEntity())) {
            return;
        }

        new CombatTimer(plugin).startTimer((Player) e.getEntity());
    }
}
