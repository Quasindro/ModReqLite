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

public class YML extends Data {

    private ModReqLite plugin;
    private YamlFile yamlConfig;
    //private YamlFile yamlTemp;

    public YML(ModReqLite plugin) {
        this.plugin = plugin;
        if (!setup()) {
            plugin.getLogger().severe("There was a problem while enabling the last-resort storage method, YML. Disabling the plugin.");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
        //redoIds();
    }

    @Override
    public void close() {
        //yamlTemp.getFile().delete();
    }

    @Override
    public void createTicket(UUID playerUuid, String playerName, String body, LocalDateTime date) {
        date = date.withNano(0);
        String id = yamlConfig.getConfig().getKeys(false).size() + 1 + "";
        //if (yamlConfig.getConfig().isConfigurationSection(id)) {
        //    redoIds();
        //}
        yamlConfig.getConfig().createSection(id);
        yamlConfig.getConfig().set(id + ".UUID", playerUuid.toString());
        yamlConfig.getConfig().set(id + ".name", playerName);
        yamlConfig.getConfig().set(id + ".body", body);
        yamlConfig.getConfig().set(id + ".date", date.toString());
        try {
            yamlConfig.getConfig().save(yamlConfig.getFile());
        } catch (IOException e) {
            e.printStackTrace(); // im lazy
        }
    }

    @Override
    public Ticket fetchTicket(int ticketId) {
        for (String section : yamlConfig.getConfig().getKeys(false)) {
            int id;

            try {
                id = Integer.parseInt(section);
            } catch (NumberFormatException e) {
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
        yamlConfig = setupFile("data.yml");
        return yamlConfig != null;
    }

    private YamlFile setupFile(String fileName) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("There was a problem creating a " + fileName + " file!");
                e.printStackTrace();
                return null;
            }
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return new YamlFile(file, config);
    }

    /*private void redoIds() {
        int before = 0;
        int cache = 1;
        yamlTemp = setupFile("temp.yml");
        for (String section : yamlConfig.getConfig().getKeys(false)) {
            try {
                cache = Integer.parseInt(section);
            } catch (NumberFormatException e) {
                if (!yamlConfig.getConfig().isConfigurationSection(cache + "")) {
                    yamlConfig.getConfig().createSection(cache + "");
                    yamlConfig.getConfig().set(cache + ".UUID", yamlConfig.getConfig().get(section + ".UUID"));
                    yamlConfig.getConfig().set(cache + ".name", yamlConfig.getConfig().get(section + ".name"));
                    yamlConfig.getConfig().set(cache + ".body", yamlConfig.getConfig().get(section + ".body"));
                    yamlConfig.getConfig().set(cache + ".timestamp", yamlConfig.getConfig().get(section + ".timestamp"));
                    yamlConfig.getConfig().set(section, null);
                    try {
                        yamlConfig.getConfig().save(yamlConfig.getFile());
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                    System.out.println("isnt a configurationsection");
                } else {
                    // utilize the tempfile
                    yamlTemp.getConfig().createSection(cache + "");
                    yamlTemp.getConfig().set(cache + ".UUID", yamlConfig.getConfig().get(section + ".UUID"));
                    yamlTemp.getConfig().set(cache + ".name", yamlConfig.getConfig().get(section + ".name"));
                    yamlTemp.getConfig().set(cache + ".body", yamlConfig.getConfig().get(section + ".body"));
                    yamlTemp.getConfig().set(cache + ".timestamp", yamlConfig.getConfig().get(section + ".timestamp"));
                    yamlConfig.getConfig().set(section, null);
                    try {
                        yamlConfig.getConfig().save(yamlConfig.getFile());
                        yamlTemp.getConfig().save(yamlTemp.getFile());
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                    System.out.println("is a configurationsection");
                }
            }
            if (cache == before + 1) {

            } else {

            }
        }
        plugin.getLogger().info("Claim IDs redone.");
    }*/

    private class YamlFile {

        private File file;
        private FileConfiguration config;

        private YamlFile(File file, FileConfiguration config) {
            this.file = file;
            this.config = config;
        }

        private File getFile() {
            return file;
        }

        private FileConfiguration getConfig() {
            return config;
        }
    }

}
