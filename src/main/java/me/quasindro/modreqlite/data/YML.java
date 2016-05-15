package me.quasindro.modreqlite.data;

import me.quasindro.modreqlite.ModReqLite;
import me.quasindro.modreqlite.Ticket;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class YML extends AData {

    private ModReqLite plugin;
    private FileConfiguration config;
    private File file;

    public YML(ModReqLite plugin) {
        this.plugin = plugin;
        if (!setup()) {
            plugin.getLogger().severe("There was a problem while enabling the last-resort storage method, YML. Disabling the plugin.");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }

    @Override
    public void close() {
        // do nothing
    }

    @Override
    public void createTicket(UUID playerUuid, String playerName, String body, LocalDateTime timestamp) {
        timestamp = timestamp.withNano(0);
        String id = config.getKeys(false).size() + 1 + "";
        config.createSection(id);
        config.set(id + ".UUID", playerUuid.toString());
        config.set(id + ".name", playerName);
        config.set(id + ".body", body);
        config.set(id + ".timestamp", timestamp.toString());
        try {
            config.save(file);
        } catch (IOException ex) {
            ex.printStackTrace(); // im lazy
        }
    }

    @Override
    public Ticket fetchTicket(int ticketId) {
        for (String section : config.getKeys(false)) {
            int id;

            try {
                id = Integer.parseInt(section);
            } catch (NumberFormatException ex) {
                plugin.getLogger().warning(section + " is not an ID, skipping. Perhaps it could be worth looking into ModReqLite/data.yml file before the claims' IDs are redone automatically.");
                continue;
            }

            if (id == ticketId) {
                // return new ticket object from config
                //return new Ticket(id, UUID.fromString(config.getString(id + ".UUID"), /* etc */));
            }
        }
        return null;
    }

    @Override
    public Ticket[] fetchTickets(UUID playerUuid) {
        return new Ticket[0];
    }

    @Override
    public Ticket[] fetchTickets(LocalDate date) {
        return new Ticket[0];
    }

    @Override
    protected boolean setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        file = new File(plugin.getDataFolder(), "data.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                plugin.getLogger().severe("There was a problem creating the data file!");
                ex.printStackTrace();
                return false;
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

        // TODO SCAN FOR ANY INCONSISTENCIES (1,2,6,9,12 ETC)
        // AND MAYBE LOAD THE LATEST ONES TO SOME KIND OF COLLECTION
        return true;
    }

    private void redoIds() {
        int before = 0;
        int cache = 1;
        for (String section : config.getKeys(false)) {
            try {
                cache = Integer.parseInt(section);
            } catch (NumberFormatException ex) {
                if (!config.isConfigurationSection(cache + "")) {
                    config.createSection(cache + "");
                    config.set(cache + ".UUID", config.get(section + ".UUID"));
                    config.set(cache + ".name", config.get(section + ".name"));
                    config.set(cache + ".body", config.get(section + ".body"));
                    config.set(cache + ".timestamp", config.get(section + ".timestamp"));
                    config.set(section, null);
                } else {
                    // probably take a random number between getKeys.size() and getKeys.size()*2 and check if it's not a ConfigurationSection -- then use it as temporary section to move data
                }
            }

            if (before + 1 == cache) {
                // all is good i think, nothing was caught an we can continue
            }
        }
        plugin.getLogger().info("Claim IDs redone.");
    }
}
