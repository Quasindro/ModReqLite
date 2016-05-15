package me.quasindro.modreqlite.commands;

import me.quasindro.modreqlite.ModReqLite;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

public class ModReqCommand extends AbstractCommand {

    private ModReqLite plugin;

    public ModReqCommand(ModReqLite plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player p, String[] args) {
        // if args.length > 0 im lazy
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        String body = sb.substring(0, sb.length() - 1);
        plugin.getData().createTicket(p.getUniqueId(), p.getName(), body, LocalDateTime.now());
        p.sendMessage("Sent successfully:");
        p.sendMessage(body);
    }
}
