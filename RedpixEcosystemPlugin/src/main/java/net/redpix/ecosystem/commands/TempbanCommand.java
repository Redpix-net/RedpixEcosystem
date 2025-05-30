package net.redpix.ecosystem.commands;

import java.time.Duration;

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

public class TempbanCommand {
    private final Ecosystem plugin;

    public TempbanCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    public LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("tempban")
        .requires(sender -> sender.getSender().hasPermission("tempban.use"))
        .then(Commands.argument("player", StringArgumentType.word())
            .then(Commands.argument("seconds", IntegerArgumentType.integer())
                .then(Commands.argument("minutes", IntegerArgumentType.integer())
                    .then(Commands.argument("hours", IntegerArgumentType.integer())
                        .then(Commands.argument("days", IntegerArgumentType.integer())
                            .then(Commands.argument("reason", StringArgumentType.greedyString())
                            .executes(this::executeTempban)))))));
    }

    private int executeTempban(CommandContext<CommandSourceStack> ctx) {
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
        
        Duration time = Duration.ZERO
            .plusSeconds(seconds)
            .plusMinutes(minutes)
            .plusHours(hours)
            .plusDays(days);

        p.ban(reason, time, ctx.getSource().getSender().getName());

        plugin.getServer().broadcast();

        return Command.SINGLE_SUCCESS;
    }
}
