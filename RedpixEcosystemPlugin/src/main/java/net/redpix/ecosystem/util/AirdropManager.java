package net.redpix.ecosystem.util;

import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Barrel;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import net.kyori.adventure.text.Component;
import net.redpix.ecosystem.Ecosystem;

public class AirdropManager {  
    private final AirdropInventory inv;
    private final Ecosystem plugin;

    private boolean running;
    private long time_ticks;
    private long time_ticks_current;

    private List<ItemStack[]> airdrops;

    public AirdropManager(Ecosystem plugin) {
        this.inv = new AirdropInventory(plugin);
        this.plugin = plugin;
        this.time_ticks = plugin.getConfigManager().getAirdropSettingsConfig().getConfig().getLong("spawn-time");
        this.airdrops = plugin.getConfigManager().getAirdropConfig().getAllAirdrops();
    }

    public void openMenu(Player player) {
        player.openInventory(inv.getInventory());
    }

    public AirdropInventory getInventory() {
        return inv;
    }

    public void saveToAirdrop() {
        plugin.getConfigManager().getAirdropConfig().saveAirdrop(inv.getContent(), inv.getName());
    }

    public void summon(Player player, String name) {
        Location loc = player.getLocation();
        loc.getBlock().setType(Material.BARREL);

        Barrel airdrop = (Barrel) loc.getBlock().getState();

        ItemStack[] items = plugin.getConfigManager().getAirdropConfig().getContent(name);

        airdrop.getInventory().setContents(items);
    }

    public void summon_random(ItemStack[] content) {
        Random rand = new Random();

        YamlConfiguration config = plugin.getConfigManager().getAirdropSettingsConfig().getConfig();

        World world = plugin.getServer().getWorld(config.getString("world"));

        int x = rand.nextInt(config.getInt("maxX"));
        int z = rand.nextInt(config.getInt("maxZ"));

        int chunkX = x >> 4;
        int chunkZ = z >> 4;

        plugin.getServer().getRegionScheduler().run(plugin, world, chunkX, chunkZ, task -> {
            Chunk chunk = world.getChunkAt(chunkX, chunkZ);
            chunk.load();

            int y = world.getHighestBlockYAt(x, z);
            
            plugin.getServer().broadcast(Component.text(String.format("%s, %s, %s", x, y, z)));

            Location loc = new Location(world, x, y+1, z);

            loc.getBlock().setType(Material.BARREL);

            Barrel airdrop = (Barrel) loc.getBlock().getState();

            airdrop.getInventory().setContents(content);
        });
    }

    public void spawnClock() {
        GlobalRegionScheduler globalRegionScheduler = plugin.getServer().getGlobalRegionScheduler();
        Random rand = new Random();

        time_ticks_current = time_ticks;

        globalRegionScheduler.runAtFixedRate(plugin, task -> {
            if (!running) {
                return;
            }

            if (time_ticks_current <= 0) {
                int i = rand.nextInt(airdrops.size());
                summon_random(airdrops.get(i));
                time_ticks_current = time_ticks;
            }

            time_ticks_current -= 1;
        }, 1L, 1L);
    }


    public void setRunning(boolean running) {
        this.running = running;
    }
}
