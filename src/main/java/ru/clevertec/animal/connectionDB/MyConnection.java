package ru.clevertec.animal.connectionDB;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MyConnection {

    static String databaseName = "";

    /**
     * Метод выдающий connection к базе данных
     *
     * @return объект типа Connection
     * @throws RuntimeException при невозможности соединиться с БД
     */
    public static Connection getConnectionDB() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
            String serverName = resourceBundle.getString("db.serverName");
            int port = Integer.parseInt(resourceBundle.getString("db.port"));
            String user = resourceBundle.getString("db.user");
            String password = resourceBundle.getString("db.password");
            if (databaseName.isEmpty())
                databaseName = resourceBundle.getString("db.databaseName");
            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setServerNames(new String[]{serverName});
            dataSource.setPortNumbers(new int[]{port});
            dataSource.setDatabaseName(databaseName);
            dataSource.setUser(user);
            dataSource.setPassword(password);
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setDatabaseName(String name) {
        databaseName = name;
    }
}
