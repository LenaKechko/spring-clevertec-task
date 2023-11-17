package ru.clevertec.animal.proxy.cache;

import java.util.Optional;

public interface IBaseCache<K, V> {

//    boolean containsKey(K key);

    Optional<V> get(K key);

    void put(K key, V value);

    void remove(K key);
//
//    void clean();
//
//    void clear();

}
