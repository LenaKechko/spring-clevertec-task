package ru.clevertec.config;

import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionCommandStep;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.config.connection.MySingletonConnection;
import ru.clevertec.util.LoadPropertyFromFile;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;

/**
 * Класс отвечающие за миграцию данных при запуске приложения
 * Для миграции использовалась Liquibase
 * Благодаря аннотации @WebListener миграция запускается при запуске приложения
 */

@WebListener
@Slf4j
public class MigrationDB implements ServletContextListener {

    /**
     * Константа с путем до changelog для liquibase
     */
    private final String FILE_CHANGELOG = LoadPropertyFromFile.getLiquibaseChangelog();

    /**
     * Метод отвечающий за инициализацию данных при запуске приложения
     *
     * @param sce объект для работы с контекстом сервлета
     */
    @SneakyThrows
    public void contextInitialized(ServletContextEvent sce) {
        if (LoadPropertyFromFile.getLiquibaseEnable()) {
            log.info("Start initialize database");

            Connection connection = MySingletonConnection.INSTANCE.getConnectionDB();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
            updateCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database);
            updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, FILE_CHANGELOG);
            updateCommand.execute();

            log.info("Finish initialize database");
        }
    }

}
