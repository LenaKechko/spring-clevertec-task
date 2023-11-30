package ru.clevertec.proxy.cache.imp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.entity.Animal;
import ru.clevertec.proxy.cache.IBaseCache;
import ru.clevertec.util.AnimalTestData;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LRUCacheTest {

    private IBaseCache<UUID, Animal> cache;

    @BeforeEach
    void setUp() {
        cache = new LRUCache<>(3);
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
                new LRUCache.Node<>(id, AnimalTestData.builder().build().buildAnimal(), LocalDateTime.now()),
                uuid1,
                new LRUCache.Node<>(uuid1, AnimalTestData.builder().withUuid(uuid1).build().buildAnimal(), LocalDateTime.now()),
                uuid2,
                new LRUCache.Node<>(uuid2, AnimalTestData.builder().withUuid(uuid2).build().buildAnimal(), LocalDateTime.now())
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
                new LRUCache.Node<>(uuid1, AnimalTestData.builder().withUuid(uuid1).build().buildAnimal(), LocalDateTime.of(2023, 11, 18, 0, 0, 0)),
                uuid2,
                new LRUCache.Node<>(uuid2, AnimalTestData.builder().withUuid(uuid2).build().buildAnimal(), LocalDateTime.of(2023, 11, 19, 1, 0, 0)),
                uuid3,
                new LRUCache.Node<>(uuid3, AnimalTestData.builder().withUuid(uuid3).build().buildAnimal(), LocalDateTime.of(2023, 11, 19, 2, 0, 0))
        )));
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
    void putExistingInCacheEntityShouldReturnUpdateCache() throws NoSuchFieldException, IllegalAccessException {
        // given
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        Field field = cache.getClass().getDeclaredField("cacheMap");
        field.setAccessible(true);
        field.set(cache, new HashMap<>(Map.of(uuid1,
                new LRUCache.Node<>(uuid1, AnimalTestData.builder().withUuid(uuid1).build().buildAnimal(), LocalDateTime.of(2023, 11, 18, 0, 0, 0)),
                uuid2,
                new LRUCache.Node<>(uuid2, AnimalTestData.builder().withUuid(uuid2).build().buildAnimal(), LocalDateTime.of(2023, 11, 19, 1, 0, 0)),
                uuid3,
                new LRUCache.Node<>(uuid3, AnimalTestData.builder().withUuid(uuid3).build().buildAnimal(), LocalDateTime.of(2023, 11, 19, 2, 0, 0))
        )));
        Animal animal = AnimalTestData.builder().withUuid(uuid1).build().buildAnimal();
        LocalDateTime unexpectedLocalDateTime = LocalDateTime.of(2023, 11, 18, 0, 0, 0);

        // when
        cache.put(animal.getId(), animal);
        Field map = cache.getClass().getDeclaredField("cacheMap");

        map.setAccessible(true);
        LocalDateTime actualLocalDateTime = ((LRUCache.Node<?, ?>) ((Map<?, ?>) map.get(cache)).get(uuid1)).getTimeLastUsing();

        // then
        assertNotEquals(unexpectedLocalDateTime, actualLocalDateTime);
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
                new LRUCache.Node<>(id, AnimalTestData.builder().build().buildAnimal(), LocalDateTime.now()),
                uuid1,
                new LRUCache.Node<>(uuid1, AnimalTestData.builder().withUuid(uuid1).build().buildAnimal(), LocalDateTime.now()),
                uuid2,
                new LRUCache.Node<>(uuid2, AnimalTestData.builder().withUuid(uuid2).build().buildAnimal(), LocalDateTime.now())
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