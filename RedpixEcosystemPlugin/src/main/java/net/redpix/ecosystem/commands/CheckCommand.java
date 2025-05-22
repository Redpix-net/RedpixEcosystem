package net.redpix.ecosystem.commands;

import java.util.Date;

import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.redpix.ecosystem.Ecosystem;

public class CheckCommand implements BasicCommand
{
    private final Ecosystem plugin;

    public CheckCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length == 0) {
            commandSourceStack.getSender().sendMessage(Component.text("Usage: /check <player> pass | deny ", NamedTextColor.RED));
            return;
        }

        Player player = (Player) plugin.getServer().getPlayer(args[0]);
        
        if (player == null) {
            commandSourceStack.getSender().sendMessage(Component.text(("Der Spieler " + args[0] + " ist nicht online"), NamedTextColor.RED));
            return;
        }

        if (args.length >= 1) {
            plugin.getPlayerCheck().add(player);
            player.sendMessage(Component.text("ᴅᴜ ᴡüʀᴅᴇѕᴛ ᴢᴜ ᴇɪɴᴇʀ üʙᴇʀᴘʀüꜰᴜɴɢ ᴀᴜѕɢᴇᴡäʜʟᴛ.", NamedTextColor.RED));

            player.showTitle(Title.title(
                Component.text("üʙᴇʀᴘʀüꜰᴜɴɢ", NamedTextColor.RED), 
                Component.text("ᴅᴜ ᴡüʀᴅᴇѕᴛ ᴢᴜ ᴇɪɴᴇʀ üʙᴇʀᴘʀüꜰᴜɴɢ ᴀᴜѕɢᴇᴡäʜʟᴛ", NamedTextColor.RED)
            ));
        }

        if (args.length <= 1) {
            return;
        }

        switch (args[1]) {
            case "pass":
                player.sendMessage(Component.text("ᴅᴜ ʜᴀѕᴛ ᴅɪᴇ üʙᴇʀᴘʀüꜰᴜɴɢ ʙᴇѕᴛᴀɴᴅᴇɴ! ᴅᴜ ᴋᴀɴɴѕᴛ ᴡᴇɪᴛᴇʀ ѕᴘɪᴇʟᴇɴ.", NamedTextColor.RED));
                plugin.getPlayerCheck().remove(player);
                break;
            case "deny":
                plugin.getPlayerCheck().remove(player);
                player.ban("Cheats found on check", (Date) null, "");
                break;
            default:
                break;
        }
    }

    @Override
    public @Nullable String permission() {
        return "";
    }
}
