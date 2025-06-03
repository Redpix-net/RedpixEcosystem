package net.redpix.ecosystem.util;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Barrel;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.redpix.ecosystem.Ecosystem;

public class AirdropManager {  
    private final AirdropInventory inv;
    private final Ecosystem plugin;

    public AirdropManager(Ecosystem plugin) {
        this.inv = new AirdropInventory(plugin);
        this.plugin = plugin;
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

    public void summon_random(String name) {
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

            ItemStack[] items = plugin.getConfigManager().getAirdropConfig().getContent(name);

            airdrop.getInventory().setContents(items);

        });
    }
}
