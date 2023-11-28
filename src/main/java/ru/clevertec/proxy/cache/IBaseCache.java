package ru.clevertec.proxy.cache;

import java.util.Optional;

public interface IBaseCache<K, V> {

    Optional<V> get(K key);

    void put(K key, V value);

    void remove(K key);

}
