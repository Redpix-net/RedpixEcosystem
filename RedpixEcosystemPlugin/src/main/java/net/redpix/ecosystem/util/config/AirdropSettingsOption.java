package net.redpix.ecosystem.util.config;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class AirdropSettingsOption implements ConfigurationSerializable {
    private String placeholder;

    public AirdropSettingsOption(String placeholder) {
        this.placeholder = placeholder;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("placeholder", this.placeholder);

        return data;
    }

    public static MutedPlayersOption deserialize(Map<String, Object> args) {
        return new MutedPlayersOption (
                (String) args.get("placeholder")
        );
    }
}
