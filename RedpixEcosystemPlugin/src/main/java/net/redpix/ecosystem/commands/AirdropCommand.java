package net.redpix.ecosystem.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.redpix.ecosystem.Ecosystem;

public class AirdropCommand {
    private final Ecosystem plugin;

    public AirdropCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    public LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("airdrop")
        .then(Commands.literal("summon")
            .requires(sender -> sender.getSender().hasPermission("airdrop.summon"))
            .then(Commands.argument("id", IntegerArgumentType.integer())
                .executes(this::executeSummon))
        )
        .then(Commands.literal("create")
            .requires(sender -> sender.getSender().hasPermission("airdrop.create"))
            .executes(this::executeCreate)
        )
        .then(Commands.literal("start")
            .executes(this::executeStart)
        )
        .then(Commands.literal("stop")
            .executes(this::executeStop)
        )
        .then(Commands.literal("list")
            .executes(this::executeList)
        )
        .then(Commands.literal("remove")
            .then(Commands.argument("id", IntegerArgumentType.integer())
                .executes(this::executeRemove))
        )
        .then(Commands.literal("reload")
            .executes(this::executeRefresh)
        );
    }

    private int executeSummon(CommandContext<CommandSourceStack> ctx) {
        int id = ctx.getArgument("id", Integer.class);

        Player p = (Player) ctx.getSource().getSender();

        plugin.getAirdropManager().summon(p, id);

        return Command.SINGLE_SUCCESS;
    }

    private int executeCreate(CommandContext<CommandSourceStack> ctx) {
        plugin.getAirdropManager().openMenu((Player) ctx.getSource().getSender());

        return Command.SINGLE_SUCCESS;
    }

    private int executeStart(CommandContext<CommandSourceStack> ctx) {
        plugin.getAirdropManager().setRunning(true);
        plugin.getAirdropManager().spawnClock();

        return Command.SINGLE_SUCCESS;
    }

    private int executeStop(CommandContext<CommandSourceStack> ctx) {
        plugin.getAirdropManager().setRunning(false);

        return Command.SINGLE_SUCCESS;
    }

    private int executeList(CommandContext<CommandSourceStack> ctx) {
        Player p = (Player) ctx.getSource().getSender();
    
        HashMap<Integer, String> airdrops = plugin.getConfigManager().getAirdropConfig().getAirdropList();

        for (Map.Entry<Integer, String> set : airdrops.entrySet())  {
            p.sendMessage(Component.text(String.format("ID[%s]: %s", set.getKey().toString(), set.getValue())));
        }

        return Command.SINGLE_SUCCESS;
    }

    private int executeRemove(CommandContext<CommandSourceStack> ctx) {
        int id = ctx.getArgument("id", Integer.class);

        plugin.getConfigManager().getAirdropConfig().removeAirdrop(String.valueOf(id));

        return Command.SINGLE_SUCCESS;
    }

    private int executeRefresh(CommandContext<CommandSourceStack> ctx) {
        plugin.getAirdropManager().refresh();

        return Command.SINGLE_SUCCESS;
    }
}
