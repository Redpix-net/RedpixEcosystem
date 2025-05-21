package net.redpix.ecosystem.util;

import org.bukkit.entity.Player;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Color;
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
        BossBar bar = BossBar.bossBar(
            Component.text(Color.RED + "ᴘᴠᴘ ᴇɴᴅᴇᴛ ɪɴ: " + plugin.getPlayersInCombat().get(player)), 
            1, 
            BossBar.Color.RED, 
            BossBar.Overlay.PROGRESS
        );
        
        player.getScheduler().runAtFixedRate(plugin, scheduledTask -> {
            player.hideBossBar(bar);

            if (!plugin.getPlayersInCombat().containsKey(player)) {
                scheduledTask.cancel();
                return;
            }

            if (plugin.getPlayersInCombat().get(player) < 0) {
                plugin.getPlayersInCombat().remove(player);
                
                player.showTitle(Title.title(Component.text("Der Kampf ist vorbei!"), Component.text("")));
                scheduledTask.cancel();
                return;
            }

            bar.name(Component.text("ᴘᴠᴘ ᴇɴᴅᴇᴛ ɪɴ: " + plugin.getPlayersInCombat().get(player)));

            player.showBossBar(bar);

            plugin.getPlayersInCombat().replace(player, plugin.getPlayersInCombat().get(player) - 1);
        }, null,(long) 1, (long) 1);
    }
}
