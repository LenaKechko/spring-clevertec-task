package ru.clevertec.proxy.cache.imp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.entity.Animal;
import ru.clevertec.proxy.cache.IBaseCache;
import ru.clevertec.util.AnimalTestData;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


class LFUCacheTest {

    private IBaseCache<UUID, Animal> cache;

    @BeforeEach
    void setUp() {
        cache = new LFUCache<>(3);
    }

    @Test
    void getShouldReturnEmptyById() {
        // given
        UUID id = UUID.randomUUID();
        Optional<Animal> expected = Optional.empty();

        // when
        Optional<Animal> actual = cache.get(id);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void getShouldReturnExistAnimalById() throws NoSuchFieldException, IllegalAccessException {
        // given
        UUID id = AnimalTestData.builder().build().getUuid();
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        Field field = cache.getClass().getDeclaredField("cacheMap");
        field.setAccessible(true);
        field.set(cache, new HashMap<>(Map.of(id,
                new LFUCache.Node<>(id, AnimalTestData.builder().build().buildAnimal(),1),
                uuid1,
                new LFUCache.Node<>(uuid1, AnimalTestData.builder().withUuid(uuid1).build().buildAnimal(), 2),
                uuid2,
                new LFUCache.Node<>(uuid2, AnimalTestData.builder().withUuid(uuid2).build().buildAnimal(), 3)
        )));
        field.setAccessible(false);
        Optional<Animal> expected = Optional.ofNullable(AnimalTestData.builder().build().buildAnimal());

        // when
        Optional<Animal> actual = cache.get(id);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void putShouldReturnCountOfAnimalInCache() throws NoSuchFieldException, IllegalAccessException {
        // given
        Animal animal = AnimalTestData.builder().build().buildAnimal();
        int expectedSizeMap = 1;

        // when
        cache.put(animal.getId(), animal);
        Field map = cache.getClass().getDeclaredField("cacheMap");

        map.setAccessible(true);
        int actualSizeMap = ((Map<?, ?>) map.get(cache)).size();

        // then
        assertEquals(expectedSizeMap, actualSizeMap);
    }

    @Test
    void putShouldReturnSizeTheSameAsLikeWas() throws NoSuchFieldException, IllegalAccessException {
        // given
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        Field field = cache.getClass().getDeclaredField("cacheMap");
        field.setAccessible(true);
        field.set(cache, new HashMap<>(Map.of(uuid1,
                new LFUCache.Node<>(uuid1, AnimalTestData.builder().withUuid(uuid1).build().buildAnimal(), 1),
                uuid2,
                new LFUCache.Node<>(uuid2, AnimalTestData.builder().withUuid(uuid2).build().buildAnimal(), 2),
                uuid3,
                new LFUCache.Node<>(uuid3, AnimalTestData.builder().withUuid(uuid3).build().buildAnimal(), 3)
        )));
        field = cache.getClass().getDeclaredField("head");
        field.setAccessible(true);
        field.set(cache, new LFUCache.Node<>(uuid1, AnimalTestData.builder().withUuid(uuid1).build().buildAnimal(), 1));
        Animal animal = AnimalTestData.builder().build().buildAnimal();
        int expectedSizeMap = 3;

        // when
        cache.put(animal.getId(), animal);
        Field map = cache.getClass().getDeclaredField("cacheMap");

        map.setAccessible(true);
        int actualSizeMap = ((Map<?, ?>) map.get(cache)).size();

        // then
        assertEquals(expectedSizeMap, actualSizeMap);
    }

    @Test
    void removeShouldReturnThatMapEmpty() throws NoSuchFieldException, IllegalAccessException {
        // given
        UUID id = AnimalTestData.builder().build().getUuid();
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        Field field = cache.getClass().getDeclaredField("cacheMap");
        field.setAccessible(true);
        field.set(cache, new HashMap<>(Map.of(id,
                new LFUCache.Node<>(id, AnimalTestData.builder().build().buildAnimal(), 1),
                uuid1,
                new LFUCache.Node<>(uuid1, AnimalTestData.builder().withUuid(uuid1).build().buildAnimal(), 2),
                uuid2,
                new LFUCache.Node<>(uuid2, AnimalTestData.builder().withUuid(uuid2).build().buildAnimal(), 3)
        )));
        field.setAccessible(false);
        int expectedSizeAfterRemove = 2;

        // when
        cache.remove(id);
        Field map = cache.getClass().getDeclaredField("cacheMap");

        map.setAccessible(true);
        int actualSizeAfterRemove = ((Map<?, ?>) map.get(cache)).size();

        // then
        assertEquals(expectedSizeAfterRemove, actualSizeAfterRemove);

    }
}