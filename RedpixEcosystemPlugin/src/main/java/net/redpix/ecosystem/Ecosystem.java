package net.redpix.ecosystem;

import java.time.Instant;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.redpix.ecosystem.listeners.CancelCommand;
import net.redpix.ecosystem.listeners.OnDeath;
import net.redpix.ecosystem.listeners.OnDrop;
import net.redpix.ecosystem.listeners.OnEnderPearl;
import net.redpix.ecosystem.listeners.OnEnterFight;
import net.redpix.ecosystem.listeners.OnLeave;
import net.redpix.ecosystem.listeners.OnPickup;
import net.redpix.ecosystem.util.CombatTimer;
import net.redpix.ecosystem.util.config.ConfigManager;

public class Ecosystem extends JavaPlugin
{
    private ConfigManager configManager = new ConfigManager(this);
    private CombatTimer combatTimer = new CombatTimer(this);

    private HashMap<Player, Instant> playersInCombat = new HashMap<Player, Instant>();
    private HashMap<Player, Instant> enderPearlCooldown = new HashMap<Player, Instant>();
    private HashMap<Player, Set<Player>> combatPairs = new HashMap<Player, Set<Player>>();

    @Override
    public void onEnable() {
        configManager.init();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnDeath(this), this);
        pm.registerEvents(new OnPickup(this), this);
        pm.registerEvents(new OnDrop(this), this);
        pm.registerEvents(new OnEnterFight(this), this);
        pm.registerEvents(new OnLeave(this), this);
        pm.registerEvents(new OnEnderPearl(this), this);
        pm.registerEvents(new CancelCommand(this), this);
    }

    @Override
    public void onDisable() {
        configManager.save_configs();
    }

    public HashMap<Player, Instant> getPlayersInCombat() {
        return playersInCombat;
    }

    public HashMap<Player, Set<Player>> getCombatPairs() {
        return combatPairs;
    }

    public HashMap<Player, Instant> getEnderPearlCooldown() {
        return enderPearlCooldown;
    }

    public CombatTimer getCombatTimer() {
        return combatTimer;
    }
}
