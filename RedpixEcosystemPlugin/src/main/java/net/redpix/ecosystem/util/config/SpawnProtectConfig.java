package net.redpix.ecosystem.util.config;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class SpawnProtectConfig implements ConfigurationSerializable {

    private String placeholder;

    public SpawnProtectConfig(String placeholder) {
        this.placeholder = placeholder;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("placeholder", this.placeholder);

        return data;
    }

    public static SpawnProtectConfig deserialize(Map<String, Object> args) {
        return new SpawnProtectConfig (
                (String) args.get("placeholder")
        );
    }
}
