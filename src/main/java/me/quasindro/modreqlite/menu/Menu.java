package me.quasindro.modreqlite.menu;

import me.quasindro.modreqlite.menu.menus.CancelButton;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public abstract class Menu {

    private MenuManager manager;
    private Inventory inv;
    private String name;
    private int rows;
    private int slots;
    private Map<Integer, Button> buttons;

    public Menu(MenuManager manager, String name, int slots) {
        this.manager = manager;
        this.name = name;
        this.slots = slots;
        rows = (slots > 0) ? (slots + 8)/9 : 0;
        if (rows == 0) {
            throw new IllegalArgumentException("Rows cannot be 0");
        }
        inv = Bukkit.createInventory(null, slots, name);
        buttons = new HashMap<>();
        setItem(8, new CancelButton());
        manager.addMenu(name, this);
    }

    public Button getMenuItem(int index) {
        return buttons.get(index);
    }

    public Inventory getInventory() {
        return inv;
    }

    private void setItem(int index, Button button) {
        buttons.put(index, button);
        inv.setItem(index, button.getItemStack());
    }
}
