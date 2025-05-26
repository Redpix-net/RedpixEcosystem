package net.redpix.ecosystem.commands;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.redpix.ecosystem.Ecosystem;

public class ReportCommand implements BasicCommand
{
    private final Ecosystem plugin;

    public ReportCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length < 2) {
            commandSourceStack.getSender().sendMessage(Component.text("Usage: /report [player] [reason]", NamedTextColor.RED));
            return;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (!p.hasPermission("mod")) {
                continue;
            }

            p.sendMessage("&c&lʀᴇᴘᴏʀᴛ &r&8»&r&f" + message);
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
