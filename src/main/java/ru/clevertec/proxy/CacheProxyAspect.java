package ru.clevertec.proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.entity.Animal;
import ru.clevertec.exception.AnimalNotFoundException;
import ru.clevertec.proxy.cache.IBaseCache;

import java.util.Optional;
import java.util.UUID;

/**
 * Класс реализующий функцию proxy. Прослойка между dao и service
 * Реализована с помощью АОП aspectJ
 *
 * @author Кечко Елена
 */
@Aspect
@Component
public class CacheProxyAspect {

    /**
     * Поле типа кэш. Внедряется бином
     */
    @Autowired
    private IBaseCache<UUID, Animal> cache;

    /**
     * Метод выполняющийся перед поиском по индексу в бд
     * Предварительно ищет в кэше, если информации нет, тогда идет в бд
     *
     * @param joinPoint для продолжения выполнения метода вокруг, которого стоит обертка
     * @param id        идентификатор сущности
     * @return Optinal элемент, если найден, в противном случает - Optinal.empty
     */
    @Around("@annotation(ru.clevertec.proxy.annotation.GetById) && args(id)")
    public Optional<Animal> get(ProceedingJoinPoint joinPoint, UUID id) throws Throwable {
        Optional<Animal> result = cache.get(id);
        if (result.isPresent())
            return result;
        Animal obj = ((Optional<Animal>) joinPoint.proceed()).orElseThrow(() -> new AnimalNotFoundException(id));
        cache.put(obj.getId(), obj);
        return Optional.of(obj);
    }

    /**
     * Метод выполняющийся после сохранения/изменения записи в бд
     * Заносит в кэш новую запись или увеличивает частоту обращения
     * или дату последнего обращения к записи
     *
     * @param animal сущность
     */
    @After("@annotation(ru.clevertec.proxy.annotation.Put) && args(animal)")
    public void put(Animal animal) {
        cache.put(animal.getId(), animal);
    }

    /**
     * Метод выполняющийся после удаления записи в бд
     * Удаляет из кэша запись
     *
     * @param id идентификатор сущности
     */
    @After("@annotation(ru.clevertec.proxy.annotation.Delete) && args(id)")
    public void remove(UUID id) {
        cache.remove(id);
    }
}
