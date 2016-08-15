package me.quasindro.modreqlite.menu.menus;

import me.quasindro.modreqlite.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CancelButton extends Button {

    public CancelButton() {
        super(Material.BARRIER, "&c&lLeave menu", "&7Click here to close this menu.");
    }

    @Override
    public void execute(Player p) {
        p.sendMessage("Executed!");
    }
}
