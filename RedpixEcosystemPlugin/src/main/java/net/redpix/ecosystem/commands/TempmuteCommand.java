package net.redpix.ecosystem.commands;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.redpix.ecosystem.Ecosystem;

public class TempmuteCommand {
    private final Ecosystem plugin;

    public TempmuteCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    public LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("tempmute")
        .requires(sender -> sender.getSender().hasPermission("tempmute.use"))
        .then(Commands.argument("player", StringArgumentType.word())
            .then(Commands.argument("seconds", IntegerArgumentType.integer())
                .then(Commands.argument("minutes", IntegerArgumentType.integer())
                    .then(Commands.argument("hours", IntegerArgumentType.integer())
                        .then(Commands.argument("days", IntegerArgumentType.integer())
                            .then(Commands.argument("reason", StringArgumentType.greedyString())
                            .executes(this::executeTempmute)))))));
    }

    private int executeTempmute(CommandContext<CommandSourceStack> ctx) {
        String player_name = ctx.getArgument("player", String.class);
        int seconds = ctx.getArgument("seconds", Integer.class);
        int minutes = ctx.getArgument("minutes", Integer.class);
        int hours = ctx.getArgument("hours", Integer.class);
        int days = ctx.getArgument("days", Integer.class);
        String reason = ctx.getArgument("reason", String.class);
        
        Player p = Bukkit.getPlayer(player_name);

        if (p == null) {
            return Command.SINGLE_SUCCESS;
        }
        
        Instant time = Instant.now()
            .plusSeconds(seconds)
            .plus(Duration.ofMinutes(minutes))
            .plus(Duration.ofHours(hours))
            .plus(Duration.ofDays(days));
        
        if (plugin.getMutedPlayers().containsKey(p)) {
            // TODO! send message to Sender that player is already muted!
            return Command.SINGLE_SUCCESS;
        }

        plugin.getMutedPlayers().put(p, time);
        
        return Command.SINGLE_SUCCESS;
    }
}
