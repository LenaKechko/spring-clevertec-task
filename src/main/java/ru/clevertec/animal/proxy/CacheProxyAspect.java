package ru.clevertec.animal.proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import ru.clevertec.animal.entity.Animal;
import ru.clevertec.animal.proxy.cache.IBaseCache;
import ru.clevertec.animal.proxy.cache.imp.LFUCache;
import ru.clevertec.animal.proxy.cache.imp.LRUCache;
import ru.clevertec.animal.util.LoadPropertyFromFile;

import java.util.Optional;
import java.util.UUID;

@Aspect
public class CacheProxyAspect {

    private IBaseCache<UUID, Animal> cache;

    public CacheProxyAspect() {
        Integer sizeCache = LoadPropertyFromFile.getSizeCache();
        String algorithmCache = LoadPropertyFromFile.getAlgorithm();
        switch (algorithmCache) {
            case "LRU" -> cache = new LRUCache<>(sizeCache);
            case "LFU" -> cache = new LFUCache<>(sizeCache);
        }
    }

    @Around("@annotation(ru.clevertec.animal.proxy.annotation.GetById) && args(id)")
    public Animal get(ProceedingJoinPoint joinPoint, UUID id) throws Throwable {
        System.out.println("Hi!");
        Optional<Animal> result = cache.get(id);
        if (result.isPresent())
            return result.get();
        Animal obj = (Animal) joinPoint.proceed();
        cache.put(obj.getId(), obj);
        return obj;
    }

    @After("@annotation(ru.clevertec.animal.proxy.annotation.Put) && args(animal)")
    public void put(Animal animal) {
        cache.put(animal.getId(), animal);
    }

    @After("@annotation(ru.clevertec.animal.proxy.annotation.Delete) && args(id)")
    public void remove(UUID id) {
        cache.remove(id);
    }
}
