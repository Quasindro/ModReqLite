package me.quasindro.modreqlite.constants;

import me.quasindro.modreqlite.ModReqLite;

public class Settings {

    private Settings() {
        throw new AssertionError();
    }

    public static final String STORAGE_METHOD = ModReqLite.getPlugin().getConfig().getString("storage-method");

    public static final String DATABASE_ADDRESS = ModReqLite.getPlugin().getConfig().getString("database.address");
    public static final String DATABASE_SCHEMA = ModReqLite.getPlugin().getConfig().getString("database.schema");
    public static final String DATABASE_USERNAME = ModReqLite.getPlugin().getConfig().getString("database.username");
    public static final String DATABASE_PASSWORD = ModReqLite.getPlugin().getConfig().getString("database.password");
}
