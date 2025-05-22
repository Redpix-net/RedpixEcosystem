package net.redpix.ecosystem.commands;

import org.jspecify.annotations.Nullable;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.redpix.ecosystem.Ecosystem;

public class DiscordCommand implements BasicCommand
{
    private final Ecosystem plugin;

    public DiscordCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        commandSourceStack.getExecutor().sendMessage(
            Component.text("ᴅɪѕᴄᴏʀᴅ \n", NamedTextColor.BLUE)
            .append(Component.text("ᴋʟɪᴄᴋᴇ ᴀᴜꜰ ᴅᴇɴ ʟɪɴᴋ ᴜɴᴛᴇɴ, ᴜᴍ ᴜɴѕᴇʀᴇᴍ ᴅɪѕᴄᴏʀᴅ ѕᴇʀᴠᴇʀ ʙᴇɪᴢᴜᴛʀᴇᴛᴇɴ! \n", NamedTextColor.WHITE))
            .append(Component.text("↓ ᴋʟɪᴄᴋᴇ ʜɪᴇʀ ↓ \n", NamedTextColor.RED))
            .append(Component.text("discord.gg/redpix \n", NamedTextColor.WHITE).clickEvent(ClickEvent.openUrl("https://discord.gg/redpix")))
        );
    }

    @Override
    public @Nullable String permission() {
        return "xlinks.discord";
    }
}
