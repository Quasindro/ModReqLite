package me.quasindro.modreqlite.listeners;

import me.quasindro.modreqlite.menu.Button;
import me.quasindro.modreqlite.menu.Menu;
import me.quasindro.modreqlite.menu.MenuManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private MenuManager manager;

    public InventoryClickListener(MenuManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }

        Menu menu = manager.getMenu(e.getClickedInventory().getName());

        if (menu == null) {
            return;
        }

        e.setCancelled(true);

        Button button = menu.getMenuItem(e.getRawSlot());
        if (button != null) {
            button.execute((Player) e.getWhoClicked());
        }
    }
}
