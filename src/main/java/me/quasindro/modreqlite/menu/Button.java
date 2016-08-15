package me.quasindro.modreqlite.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public abstract class Button {

    private Material mat;
    private String title;
    private List<String> description;
    private ItemStack is;

    public Button(Material mat, String title, String... lines) {
        this.mat = mat;
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        description = Arrays.asList(lines);
        for (ListIterator<String> it = description.listIterator(); it.hasNext();) {
            String line = it.next();
            it.set(ChatColor.translateAlternateColorCodes('&', line));
        }
        is = new ItemStack(mat);
        ItemMeta meta = is.getItemMeta();
        for (ItemFlag flag : ItemFlag.values()) {
            meta.addItemFlags(flag);
        }
        meta.setDisplayName(this.title);
        meta.setLore(description);
        is.setItemMeta(meta);
    }

    public ItemStack getItemStack() {
        return is;
    }

    public abstract void execute(Player p);
}
