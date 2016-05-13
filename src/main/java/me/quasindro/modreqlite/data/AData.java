package me.quasindro.modreqlite.data;

import me.quasindro.modreqlite.Ticket;

public abstract class AData {

    // TODO it shouldn't be like that but at this point I have no idea what to do with it,
    // I guess I will just roll with whatever comes to my mind later
    public abstract int getLatestId();

    public abstract Ticket getTicket(int ticketId);

    public abstract void addTicket(Ticket ticket);

    public abstract void removeTicket(int ticketId);
}
