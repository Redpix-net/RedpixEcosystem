package net.redpix.ecosystem;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import net.redpix.ecosystem.listeners.LightingOnDeathListener;

public class Ecosystem extends JavaPlugin
{
    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new LightingOnDeathListener(this), this);
    }

    @Override
    public void onDisable() {}
}
