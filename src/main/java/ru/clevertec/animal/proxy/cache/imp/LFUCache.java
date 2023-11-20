package ru.clevertec.animal.proxy.cache.imp;

import ru.clevertec.animal.proxy.cache.IBaseCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LFUCache<K, V> implements IBaseCache<K, V> {

    private final Integer capacity;
    private final Map<K, Node<K, V>> cacheMap;
    Node<K, V> head;
    Node<K, V> tail;

    public LFUCache(Integer capacity) {
        this.capacity = capacity;
        cacheMap = new HashMap<>();
    }

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

    @Override
    public void remove(K key) {
        Node<K, V> node = cacheMap.get(key);
        removeNode(node);
        cacheMap.remove(key);
    }

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
