package me.quasindro.modreqlite.data;

import me.quasindro.modreqlite.Ticket;

public abstract class AData {

    public abstract int getLatestId();

    public abstract Ticket getTicket(int ticketId);

    public abstract void addTicket(Ticket ticket);

    public abstract void removeTicket(int ticketId);
}
