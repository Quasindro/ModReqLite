package me.quasindro.modreqlite;

import me.quasindro.modreqlite.commands.CommandManager;
import me.quasindro.modreqlite.data.AData;
import me.quasindro.modreqlite.data.MongoDB;
import me.quasindro.modreqlite.data.MySQL;
import me.quasindro.modreqlite.data.YML;
import org.bukkit.plugin.java.JavaPlugin;

public class ModReqLite extends JavaPlugin {

    private CommandManager cManager;
    private AData data;
    //placeholder for an update
    @Override
    public void onEnable() {
        saveDefaultConfig();
        switch (getConfig().getString("storage-method").toLowerCase()) {
            case "yml": data = new YML(this); break;
            case "mysql": data = new MySQL(); break;
            case "mongodb": data = new MongoDB(); break;
            default: {
                data = null;
                getLogger().severe("Unknown storage method in ModReqLite/config.yml! Disabling the plugin.");
                getPluginLoader().disablePlugin(this);
            }
        }
        cManager = new CommandManager(this);
    }

    public AData getData() {
        return data;
    }

    public CommandManager getCommandManager() {
        return cManager;
    }
}
