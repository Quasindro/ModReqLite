package me.quasindro.modreqlite.data;

import me.quasindro.modreqlite.Ticket;

public class MongoDB extends AData {
    @Override
    public int getLatestId() {
        return 0;
    }

    @Override
    public Ticket getTicket(int ticketId) {
        return null;
    }

    @Override
    public void addTicket(Ticket ticket) {

    }

    @Override
    public void removeTicket(int ticketId) {

    }
}
