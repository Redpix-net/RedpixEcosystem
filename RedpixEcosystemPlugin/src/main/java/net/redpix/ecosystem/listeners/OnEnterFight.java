package net.redpix.ecosystem.listeners;

import java.time.Instant;

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
        if (e.getEntityType() != EntityType.PLAYER || e.getDamager().getType() != EntityType.PLAYER) {
            return;
        }
        
        Player player_target = (Player) e.getEntity();
        Player player_attacker = (Player) e.getDamager();

        if (plugin.getPlayersInCombat().containsKey(player_target)) {
            plugin.getPlayersInCombat().replace(player_target, Instant.now().plusSeconds(30));
            plugin.getPlayersInCombat().replace(player_attacker, Instant.now().plusSeconds(30));
            return;
        }

        plugin.getPlayersInCombat().put(player_target, Instant.now().plusSeconds(30));
        plugin.getPlayersInCombat().put(player_attacker, Instant.now().plusSeconds(30));
        plugin.getCombatTimer().startTimer(player_target);
    }
}
