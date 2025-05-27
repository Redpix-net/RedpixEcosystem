package net.redpix.ecosystem.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.Nullable;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.redpix.ecosystem.Ecosystem;

public class SpawnProtectCommand implements BasicCommand
{
    private final Ecosystem plugin;

    public SpawnProtectCommand(Ecosystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (!(commandSourceStack.getSender() instanceof Player)) return; 

        Player p = (Player) commandSourceStack.getSender();

        ItemStack editPick = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta edit_pick_meta = editPick.getItemMeta();
        edit_pick_meta.displayName(Component.text("Spawn Zone Editor"));
        List<Component> lore = new ArrayList<Component>();
        lore.add(Component.text("Can place points for Spawn Zone"));
        edit_pick_meta.lore(lore);
    
        edit_pick_meta.getPersistentDataContainer().set(plugin.getZonePickKey(), PersistentDataType.STRING, "true");

        editPick.setItemMeta(edit_pick_meta);

        p.getInventory().setItemInMainHand(editPick);
    }

    @Override
    public @Nullable String permission() {
        return "spawnprotect.use";
    }
}
