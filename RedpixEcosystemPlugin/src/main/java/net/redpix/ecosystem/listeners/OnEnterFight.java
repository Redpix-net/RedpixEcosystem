package net.redpix.ecosystem.listeners;

import java.time.Instant;
import java.util.HashMap;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
        if (e.getEntityType() != EntityType.PLAYER || e.getDamager().getType() != EntityType.PLAYER) {
            return;
        }

        Player player_target = (Player) e.getEntity();
        Player player_attacker = (Player) e.getDamager();

        if (player_attacker == player_target) {
            return;
        }

        HashMap<Player, Instant> players = plugin.getPlayersInCombat();

        if (players.containsKey(player_target) || players.containsKey(player_attacker) ) {
            players.replace(player_target, Instant.now().plusSeconds(30));
            players.replace(player_attacker, Instant.now().plusSeconds(30));
            return;
        }

        players.put(player_target, Instant.now().plusSeconds(30));
        players.put(player_attacker, Instant.now().plusSeconds(30));

        plugin.getCombatTimer().startTimer(player_target);
        plugin.getCombatTimer().startTimer(player_attacker);
        
        Component message = Component.text(
            "ᴅᴜ ʙɪѕᴛ ɪᴍ ᴋᴀᴍᴘꜰ, ᴠᴇʀʟᴀѕѕᴇ ɴɪᴄʜᴛ ᴅᴇɴ ѕᴇʀᴠᴇʀ!",
            NamedTextColor.RED
        );

        player_target.sendMessage(message);
        player_attacker.sendMessage(message);
    }
}
