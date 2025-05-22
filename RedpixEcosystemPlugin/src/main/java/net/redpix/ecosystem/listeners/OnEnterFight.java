package net.redpix.ecosystem.listeners;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
        if (e.getEntityType() != EntityType.PLAYER || e.getDamager().getType() != EntityType.PLAYER) {
            return;
        }

        Player player_target = (Player) e.getEntity();
        Player player_attacker = (Player) e.getDamager();

        if (player_attacker == player_target) {
            return;
        }

        HashMap<Player, Instant> players = plugin.getPlayersInCombat();

        Component message = Component.text(
            "ᴅᴜ ʙɪѕᴛ ɪᴍ ᴋᴀᴍᴘꜰ, ᴠᴇʀʟᴀѕѕᴇ ɴɪᴄʜᴛ ᴅᴇɴ ѕᴇʀᴠᴇʀ!",
            NamedTextColor.RED
        );

        // Player Target is Already in a pair And attacker already is inside
        if (
            plugin.getCombatPairs().containsKey(player_target) && plugin.getCombatPairs().get(player_target).contains(player_attacker)
        ) {
            for (Player p : plugin.getCombatPairs().get(player_target)) {
                players.replace(p, Instant.now().plusSeconds(60));
            }

            return;
        } 

        // Player Attacker is Already in a pair And Target already is inside
        if (
            plugin.getCombatPairs().containsKey(player_attacker) && plugin.getCombatPairs().get(player_attacker).contains(player_target)
        ) {
            for (Player p : plugin.getCombatPairs().get(player_target)) {
                players.replace(p, Instant.now().plusSeconds(60));
            }

            return;
        } 

        // Attacker is inside pair but target isnt yet
        if (
            plugin.getCombatPairs().containsKey(player_attacker) && !(plugin.getCombatPairs().get(player_attacker).contains(player_target))
        ) {
            for (Player p : plugin.getCombatPairs().get(player_target)) {
                players.replace(p, Instant.now().plusSeconds(60));
            }

            players.put(player_target, Instant.now().plusSeconds(60));
            plugin.getCombatPairs().get(player_attacker).add(player_target);
            plugin.getCombatTimer().startTimer(player_target);

            player_target.sendMessage(message);

            return;
        } 

        // Target is inside pair but attacker isnt yet
        if (
            plugin.getCombatPairs().containsKey(player_target) && !(plugin.getCombatPairs().get(player_target).contains(player_attacker))
        ) {
            for (Player p : plugin.getCombatPairs().get(player_attacker)) {
                players.replace(p, Instant.now().plusSeconds(60));
            }

            players.put(player_attacker, Instant.now().plusSeconds(60));
            plugin.getCombatPairs().get(player_target).add(player_attacker);
            plugin.getCombatTimer().startTimer(player_attacker);

            player_attacker.sendMessage(message);

            return;
        } 

        // Both arent in a pair
        players.put(player_target, Instant.now().plusSeconds(60));
        players.put(player_attacker, Instant.now().plusSeconds(60));

        plugin.getCombatTimer().startTimer(player_target);
        plugin.getCombatTimer().startTimer(player_attacker);
        
        // TODO! Fix me before release IMPORTANT
        Set<Player> set = new HashSet<Player>();
        set.add(player_attacker);

        plugin.getCombatPairs().put(player_target, set);

        player_target.sendMessage(message);
        player_attacker.sendMessage(message);
    }
}
