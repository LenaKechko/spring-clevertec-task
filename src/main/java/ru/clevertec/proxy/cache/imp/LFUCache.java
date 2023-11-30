package ru.clevertec.proxy.cache.imp;

import ru.clevertec.proxy.cache.IBaseCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Класс для реализации  кэша по алгоритмы LFU
 *
 * @author Кечко Елена
 */
public class LFUCache<K, V> implements IBaseCache<K, V> {

    /**
     * Поле размера кэша
     */
    private final Integer capacity;
    /**
     * Содержимое кэша
     */
    private final Map<K, Node<K, V>> cacheMap;
    /**
     * Голова кэша, здесь находится самый редко используемый элемент
     */
    Node<K, V> head;
    /**
     * Хвост кэша, здесь находится самый часто используемый элемент
     */
    Node<K, V> tail;

    public LFUCache(Integer capacity) {
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
        removeNode(node);
        node.frequency += 1;
        addNode(node);
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
            node.frequency += 1;
            removeNode(node);
            addNode(node);
        } else {
            if (cacheMap.size() >= capacity) {
                cacheMap.remove(head.key);
                removeNode(head);
            }
            Node<K, V> newNode = new Node<>(key, value, 1);
            addNode(newNode);
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
        Node<K, V> node = cacheMap.get(key);
        removeNode(node);
        cacheMap.remove(key);
    }

    /**
     * Метод удаления элемента из списка
     *
     * @param node элемент двунаправленного списка
     */
    private void removeNode(Node<K, V> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    /**
     * Метод добавления элемента в списка
     *
     * @param node элемент двунаправленного списка
     */
    private void addNode(Node<K, V> node) {
        if (tail != null && head != null) {
            Node<K, V> temp = head;
            while (temp != null) {
                if (temp.frequency > node.frequency) {
                    if (temp == head) {
                        node.next = temp;
                        temp.prev = node;
                        head = node;
                        break;
                    } else {
                        node.next = temp;
                        node.prev = temp.prev;
                        temp.prev = node;
                        break;
                    }
                } else {
                    temp = temp.next;
                    if (temp == null) {
                        tail.next = node;
                        node.prev = tail;
                        node.next = null;
                        tail = node;
                        break;
                    }
                }
            }
        } else {
            tail = node;
            head = tail;
        }

    }

    /**
     * Класс для организации двунаправленного списка
     */

    protected static class Node<K, V> {

        K key;
        V value;
        int frequency;
        Node<K, V> prev;
        Node<K, V> next;

        public Node(K key, V value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }

}
