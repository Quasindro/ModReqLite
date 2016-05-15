package me.quasindro.modreqlite.data;

import com.zaxxer.hikari.HikariDataSource;
import me.quasindro.modreqlite.ModReqLite;
import me.quasindro.modreqlite.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class MySQL extends AData {

    private ModReqLite plugin;
    private HikariDataSource hikariDataSource;

    public MySQL(ModReqLite plugin) {
        this.plugin = plugin;
        hikariDataSource = new HikariDataSource();
        setup();
    }

    @Override
    public void createTicket(UUID playerUuid, String playerName, String body, LocalDateTime timestamp) {

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
        String address = plugin.getConfig().getString("database.address");
        String database = plugin.getConfig().getString("database.schema");
        String username = plugin.getConfig().getString("database.username");
        String password = plugin.getConfig().getString("database.password");

        hikariDataSource.setMaximumPoolSize(10);
        hikariDataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikariDataSource.addDataSourceProperty("serverName", address.split(":")[0]);
        hikariDataSource.addDataSourceProperty("port", address.split(":")[1]);
        hikariDataSource.addDataSourceProperty("databaseName", database);
        hikariDataSource.addDataSourceProperty("user", username);
        hikariDataSource.addDataSourceProperty("password", password);

        Connection connection = null;

        try {
            connection = hikariDataSource.getConnection();
            String statement = "CREATE TABLE IF NOT EXISTS `modreqlite_tickets` (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`uuid` varchar(36) NOT NULL," +
                    "`name` varchar(16) NOT NULL," +
                    "`body` varchar(100) NOT NULL, " +
                    "`timestamp` timestamp," +
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
