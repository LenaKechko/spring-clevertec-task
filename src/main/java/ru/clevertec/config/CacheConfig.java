package ru.clevertec.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.clevertec.entity.Animal;
import ru.clevertec.proxy.cache.IBaseCache;
import ru.clevertec.proxy.cache.imp.LFUCache;
import ru.clevertec.proxy.cache.imp.LRUCache;

import java.util.Objects;
import java.util.UUID;

@Configuration
@ComponentScan(basePackages = "ru.clevertec.proxy")
@EnableAspectJAutoProxy
@PropertySource("classpath:application.yml")
@Slf4j
public class CacheConfig {

    static {
        System.out.println("CacheConfig");
    }

    @Autowired
    private Environment env;

    @Bean
    public IBaseCache<UUID, Animal> cache() {
        String algorithmCache = env.getProperty("cache.algorithm");
        Integer capacity = Integer.valueOf(Objects.requireNonNull(env.getProperty("cache.size")));
        return switch (Objects.requireNonNull(algorithmCache)) {
            case "LRU" -> new LRUCache<>(capacity);
            case "LFU" -> new LFUCache<>(capacity);
            default -> throw new IllegalStateException("Unexpected value: " + Objects.requireNonNull(algorithmCache));
        };
    }
}
