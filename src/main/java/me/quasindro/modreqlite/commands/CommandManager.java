package me.quasindro.modreqlite.commands;

import me.quasindro.modreqlite.ModReqLite;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandManager implements CommandExecutor {

    private ModReqLite plugin;
    private HashMap<String, Command> commandRegistry;

    public CommandManager(ModReqLite plugin) {
        this.plugin = plugin;
        commandRegistry = new HashMap<>();
        registerCommand("modreq", new ModReqCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().warning("Only a player may perform this command!");
            return true;
        }

        if (commandRegistry.containsKey(cmd.getName())) {
            commandRegistry.get(cmd.getName()).execute((Player) sender, args);
        }
        return true;
    }

    private void registerCommand(String command, Command object) {
        commandRegistry.put(command, object);
        plugin.getCommand(command).setExecutor(this);
    }
}
