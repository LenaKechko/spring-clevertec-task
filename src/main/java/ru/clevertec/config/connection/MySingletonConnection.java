package ru.clevertec.config.connection;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Реализация шаблона Singleton для выдачи connection с помощью перечисления
 *
 * @author Кечко Елена
 */
public enum MySingletonConnection {
    INSTANCE;

    /**
     * Поле для пула соединений
     */
    private final PGSimpleDataSource dataSource = new PGSimpleDataSource();

    /**
     * Конструктор. Содержит подключение к базе данных.
     * Данные для подключения берутся из properties-файла
     */
    MySingletonConnection() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String serverName = resourceBundle.getString("db.serverName");
        int port = Integer.parseInt(resourceBundle.getString("db.port"));
        String user = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");
        String databaseName = resourceBundle.getString("db.databaseName");
        dataSource.setServerNames(new String[]{serverName});
        dataSource.setPortNumbers(new int[]{port});
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(user);
        dataSource.setPassword(password);
    }

    /**
     * Метод выдающий connection к базе данных
     *
     * @return объект типа Connection
     * @throws RuntimeException при невозможности соединиться с БД
     */
    public Connection getConnectionDB() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
