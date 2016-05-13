package me.quasindro.modreqlite.commands;

import me.quasindro.modreqlite.ModReqLite;
import me.quasindro.modreqlite.Ticket;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

public class ModReqCommand extends AbstractCommand {

    private ModReqLite plugin;

    public ModReqCommand(ModReqLite plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player p, String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        String body = sb.substring(0, sb.length() - 1);
        int latest = plugin.getData().getLatestId();
        Ticket ticket = new Ticket(latest + 1, p.getUniqueId(), p.getName(), body, LocalDateTime.now().withNano(0));

        plugin.getData().addTicket(ticket);
    }
}
