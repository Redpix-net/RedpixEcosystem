package net.redpix.ecosystem.commands;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.redpix.ecosystem.Ecosystem;
import net.redpix.ecosystem.util.config.TempBanMuteConfig;

public class TempbanCommand {
    private final Ecosystem plugin;

    public TempbanCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    public LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("tempban")
        .requires(sender -> sender.getSender().hasPermission("tempban.use"))
        .then(Commands.argument("player", StringArgumentType.word())
            .suggests(this::getPlayerSuggestions)
            .then(Commands.argument("time", StringArgumentType.word())
                .then(Commands.argument("reason", StringArgumentType.greedyString())
                    .executes(this::executeTempban))));
    }

    private int executeTempban(CommandContext<CommandSourceStack> ctx) {
        String player_name = ctx.getArgument("player", String.class);
        String time = ctx.getArgument("time", String.class);
        String reason = ctx.getArgument("reason", String.class);
        
        Player p = Bukkit.getPlayer(player_name);
        CommandSender sender = ctx.getSource().getSender();

        TempBanMuteConfig configManager = plugin.getConfigManager().getTempBanMuteConfig();

        if (p == null) {
            sender.sendMessage(configManager.getMessage("ban-no-player-found", p, reason));

            return Command.SINGLE_SUCCESS;
        }
        
        if (time.length() <= 1) {
            sender.sendMessage(configManager.getMessage("ban-wrong-time-input", p, reason));
            return Command.SINGLE_SUCCESS;
        }

        Instant time_ban = Instant.now();
        int time_length = Integer.parseInt(time.substring(0, time.length() - 1));

        switch (time.charAt(time.length() - 1)) {
            case 'm':
                time_ban = time_ban.plus(Duration.ofDays(time_length * 31));
                break;
            case 'w':
                time_ban = time_ban.plus(Duration.ofDays(time_length * 7));
                break;
            case 'd':
                time_ban = time_ban.plus(Duration.ofDays(time_length));
                break;
            case 'h':
                time_ban = time_ban.plus(Duration.ofHours(time_length));
                break;
            case 's':
                time_ban = time_ban.plus(Duration.ofSeconds(time_length));
                break;
            default: 
                sender.sendMessage(configManager.getMessage("ban-wrong-time-input", p, reason));
                return Command.SINGLE_SUCCESS;
        }

        p.ban(reason, time_ban, ctx.getSource().getSender().getName());

        sender.sendMessage(configManager.getMessage("ban-player-has-been-banned", p, reason));

        plugin.getServer().broadcast(configManager.getMessage("ban-broadcast", p, reason));

        return Command.SINGLE_SUCCESS;
    }

    private CompletableFuture<Suggestions> getPlayerSuggestions(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) {
        List<String> players = Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();

        players.stream()
            .filter(entry -> entry.toLowerCase().startsWith(builder.getRemainingLowerCase()))
            .forEach(builder::suggest);

        return builder.buildFuture();
    }
}
