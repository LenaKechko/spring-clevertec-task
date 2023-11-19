package ru.clevertec.animal.proxy.cache.imp;

import lombok.Getter;
import ru.clevertec.animal.proxy.cache.IBaseCache;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCache<K, V> implements IBaseCache<K, V> {

    private final Integer capacity;
    private final Map<K, Node<K, V>> cacheMap;

    public LRUCache(Integer capacity) {
        this.capacity = capacity;
        cacheMap = new HashMap<>();
    }

    @Override
    public Optional<V> get(K key) {
        Node<K, V> node = cacheMap.get(key);
        if (node == null)
            return Optional.empty();
        node.timeLastUsing = LocalDateTime.now();
        return Optional.of(node.value);
    }

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

    @Override
    public void remove(K key) {
        cacheMap.remove(key);
    }

    private static class Node<K, V> {

        @Getter
        K key;
        @Getter
        V value;
        LocalDateTime timeLastUsing;

        public Node(K key, V value, LocalDateTime timeLastUsing) {
            this.key = key;
            this.value = value;
            this.timeLastUsing = timeLastUsing;
        }
    }

}
