package net.redpix.ecosystem.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
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
            .then(Commands.argument("name", StringArgumentType.word())
                .executes(this::executeSummon))
        )
        .then(Commands.literal("create")
            .requires(sender -> sender.getSender().hasPermission("airdrop.create"))
            .executes(this::executeCreate)
        );
    }

    private int executeSummon(CommandContext<CommandSourceStack> ctx) {
        String name = ctx.getArgument("name", String.class);

        Player p = (Player) ctx.getSource().getSender();

        Location loc = p.getLocation();
        loc.getBlock().setType(Material.BARREL);

        Barrel airdrop = (Barrel) loc.getBlock().getState();

        ItemStack[] items = plugin.getConfigManager().getAirdropConfig().getContent(name);

        airdrop.getInventory().setContents(items);

        return Command.SINGLE_SUCCESS;
    }

    private int executeCreate(CommandContext<CommandSourceStack> ctx) {
        plugin.getAirdropManager().openMenu((Player) ctx.getSource().getSender());

        return Command.SINGLE_SUCCESS;
    }
}
