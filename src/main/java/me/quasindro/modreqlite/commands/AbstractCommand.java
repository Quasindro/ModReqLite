package me.quasindro.modreqlite.commands;

import org.bukkit.entity.Player;

abstract class AbstractCommand {

    public abstract void execute(Player p, String[] args);
}
