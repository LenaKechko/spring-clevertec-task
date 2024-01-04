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

@Configuration
@ComponentScan(basePackages = "ru.clevertec")
@PropertySource(value = "classpath:application.yml")
@Slf4j
public class DBConfig {

    @Autowired
    private BeanFactoryPostProcessor beanFactoryPostProcessor;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;
    @Value("${db.serverName}")
    private String serverName;
    @Value("${db.port}")
    private String port;
    @Value("${db.databaseName}")
    private String databaseName;
    @Value("${liquibase.enable}")
    private String liquibaseEnable;
    @Value("${liquibase.change-log}")
    private String liquibaseChangeLog;

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
