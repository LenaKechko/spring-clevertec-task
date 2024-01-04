package ru.clevertec.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ComponentScan(basePackages = "ru.clevertec",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
                pattern = "ru.clevertec.proxy..*"))
@PropertySource("classpath:application.yml")
@Slf4j
public class DBConfig {

    static {
        System.out.println("DBConfig");
    }

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        log.info("SpringConfig");
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setServerNames(new String[]{env.getProperty("db.serverName")});
        dataSource.setPortNumbers(new int[]{Integer.parseInt(Objects.requireNonNull(env.getProperty("db.port")))});
        dataSource.setDatabaseName(env.getProperty("db.databaseName"));
        return dataSource;
    }

    @SneakyThrows
    @Bean
    public SpringLiquibase migrationDB() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setShouldRun(Boolean.parseBoolean(env.getProperty("liquibase.enable")));
        liquibase.setChangeLog(env.getProperty("liquibase.change-log"));
        liquibase.setDataSource(dataSource());
        return liquibase;
    }
}
