package net.redpix.ecosystem.commands;

import org.jspecify.annotations.Nullable;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.redpix.ecosystem.Ecosystem;

public class ReportCommand implements BasicCommand
{
    private final Ecosystem plugin;

    public ReportCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        
    }

    @Override
    public @Nullable String permission() {
        return "";
    }
}
