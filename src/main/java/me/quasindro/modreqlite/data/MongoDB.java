package me.quasindro.modreqlite.data;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.quasindro.modreqlite.ModReqLite;
import me.quasindro.modreqlite.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class MongoDB extends AData {

    private ModReqLite plugin;
    private MongoCollection tickets;
    private MongoDatabase database;
    private MongoClient client;

    public MongoDB() {
        if (!setup()) {
            plugin.setData(new YML(plugin));
        }
    }

    @Override
    public void createTicket(UUID playerUuid, String playerName, String body, LocalDateTime timestamp) {

    }

    @Override
    public Ticket fetchTicket(int ticketId) {
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
    public void close() {

    }

    protected boolean setup() {
        String address = plugin.getConfig().getString("database.address");
        try {
            client = new MongoClient(address.split(":")[0], Integer.parseInt(address.split(":")[1]));
        } catch (NumberFormatException ex) {
            plugin.getLogger().severe("Incorrect address of the database!");
            return false;
        }
        database = client.getDatabase("modreqlite");
        tickets = database.getCollection("tickets");
        return true;
    }
}
