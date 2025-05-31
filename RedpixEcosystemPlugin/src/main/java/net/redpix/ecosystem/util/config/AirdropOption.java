package net.redpix.ecosystem.util.config;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class AirdropOption implements ConfigurationSerializable {

    private String placeholder;

    public AirdropOption(String placeholder) {
        this.placeholder = placeholder;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("placeholder", this.placeholder);

        return data;
    }

    public static AirdropOption deserialize(Map<String, Object> args) {
        return new AirdropOption (
                (String) args.get("placeholder")
        );
    }
}
