package net.redpix.ecosystem;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.redpix.ecosystem.listeners.OnDeath;
import net.redpix.ecosystem.util.config.ConfigManager;

public class Ecosystem extends JavaPlugin
{
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        configManager.init();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnDeath(this), this);
    }

    @Override
    public void onDisable() {
        configManager.save_configs();
    }
}
