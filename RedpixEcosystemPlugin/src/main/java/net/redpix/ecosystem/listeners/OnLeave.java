package net.redpix.ecosystem.listeners;

import java.util.Date;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.redpix.ecosystem.Ecosystem;

public class OnLeave implements Listener
{
    private final Ecosystem plugin;

    public OnLeave(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (e.getPlayer().isBanned()) {
            if (plugin.getPlayerCheck().contains(e.getPlayer())) {
                plugin.getPlayerCheck().remove(e.getPlayer());
            }

            if (plugin.getPlayersInCombat().containsKey(e.getPlayer())) {
                plugin.getPlayersInCombat().remove(e.getPlayer());
            }

            return;
        }

        if (plugin.getPlayersInCombat().containsKey(e.getPlayer())) {
            e.getPlayer().setHealth(0);

            plugin.getServer().broadcast(
                Component.text((e.getPlayer().getName() + " ɪѕᴛ ɪᴍ ᴋᴀᴍᴘꜰ ᴠᴇʀʟᴀѕѕᴇɴ!"), NamedTextColor.GREEN)
            );
        }

        if (plugin.getPlayerCheck().contains(e.getPlayer())) {
            e.getPlayer().ban("Left during check", (Date) null, "");
            plugin.getPlayerCheck().remove(e.getPlayer());

            if (plugin.getPlayersInCombat().containsKey(e.getPlayer())) {
                plugin.getPlayersInCombat().remove(e.getPlayer());
            }
        }
    }
}
