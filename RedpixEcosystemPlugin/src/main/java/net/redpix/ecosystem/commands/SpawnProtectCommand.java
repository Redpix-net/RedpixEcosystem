package net.redpix.ecosystem.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.redpix.ecosystem.Ecosystem;

public class SpawnProtectCommand implements BasicCommand
{
    private final Ecosystem plugin;

    public SpawnProtectCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (!(commandSourceStack.getExecutor() instanceof Player)) return; 

        Player p = (Player) commandSourceStack.getExecutor();

        p.give(plugin.getZonePick());
    }

    @Override
    public @Nullable String permission() {
        return "spawnprotect.use";
    }
}
