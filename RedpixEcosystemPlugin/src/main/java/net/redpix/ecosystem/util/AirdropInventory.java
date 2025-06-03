// This code sucks so much xD

package net.redpix.ecosystem.util;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.redpix.ecosystem.Ecosystem;


public class AirdropInventory implements InventoryHolder {  
    
    public enum InventoryState {
        DEFAULT,
        SETTING_CONTENTS,
        SETTING_NAME,
    }

    private final int[] BORDER_POSITIONS = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53};
    private final Material BORDER = Material.BLACK_STAINED_GLASS_PANE;

    private final Inventory inventory;
    private HashMap<String, Inventory> airdropInventorys = new HashMap<>();
    private final Ecosystem plugin;

    private Inventory contentBuffer; 

    private String name = "test-1";

    private InventoryState state = InventoryState.DEFAULT;

    public AirdropInventory(Ecosystem plugin) {
        this.plugin = plugin;

        this.inventory = plugin.getServer().createInventory(this, 54);
        this.contentBuffer = plugin.getServer().createInventory(this, 54);
    }
    
    public void createAirdropInventory(String name, Inventory inv) {
        this.airdropInventorys.put(name, inv);
    }

    public Inventory getAirdropInventory(String name) {
        return this.airdropInventorys.get(name);
    }

    public InventoryState getState() {
        return state;
    }

    public void setState(InventoryState state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // should i split this up in multiple InventoryHolders? yes...
    private void setContentInv() {
        int[] border_pos = {27,28,29,30,31,32,33,34,35,36,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53};

        ItemStack border = new ItemStack(BORDER);
        ItemMeta border_meta = border.getItemMeta();
        border_meta.displayName(Component.text(""));
        border.setItemMeta(border_meta);
        
        for (int i : border_pos) {
            contentBuffer.setItem(i, border);
        }

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta back_meta =  back.getItemMeta();
        back_meta.getPersistentDataContainer().set(plugin.getAirdropKey(), PersistentDataType.STRING, "back");
        back.setItemMeta(back_meta);

        contentBuffer.setItem(37, back);
    }

    private void setMainInv() {
        inventory.clear();

        // should i write a helper class to create itemStacks? yes. will i? no....
        ItemStack open_content = new ItemStack(Material.CHEST);
        ItemMeta o_meta =  open_content.getItemMeta();
        o_meta.getPersistentDataContainer().set(plugin.getAirdropKey(), PersistentDataType.STRING, "content");
        open_content.setItemMeta(o_meta);

        ItemStack open_name = new ItemStack(Material.OAK_SIGN);
        ItemMeta n_meta =  open_content.getItemMeta();
        n_meta.getPersistentDataContainer().set(plugin.getAirdropKey(), PersistentDataType.STRING, "name");
        open_name.setItemMeta(n_meta);

        ItemStack save = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta save_meta =  save.getItemMeta();
        save_meta.getPersistentDataContainer().set(plugin.getAirdropKey(), PersistentDataType.STRING, "save");
        save.setItemMeta(save_meta);

        inventory.setItem(12, open_content);
        inventory.setItem(14, open_name);
        inventory.setItem(43, save);

        defaultInv();
    }


    // creates black border around inv
    private void defaultInv() {
        ItemStack border = new ItemStack(BORDER);
        ItemMeta border_meta = border.getItemMeta();
        border_meta.displayName(Component.text(""));
        border.setItemMeta(border_meta);
        
        for (int i : BORDER_POSITIONS) {
            inventory.setItem(i, border);
        }
    }

    public ItemStack[] getContent() {
        ItemStack[] content = contentBuffer.getContents();
        ItemStack[] new_content = new ItemStack[27];

        for (int i = 0; i < 27; i++) {
            new_content[i] = content[i];
        }

        return new_content;
    }

    @Override
    public Inventory getInventory() {
        switch (state) {
            case DEFAULT:
                setMainInv();
                return this.inventory;
            case SETTING_NAME:
                return this.inventory;
            case SETTING_CONTENTS:
                setContentInv();
                return this.contentBuffer;
            default:
                return this.inventory;
        }
    }
}

