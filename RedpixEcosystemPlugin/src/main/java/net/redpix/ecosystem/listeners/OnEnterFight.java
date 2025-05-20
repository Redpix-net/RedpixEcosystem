package net.redpix.ecosystem.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class OnEnterFight implements Listener
{
    private final Plugin plugin;

    public OnEnterFight(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntityType() != EntityType.PLAYER) {
            return;
        }

        Entity player = e.getEntity();
        BossBar bar = BossBar.bossBar(Component.text(""), 1, BossBar.Color.PURPLE, BossBar.Overlay.NOTCHED_6);
        
        e.getEntity().getScheduler().run(plugin, task -> {
            player.showBossBar(bar);
        }, null);
    }
}
