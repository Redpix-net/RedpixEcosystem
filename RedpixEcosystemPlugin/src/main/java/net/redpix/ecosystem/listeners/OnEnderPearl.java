package net.redpix.ecosystem.listeners;

import java.time.Duration;
import java.time.Instant;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

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

        if (!plugin.getEnderPearlCooldown().containsKey(player)) {
            plugin.getEnderPearlCooldown().put(player, Instant.now().plusSeconds(30));

            return;
        }
        
        Long cooldown = Duration.between(Instant.now(), plugin.getEnderPearlCooldown().get(player)).getSeconds();

        if (cooldown <= 0) {
            plugin.getEnderPearlCooldown().remove(player);

            return;
        }

        e.setCancelled(true);
    }
}
