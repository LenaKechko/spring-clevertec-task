package ru.clevertec.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.clevertec.entity.Animal;
import ru.clevertec.proxy.cache.IBaseCache;
import ru.clevertec.proxy.cache.imp.LFUCache;
import ru.clevertec.proxy.cache.imp.LRUCache;

import java.util.Objects;
import java.util.UUID;

/**
 * Конфигурационный класс для работы с кэшом
 */
@Configuration
@ComponentScan(basePackages = "ru.clevertec")
@EnableAspectJAutoProxy
@Slf4j
public class CacheConfig {

    /**
     * Зависимость для работы с данными из application.yml
     */
    @Autowired
    private BeanFactoryPostProcessor beanFactoryPostProcessor;
    /**
     * Поле с размером кэша
     */
    @Value("${cache.size}")
    private Integer cacheSize;
    /**
     * Поле с алгоритмом кэширования. Доступно LFU и LRU
     */
    @Value("${cache.algorithm}")
    private String cacheAlgorithm;

    /**
     * Создание бина для кэша
     *
     * @return объект типа кэш
     */
    @Bean
    public IBaseCache<UUID, Animal> cache() {
        String algorithmCache = cacheAlgorithm;
        Integer capacity = Objects.requireNonNull(cacheSize);
        return switch (algorithmCache) {
            case "LRU" -> new LRUCache<>(capacity);
            case "LFU" -> new LFUCache<>(capacity);
            default -> throw new IllegalStateException("Unexpected value: " + algorithmCache);
        };
    }
}
