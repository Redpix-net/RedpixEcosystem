package net.redpix.ecosystem.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.redpix.ecosystem.Ecosystem;

public class OnEnderPearl implements Listener
{
    private final Ecosystem plugin;

    public OnEnderPearl(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerTeleport(ProjectileLaunchEvent e) {
        if (e.getEntityType() != EntityType.ENDER_PEARL) {
            return;
        }

        if (!(e.getEntity().getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity().getShooter();

        if (!plugin.getPlayersInCombat().containsKey(player)) {
            return;
        }

        e.setCancelled(true);
        player.showTitle(Title.title(Component.text(""), Component.text("Du bist im Kampf!")));
    }
}
