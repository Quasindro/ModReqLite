package me.quasindro.modreqlite.data;

import com.zaxxer.hikari.HikariDataSource;
import me.quasindro.modreqlite.ModReqLite;
import me.quasindro.modreqlite.Ticket;
import me.quasindro.modreqlite.constants.Settings;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class MySQL extends Data {

    private ModReqLite plugin;
    private HikariDataSource hikariDataSource = new HikariDataSource();

    private static final String INSERT = "INSERT INTO `modreqlite_tickets` (uuid, name, body, date) VALUES(?, ?, ?, ?)";

    public MySQL(ModReqLite plugin) {
        this.plugin = plugin;
        if (!setup()) {
            Bukkit.getLogger().severe("There was a problem during establishing the MySQL connection, falling back to YML.");
            plugin.setData(new YML(plugin));
        }
    }

    @Override
    public void createTicket(UUID playerUuid, String playerName, String body, LocalDateTime timestamp) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection connection = null;

                try {
                    connection = hikariDataSource.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
                    preparedStatement.setString(1, playerUuid.toString());
                    preparedStatement.setString(2, playerName);
                    preparedStatement.setString(3, body);
                    preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
                    preparedStatement.execute();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if(connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    @Override
    public Ticket fetchTicket(int ticketId) {
        return null;
    }

    @Override
    public Ticket[] fetchTickets(UUID playerUuid) {
        return new Ticket[0];
    }

    @Override
    public Ticket[] fetchTickets(LocalDate date) {
        return new Ticket[0];
    }

    @Override
    public void close() {
        if (hikariDataSource != null) {
            hikariDataSource.close();
        }
    }

    protected boolean setup() {
        hikariDataSource.setMaximumPoolSize(10);
        hikariDataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikariDataSource.addDataSourceProperty("serverName", Settings.DATABASE_ADDRESS.split(":")[0]);
        hikariDataSource.addDataSourceProperty("port", Settings.DATABASE_ADDRESS.split(":")[1]);
        hikariDataSource.addDataSourceProperty("databaseName", Settings.DATABASE_SCHEMA);
        hikariDataSource.addDataSourceProperty("user", Settings.DATABASE_USERNAME);
        hikariDataSource.addDataSourceProperty("password", Settings.DATABASE_PASSWORD);

        Connection connection = null;

        try {
            connection = hikariDataSource.getConnection();
            String statement = "CREATE TABLE IF NOT EXISTS `modreqlite_tickets` (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`uuid` varchar(36) NOT NULL," +
                    "`name` varchar(16) NOT NULL," +
                    "`body` varchar(100) NOT NULL, " +
                    "`date` DATE," +
                    "PRIMARY KEY (`id`))" +
                    "ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
}
