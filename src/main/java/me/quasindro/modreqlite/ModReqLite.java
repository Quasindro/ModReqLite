package me.quasindro.modreqlite;

import me.quasindro.modreqlite.commands.CommandManager;
import me.quasindro.modreqlite.constants.Settings;
import me.quasindro.modreqlite.data.Data;
import me.quasindro.modreqlite.data.MongoDB;
import me.quasindro.modreqlite.data.MySQL;
import me.quasindro.modreqlite.data.YML;
import me.quasindro.modreqlite.listeners.InventoryClickListener;
import me.quasindro.modreqlite.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ModReqLite extends JavaPlugin {

    private CommandManager cManager;
    private MenuManager menuManager;
    private Data data;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        switch (Settings.STORAGE_METHOD.toUpperCase()) {
            case "YML": {
                data = new YML(this);
                break;
            }
            case "MYSQL": {
                data = new MySQL(this);
                break;
            }
            case "NOSQL": {
                data = new MongoDB();
                break;
            }
            default: {
                data = null;
                getLogger().severe("Unknown storage method in ModReqLite/config.yml! Disabling the plugin.");
                getPluginLoader().disablePlugin(this);
            }
        }
        cManager = new CommandManager(this);
        menuManager = new MenuManager(this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(menuManager), this);
    }

    @Override
    public void onDisable() {
        data.close();
    }

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("ModReqLite");
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public CommandManager getCommandManager() {
        return cManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }
}
