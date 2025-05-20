package net.redpix.ecosystem.util.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Item;

public class StrikeLightingConfig implements ConfigurationSerializable {

    private String placeholder;

    public StrikeLightingConfig(String placeholder) {
        this.placeholder = placeholder;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("placeholder", this.placeholder);

        return data;
    }

    public static StrikeLightingConfig deserialize(Map<String, Object> args) {
        return new StrikeLightingConfig (
                (String) args.get("placeholder")
        );
    }
}
