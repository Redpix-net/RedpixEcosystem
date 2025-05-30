package net.redpix.ecosystem;

import java.io.ObjectInputFilter.Config;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.brigadier.tree.LiteralCommandNode;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.redpix.ecosystem.commands.AirdropCommand;
import net.redpix.ecosystem.commands.CheckCommand;
import net.redpix.ecosystem.commands.DiscordCommand;
import net.redpix.ecosystem.commands.ReportCommand;
import net.redpix.ecosystem.commands.SpawnProtectCommand;
import net.redpix.ecosystem.commands.SupportCommand;
import net.redpix.ecosystem.listeners.AirdropListener;
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
import net.redpix.ecosystem.listeners.OnSpawnProtectPick;
import net.redpix.ecosystem.listeners.SpawnZoneListener;
import net.redpix.ecosystem.listeners.SupportInvListener;
import net.redpix.ecosystem.util.AirdropManager;
import net.redpix.ecosystem.util.CombatTimer;
import net.redpix.ecosystem.util.config.ConfigManager;

public class Ecosystem extends JavaPlugin
{
    private ConfigManager configManager = new ConfigManager(this);
    private CombatTimer combatTimer = new CombatTimer(this);
    private AirdropManager airdropManager = new AirdropManager(this);

    private HashMap<Player, Instant> playersInCombat = new HashMap<Player, Instant>();
    private HashMap<Player, Instant> enderPearlCooldown = new HashMap<Player, Instant>();
    private List<Player> playerCheck = new ArrayList<Player>();

    private HashMap<Player, String> supportTickets = new HashMap<Player, String>();

    private Inventory supInv = Bukkit.createInventory(null, 27);
    private Inventory ticketInv = Bukkit.createInventory(null, 27);

    private ItemStack editPick;

    private final NamespacedKey zonePickKey = new NamespacedKey(this, "zone_pick");

    @Override
    public void onEnable() {
        // Please dont ask
        editPick = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta edit_pick_meta = editPick.getItemMeta();
        edit_pick_meta.displayName(Component.text("Spawn Zone Editor"));
        List<Component> lore = new ArrayList<Component>();
        lore.add(Component.text("Can place points for Spawn Zone"));
        edit_pick_meta.lore(lore);
    
        edit_pick_meta.getPersistentDataContainer().set(zonePickKey, PersistentDataType.STRING, "true");

        editPick.setItemMeta(edit_pick_meta);

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
        pm.registerEvents(new OnSpawnProtectPick(this), this);
        pm.registerEvents(new SpawnZoneListener(this), this);
        pm.registerEvents(new AirdropListener(this), this);

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
            event -> {
                event.registrar().register("discord", new DiscordCommand(this));
                event.registrar().register("check", new CheckCommand(this));
                event.registrar().register("report", new ReportCommand(this));
                event.registrar().register("support", new SupportCommand(this));
                event.registrar().register("spawnprotect", new SpawnProtectCommand(this));
                event.registrar().register(new AirdropCommand(this).createCommand().build());
            }
        );
    }

    @Override
    public void onDisable() {
        configManager.save_configs();

        this.saveConfig();
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

    public ItemStack getZonePick() {
        return editPick;
    }

    public NamespacedKey getZonePickKey() {
        return zonePickKey;
    }

    public AirdropManager getAirdropManager() {
        return airdropManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
