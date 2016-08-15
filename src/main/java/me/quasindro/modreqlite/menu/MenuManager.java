package me.quasindro.modreqlite.menu;

import me.quasindro.modreqlite.ModReqLite;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {

    private ModReqLite plugin;
    private Map<String, Menu> menus;

    public MenuManager(ModReqLite plugin) {
        this.plugin = plugin;
        menus = new HashMap<>();
    }

    public void addMenu(String name, Menu menu) {
        menus.put(name, menu);
    }

    public Menu getMenu(String name) {
        return menus.get(name);
    }
}
