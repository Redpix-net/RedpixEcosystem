package net.redpix.ecosystem;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.redpix.ecosystem.listeners.OnDeath;

public class Ecosystem extends JavaPlugin
{
    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnDeath(this), this);
    }

    @Override
    public void onDisable() {}
}
