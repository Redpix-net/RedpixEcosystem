package net.redpix.ecosystem;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.redpix.ecosystem.commands.CheckCommand;
import net.redpix.ecosystem.commands.DiscordCommand;
import net.redpix.ecosystem.commands.ReportCommand;
import net.redpix.ecosystem.commands.SupportCommand;
import net.redpix.ecosystem.listeners.CancelCommand;
import net.redpix.ecosystem.listeners.FreezePlayer;
import net.redpix.ecosystem.listeners.GrindstoneListener;
import net.redpix.ecosystem.listeners.MaceGlow;
import net.redpix.ecosystem.listeners.OnCraft;
import net.redpix.ecosystem.listeners.OnDeath;
import net.redpix.ecosystem.listeners.OnDrop;
import net.redpix.ecosystem.listeners.OnEnderPearl;
import net.redpix.ecosystem.listeners.OnEnterFight;
import net.redpix.ecosystem.listeners.OnLeave;
import net.redpix.ecosystem.listeners.OnPickup;
import net.redpix.ecosystem.listeners.OnPlace;
import net.redpix.ecosystem.listeners.SupportInvListener;
import net.redpix.ecosystem.util.CombatTimer;
import net.redpix.ecosystem.util.config.ConfigManager;

public class Ecosystem extends JavaPlugin
{
    private ConfigManager configManager = new ConfigManager(this);
    private CombatTimer combatTimer = new CombatTimer(this);

    private HashMap<Player, Instant> playersInCombat = new HashMap<Player, Instant>();
    private HashMap<Player, Instant> enderPearlCooldown = new HashMap<Player, Instant>();
    private List<Player> playerCheck = new ArrayList<Player>();

    private HashMap<Player, String> supportTickets = new HashMap<Player, String>();

    private Inventory supInv = Bukkit.createInventory(null, 27);
    private Inventory ticketInv = Bukkit.createInventory(null, 27);

    @Override
    public void onEnable() {
        configManager.init();

        PluginManager pm = getServer().getPluginManager();

        // TODO! REWRITE 
        pm.registerEvents(new OnDeath(this), this);
        pm.registerEvents(new OnPickup(this), this);
        pm.registerEvents(new OnDrop(this), this);
        pm.registerEvents(new OnEnterFight(this), this);
        pm.registerEvents(new OnLeave(this), this);
        pm.registerEvents(new OnEnderPearl(this), this);
        pm.registerEvents(new CancelCommand(this), this);
        pm.registerEvents(new FreezePlayer(this), this);
        pm.registerEvents(new OnCraft(this), this);
        pm.registerEvents(new OnPlace(this), this);
        pm.registerEvents(new GrindstoneListener(this), this);
        pm.registerEvents(new MaceGlow(this), this);
        pm.registerEvents(new SupportInvListener(this), this);

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
            event -> {
                event.registrar().register("discord", new DiscordCommand(this));
                event.registrar().register("check", new CheckCommand(this));
                event.registrar().register("report", new ReportCommand(this));
                event.registrar().register("support", new SupportCommand(this));
            }
        );
    }

    @Override
    public void onDisable() {
        configManager.save_configs();
    }

    public HashMap<Player, Instant> getPlayersInCombat() {
        return playersInCombat;
    }

    public HashMap<Player, Instant> getEnderPearlCooldown() {
        return enderPearlCooldown;
    }

    public HashMap<Player, String> getSupportTickets() {
        return supportTickets;
    }

    public List<Player> getPlayerCheck() {
        return playerCheck;
    }

    public CombatTimer getCombatTimer() {
        return combatTimer;
    }

    public Inventory getSupInv() {
        return supInv;
    }

    public Inventory getTicketInv() {
        return ticketInv;
    }
}
