package ru.clevertec.proxy.cache.imp;

import lombok.Getter;
import ru.clevertec.proxy.cache.IBaseCache;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Класс для реализации  кэша по алгоритмы LRU
 *
 * @author Кечко Елена
 */
public class LRUCache<K, V> implements IBaseCache<K, V> {

    /**
     * Поле размера кэша
     */
    private final Integer capacity;
    /**
     * Содержимое кэша
     */
    private final Map<K, Node<K, V>> cacheMap;

    public LRUCache(Integer capacity) {
        this.capacity = capacity;
        cacheMap = new HashMap<>();
    }

    /**
     * Метод нахождения элемента в кэше по ключу
     *
     * @param key сущность
     * @return Optional сущности, если элемента нет в кэше - Optinal.empty
     */
    @Override
    public Optional<V> get(K key) {
        Node<K, V> node = cacheMap.get(key);
        if (node == null)
            return Optional.empty();
        node.timeLastUsing = LocalDateTime.now();
        return Optional.ofNullable(node.value);
    }

    /**
     * Метод сохранения/изменения элемента в кэше по ключу
     *
     * @param key   сущности
     * @param value значение сущности
     */
    @Override
    public void put(K key, V value) {
        if (cacheMap.containsKey(key)) {
            Node<K, V> node = cacheMap.get(key);
            node.value = value;
            node.timeLastUsing = LocalDateTime.now();
        } else {
            if (cacheMap.size() >= capacity) {
                K removeKey = cacheMap.entrySet().stream()
                        .min(Comparator.comparing(el -> el.getValue().timeLastUsing))
                        .orElseThrow()
                        .getKey();
                cacheMap.remove(removeKey);
            }
            Node<K, V> newNode = new Node<>(key, value, LocalDateTime.now());
            cacheMap.put(key, newNode);
        }
    }

    /**
     * Метод удаления элемента из кэше по ключу
     *
     * @param key сущности
     */
    @Override
    public void remove(K key) {
        cacheMap.remove(key);
    }

    /**
     * Класс для элементов соответствующих ключу в кэше.
     * Содержат последнее время обращение к элементу
     */
    @Getter
    protected static class Node<K, V> {

        K key;
        V value;
        LocalDateTime timeLastUsing;

        public Node(K key, V value, LocalDateTime timeLastUsing) {
            this.key = key;
            this.value = value;
            this.timeLastUsing = timeLastUsing;
        }
    }

}
