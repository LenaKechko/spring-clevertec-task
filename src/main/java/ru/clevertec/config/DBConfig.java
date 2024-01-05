package ru.clevertec.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * Конфигурационный класс для работы с базой данных
 */
@Configuration
@ComponentScan(basePackages = "ru.clevertec")
@PropertySource(value = "classpath:application.yml")
@Slf4j
public class DBConfig {

    /**
     * Зависимость для работы с данными из application.yml
     */
    @Autowired
    private BeanFactoryPostProcessor beanFactoryPostProcessor;
    /**
     * Имя пользователя для подключение к БД
     */
    @Value("${db.user}")
    private String user;
    /**
     * Пароль пользователя для подключение к БД
     */
    @Value("${db.password}")
    private String password;
    /**
     * Имя сервера
     */
    @Value("${db.serverName}")
    private String serverName;
    /**
     * Порт для доступа к БД
     */
    @Value("${db.port}")
    private String port;
    /**
     * Имя схемы БД
     */
    @Value("${db.databaseName}")
    private String databaseName;
    /**
     * Поле отвечающее на вопрос: необходимо ли мигрировать данные в БД с помощью Liquibase
     */
    @Value("${liquibase.enable}")
    private String liquibaseEnable;
    /**
     * Путь, по которому находятся changeLog'и для Liquibase
     */
    @Value("${liquibase.change-log}")
    private String liquibaseChangeLog;

    /**
     * Бин для подключения к БД
     *
     * @return возвращается DataSource для дальнейшей работы с БД
     */
    @Bean
    public DataSource dataSource() {
        log.info("SpringConfig");
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setServerNames(new String[]{serverName});
        dataSource.setPortNumbers(new int[]{Integer.parseInt(Objects.requireNonNull(port))});
        dataSource.setDatabaseName(databaseName);
        return dataSource;
    }

    /**
     * Бин для накатывания данных в БД
     *
     * @return настроенный объект для миграции данных
     */
    @SneakyThrows
    @Bean
    public SpringLiquibase migrationDB() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setShouldRun(Boolean.parseBoolean(liquibaseEnable));
        liquibase.setChangeLog(liquibaseChangeLog);
        liquibase.setDataSource(dataSource());
        return liquibase;
    }
}
