package net.redpix.ecosystem.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.redpix.ecosystem.Ecosystem;

public class CancelCommand implements Listener
{
    private final Ecosystem plugin;

    public CancelCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (!e.getMessage().startsWith("/spawn")) {
            return;
        }

        if (plugin.getPlayersInCombat().containsKey(e.getPlayer())) {
            e.getPlayer().sendMessage(Component.text("ᴅᴜ ᴋᴀɴɴѕᴛ ᴋᴇɪɴᴇ ᴄᴏᴍᴍᴀɴᴅѕ ᴡäʜʀᴇɴᴅ ᴅᴇѕ ᴋᴀᴍᴘꜰᴇѕ ʙᴇɴᴜᴛᴢᴛᴇɴ!", NamedTextColor.RED));
            e.setCancelled(true);
        }
    }
}
