package net.redpix.ecosystem.util;

import java.time.Duration;
import java.time.Instant;

import org.bukkit.entity.Player;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.redpix.ecosystem.Ecosystem;

public class CombatTimer { 
    private final Ecosystem plugin;

    public CombatTimer(Ecosystem plugin) {
        this.plugin = plugin;
    }

    // TODO! Rewrite this whole thing!
    public void startTimer(Player player) {
        BossBar bar = BossBar.bossBar(
            Component.text("ᴘᴠᴘ ᴇɴᴅᴇᴛ ɪɴ: " + plugin.getPlayersInCombat().get(player), NamedTextColor.RED), 
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

            long timeLeft = Duration.between(
                Instant.now(), 
                plugin.getPlayersInCombat().get(player)
            ).getSeconds();

            if (timeLeft <= 0) {
                plugin.getPlayersInCombat().remove(player);
                
                player.sendMessage(Component.text("ᴅᴜ ʙɪѕᴛ ɴɪᴄʜᴛ ᴍᴇʜʀ ɪᴍ ᴋᴀᴍᴘꜰ, ᴅᴜ ᴋᴀɴɴѕᴛ ᴅᴇɴ ѕᴇʀᴠᴇʀ ᴠᴇʀʟᴀѕѕᴇɴ!", NamedTextColor.GREEN));
                scheduledTask.cancel();
                return;
            }

            bar.name(Component.text("ᴘᴠᴘ ᴇɴᴅᴇᴛ ɪɴ: " + timeLeft, NamedTextColor.RED));
            bar.progress(Math.max(0, timeLeft / 60.0f));

            player.showBossBar(bar);
        }, null, 1L, 20L);
    }
}
