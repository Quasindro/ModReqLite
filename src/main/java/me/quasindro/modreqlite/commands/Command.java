package me.quasindro.modreqlite.commands;

import org.bukkit.entity.Player;

abstract class Command {

    public abstract void execute(Player p, String[] args);
}
