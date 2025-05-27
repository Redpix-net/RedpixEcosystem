package net.redpix.ecosystem.commands;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.Nullable;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.redpix.ecosystem.Ecosystem;

public class SupportCommand implements BasicCommand
{
    private final Ecosystem plugin;

    public SupportCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (!(commandSourceStack.getSender() instanceof Player)) {
            return;
        }

        // open gui
        if (args.length == 0) {
            openPlayerInventory((Player) commandSourceStack.getSender());
            return;
        }

        // close | create
        switch (args[0]) {
            case "close":
                Player p = Bukkit.getPlayerExact(args[1]);

                if (p == null) return;

                plugin.getSupportTickets().remove(p);

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
        return "support.use";
    }

    public void openPlayerInventory(Player p) {
        Inventory inv = plugin.getSupInv();

        ItemStack createTicket = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemStack closeTicket = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemStack viewTickets = new ItemStack(Material.PAPER, 1);

        ItemMeta createMeta = createTicket.getItemMeta();
        ItemMeta closeMeta = closeTicket.getItemMeta();
        ItemMeta viewMeta = viewTickets.getItemMeta();

        createMeta.displayName(Component.text("Create Ticket"));
        closeMeta.displayName(Component.text("Close Ticket"));
        viewMeta.displayName(Component.text("View open Tickets"));

        createTicket.setItemMeta(createMeta);
        closeTicket.setItemMeta(closeMeta);
        viewTickets.setItemMeta(viewMeta);

        if (p.hasPermission("support.mod")) {
            inv.setItem(12, viewTickets);
            inv.setItem(14, createTicket);
        } else {
            inv.setItem(12, createTicket);
            inv.setItem(14, closeTicket);
        }

        p.openInventory(inv);
    }
}
