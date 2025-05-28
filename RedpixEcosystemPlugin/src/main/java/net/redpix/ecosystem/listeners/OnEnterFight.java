package net.redpix.ecosystem.listeners;

import java.time.Instant;
import java.util.HashMap;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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

        Player player_attacker;
        Player player_target = (Player) e.getEntity();

        if (plugin.getPlayerCheck().contains(player_target)) {
            e.setDamage(0);
            return;
        }

        if (e.getDamageSource().getDirectEntity() instanceof Player) {
            player_attacker = (Player) e.getDamageSource().getDirectEntity();
        } else if (e.getDamageSource().getCausingEntity() instanceof Player) {
            player_attacker = (Player) e.getDamageSource().getCausingEntity();
        } else {
            return;
        }

        if (player_attacker == player_target) {
            return;
        }

        HashMap<Player, Instant> players = plugin.getPlayersInCombat();

        Component message = Component.text(
            "ᴅᴜ ʙɪѕᴛ ɪᴍ ᴋᴀᴍᴘꜰ, ᴠᴇʀʟᴀѕѕᴇ ɴɪᴄʜᴛ ᴅᴇɴ ѕᴇʀᴠᴇʀ!",
            NamedTextColor.RED
        );
        
        if (players.containsKey(player_target) && !(players.containsKey(player_attacker))) {
            // only Attacker isnt in combat

            players.put(player_attacker, Instant.now().plusSeconds(60));
            players.replace(player_target, Instant.now().plusSeconds(60));
            plugin.getCombatTimer().startTimer(player_attacker);
            player_attacker.sendMessage(message);

            return;
        } else if (players.containsKey(player_attacker) && !(players.containsKey(player_target))) { 
            // only Target isnt in combat
            
            players.put(player_target, Instant.now().plusSeconds(60));
            players.replace(player_attacker, Instant.now().plusSeconds(60));
            plugin.getCombatTimer().startTimer(player_target);
            player_target.sendMessage(message);

            return;
        } 

        if (players.containsKey(player_target) && players.containsKey(player_attacker)) {
            players.replace(player_attacker, Instant.now().plusSeconds(60));
            players.replace(player_target, Instant.now().plusSeconds(60));

            return;
        }

        // Both arent in a pair
        players.put(player_target, Instant.now().plusSeconds(60));
        players.put(player_attacker, Instant.now().plusSeconds(60));

        plugin.getCombatTimer().startTimer(player_target);
        plugin.getCombatTimer().startTimer(player_attacker);

        player_target.sendMessage(message);
        player_attacker.sendMessage(message);
    }
}
