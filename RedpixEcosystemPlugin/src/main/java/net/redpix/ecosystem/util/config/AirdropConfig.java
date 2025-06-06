package net.redpix.ecosystem.util.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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

    public void saveAirdrop(ItemStack[] content, String name, HashMap<Integer, Integer> chances) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        int id = 0;

        for (String key : config.getKeys(false)) {
            id = Integer.parseInt(key.substring(8));
        }

        config.set(String.format("airdrop-%s.name", id), name);
        config.set(String.format("airdrop-%s.content", id), content);
        config.set(String.format("airdrop-%s.chances", id), chances);
        config.set(String.format("airdrop-%s.id", id), id);

        try {
            config.save(file);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // ID - Chances
    public HashMap<Integer, Integer> getChances(String id) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        
        HashMap<Integer, Integer> chances = new HashMap<>();

        ConfigurationSection chance = config.getConfigurationSection(String.format("airdrop-%s.chances", id));

        if (chance == null) return chances;

        for (String key : chance.getKeys(false)) {
            try {
                int keyValue = Integer.parseInt(key);
                int value = chance.getInt(key);
                
                chances.put(keyValue, value);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        return chances;
    }

    public ItemStack[] getContent(String id) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        
        List<ItemStack> items = new ArrayList<>();

        for (Object obj : config.getList(String.format("airdrop-%s.content", id))) {
            if (!(obj instanceof ItemStack)) {
                items.add(null);
                continue;
            };

            items.add((ItemStack)obj);
        }

        return (items.toArray(new ItemStack[0]));
    }

    // ID - Content
    // could i just use the getContent methode from above? yes... will i? hahahah... no .-.
    public HashMap<Integer, ItemStack[]> getAllAirdrops() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        HashMap<Integer, ItemStack[]> airdrops = new HashMap<>();
        
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
            String id = airdrop.getString("id");

            airdrops.put(Integer.parseInt(id), itemStacks);
        }

        return airdrops;
    }
}
