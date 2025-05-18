package net.redpix.ecosystem.util.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Item;

public class StrikeLightingConfig implements ConfigurationSerializable {

    private List<Item> banned_items;

    public StrikeLightingConfig(List<Item> banned_items) {
        this.banned_items = banned_items;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("banned_items", this.banned_items);

        return data;
    }

    public static StrikeLightingConfig deserialize(Map<String, Object> args) {
        return new StrikeLightingConfig (
                (List<Item>) args.get("banned_items")
        );
    }
}
