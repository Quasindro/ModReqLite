package me.quasindro.modreqlite;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {

    private int id;
    private UUID playerUuid;
    private String playerName;
    private String body;
    private LocalDateTime timestamp;

    public Ticket(int id, UUID playerUuid, String playerName, String body, LocalDateTime timestamp) {
        this.id = id; // SAM POWINIEN SIE DOWIEDZIEC JAKIE JEST OSTATNIE ID
        this.playerUuid = playerUuid;
        this.playerName = playerName;
        this.body = body;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
