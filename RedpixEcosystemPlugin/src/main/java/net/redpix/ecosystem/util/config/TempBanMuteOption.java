package net.redpix.ecosystem.util.config;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class TempBanMuteOption implements ConfigurationSerializable {

    private String placeholder;

    public TempBanMuteOption(String placeholder) {
        this.placeholder = placeholder;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("placeholder", this.placeholder);

        return data;
    }

    public static TempBanMuteOption deserialize(Map<String, Object> args) {
        return new TempBanMuteOption (
                (String) args.get("placeholder")
        );
    }
}
