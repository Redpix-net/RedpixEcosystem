package net.redpix.ecosystem.util.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import net.redpix.ecosystem.Ecosystem;

public class AirdropConfig {
    private final File file;
    
    public AirdropConfig(Ecosystem plugin) {
        this.file = new File(plugin.getDataFolder(), "airdrop.yml");
    }

    public void saveAirdrop(ItemStack[] content, String name) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(String.format("airdrop-%s.name", name), name);
        config.set(String.format("airdrop-%s.content", name), content);

        try {
            config.save(file);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public ItemStack[] getContent(String name) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        
        List<ItemStack> items = new ArrayList<>();

        for (Object obj : config.getList(String.format("airdrop-%s.content", name))) {
            if (!(obj instanceof ItemStack)) {
                items.add(null);
                continue;
            };

            items.add((ItemStack)obj);
        }

        return (items.toArray(new ItemStack[0]));
    }

    public List<ItemStack[]> getAllAirdrops() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<ItemStack[]> airdrops = new ArrayList<>();

        for (String key: config.getKeys(false)) {
            ItemStack[] itemStacks = new ItemStack[27];
            int i = 0;

            ConfigurationSection airdrop = config.getConfigurationSection(key);
                
            if (airdrop == null) continue;
    
            List<?> content = airdrop.getList("content");
            for (Object obj : content) {
                if (!(obj instanceof ItemStack)) {
                    itemStacks[i] = null;
                    i++;
                    continue;
                }

                itemStacks[i] = (ItemStack) obj;
                i++;
            }

            airdrops.add(itemStacks);
        }

        return airdrops;
    }
}
