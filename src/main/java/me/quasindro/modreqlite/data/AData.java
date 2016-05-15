package me.quasindro.modreqlite.data;

import me.quasindro.modreqlite.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AData {

    public abstract void createTicket(UUID playerUuid, String playerName, String body, LocalDateTime timestamp);

    public abstract Ticket fetchTicket(int ticketId);

    public abstract Ticket[] fetchTickets(UUID playerUuid);

    public abstract Ticket[] fetchTickets(LocalDate date);

    public abstract void close();

    protected abstract boolean setup();
}
