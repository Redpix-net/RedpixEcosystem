package net.redpix.ecosystem.util;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.redpix.ecosystem.Ecosystem;

public class CombatTimer {
    private final Ecosystem plugin;

    public CombatTimer(Ecosystem plugin) {
        this.plugin = plugin;
    }

    // TODO! Rewrite this whole thing!
    public void startTimer(Player player) {
        plugin.getPlayersInCombat().put(player, 30);

        BossBar bar = BossBar.bossBar(Component.text("ᴘᴠᴘ ᴇɴᴅᴇᴛ ɪɴ: " + plugin.getPlayersInCombat().get(player)), 1, BossBar.Color.RED, BossBar.Overlay.PROGRESS);

        Bukkit.getAsyncScheduler().runAtFixedRate(plugin, task -> {
            if (player == null) {
                return;
            }

            if (!plugin.getPlayersInCombat().containsKey(player)) {
                player.hideBossBar(bar);
                return;
            }

            player.hideBossBar(bar);

            if (plugin.getPlayersInCombat().get(player) < 0) {
                plugin.getPlayersInCombat().remove(player);
                
                player.showTitle(Title.title(Component.text("Der Kampf ist vorbei!"), Component.text("")));

                return;
            }

            bar.name(Component.text("ᴘᴠᴘ ᴇɴᴅᴇᴛ ɪɴ: " + plugin.getPlayersInCombat().get(player)));

            player.showBossBar(bar);

            plugin.getPlayersInCombat().replace(player, plugin.getPlayersInCombat().get(player) - 1);
        }, 0, 1, TimeUnit.SECONDS);
    }
}
