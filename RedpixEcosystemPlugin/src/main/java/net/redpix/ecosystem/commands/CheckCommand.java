package net.redpix.ecosystem.commands;

import java.util.Collection;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
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

        if (args.length == 1) {
            plugin.getPlayerCheck().add(player);
            player.sendMessage(
                Component.text("ᴠᴇʀʟᴀѕѕᴇ ᴅᴇɴ ѕᴇʀᴠᴇʀ ɴɪᴄʜᴛ, ᴛʀɪᴛᴛ ᴅᴇᴍ ᴡᴀʀᴛᴇʀᴀᴜᴍ ʙᴇɪ. ꜰᴀʟʟѕ ᴅᴜ ɴᴏᴄʜ ɴɪᴄʜᴛ ɪᴍ ᴅɪѕᴄᴏʀᴅ ʙɪѕᴛ, ʜɪᴇʀ ɪѕᴛ ᴅᴇʀ ʟɪɴᴋ. ", NamedTextColor.RED)
                .append(Component.text("discord.gg/redpix \n", NamedTextColor.RED).clickEvent(ClickEvent.openUrl("https://discord.gg/redpix")))
            );
            player.sendMessage(Component.text("ᴅᴜ ᴡüʀᴅᴇѕᴛ ᴢᴜ ᴇɪɴᴇʀ üʙᴇʀᴘʀüꜰᴜɴɢ ᴀᴜѕɢᴇᴡäʜʟᴛ.", NamedTextColor.RED));

            player.setAllowFlight(true);

            player.showTitle(Title.title(
                Component.text("üʙᴇʀᴘʀüꜰᴜɴɢ", NamedTextColor.RED), 
                Component.text("ᴅᴜ ᴡüʀᴅᴇѕᴛ ᴢᴜ ᴇɪɴᴇʀ üʙᴇʀᴘʀüꜰᴜɴɢ ᴀᴜѕɢᴇᴡäʜʟᴛ", NamedTextColor.RED),
                null
            ));

            return;
        }

        switch (args[1]) {
            case "pass":
                player.sendMessage(Component.text("ᴅᴜ ʜᴀѕᴛ ᴅɪᴇ üʙᴇʀᴘʀüꜰᴜɴɢ ʙᴇѕᴛᴀɴᴅᴇɴ! ᴅᴜ ᴋᴀɴɴѕᴛ ᴡᴇɪᴛᴇʀ ѕᴘɪᴇʟᴇɴ.", NamedTextColor.RED));
                plugin.getPlayerCheck().remove(player);
                player.setAllowFlight(false);
                break;
            case "deny":
                plugin.getPlayerCheck().remove(player);
                player.ban("Cheats found on check", (Date) null, "");
                player.setAllowFlight(false);
                break;
            default:
                break;
        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length == 0) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }

        return Bukkit.getOnlinePlayers().stream()
            .map(Player::getName)
            .filter(name -> name.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
            .toList();
    }


    @Override
    public @Nullable String permission() {
        return "";
    }
}
