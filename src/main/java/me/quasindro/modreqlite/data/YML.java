package me.quasindro.modreqlite.data;

import me.quasindro.modreqlite.ModReqLite;
import me.quasindro.modreqlite.Ticket;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class YML extends AData {

    private ModReqLite plugin;
    private FileConfiguration config;

    public YML(ModReqLite plugin) {
        this.plugin = plugin;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File file = new File(plugin.getDataFolder(), "data.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public int getLatestId() {
        return 0;
    }

    @Override
    public Ticket getTicket(int ticketId) {
        for (String section : config.getKeys(false)) {
            int id;

            try {
                id = Integer.parseInt(section);
            } catch (NumberFormatException ex) {
                plugin.getLogger().warning(section + " is not an ID, skipping. It could be worth looking into ModReqLite/data.yml files.");
                continue;
            }

            if (id == ticketId) {
                // return new ticket object from config
                return new Ticket(id, UUID.fromString(config.getString(id + ".UUID"), /* etc */));
            }
        }
    }

    @Override
    public void addTicket(Ticket ticket) {
        config.createSection(ticket.getId() + "");
        config.set(ticket.getId() + ".UUID", ticket.getPlayerUuid());
        config.set(ticket.getId() + ".name", ticket.getPlayerName());
        config.set(ticket.getId() + ".body", ticket.getBody());
        config.set(ticket.getId() + ".timestamp", ticket.getTimestamp());
    }

    @Override
    public void removeTicket(int ticketId) {
        config.set(ticketId + "", null);
    }
}
