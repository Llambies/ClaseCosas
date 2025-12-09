package com.germangascon.ejemplosada.hashmap;

import java.util.*;

/**
 * MyHashMap
 * License: 🅮 Public Domain
 * Created on: 2025-10-01
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class MyHashMap<K, V> {
    private static final int GROW_FACTOR = 2;
    private int size;
    private Node<K,V>[][] data;

    public MyHashMap(int capacity) {
        data = new Node[capacity][capacity];
        size = 0;
    }

    private int hash(K key, Node<K, V>[][] matrix) {
        int hash = key.hashCode();
        hash = hash * 31;
        hash = hash & 0x7FFFFFFF;
        return hash % matrix.length;
    }

    private int hash(K key) {
        return hash(key, data);
    }

    public V put(K key, V value) {
        return put(key, value, data);
    }

    private V put(K key, V value, Node<K,V>[][] matrix) {
        int column = hash(key, matrix);
        int row;
        for (row = 0; row < matrix.length; row++) {
            Node<K, V> node = matrix[row][column];
            if (node == null || node.getKey().equals(key)) {
                break;
            }
        }
        if (row == matrix.length) {
            resize();
        }
        Node<K, V> oldValue = matrix[row][column];
        matrix[row][column] = new Node<>(key, value);
        if (oldValue == null) {
            size++;
        }
        return oldValue == null ? null : oldValue.getValue();
    }

    public V remove(K key) {
        int column = hash(key);
        for (int row = 0; row < data.length; row++) {
            Node<K, V> node = data[row][column];
            if (node == null) {
                return null;
            }
            if (node.getKey().equals(key)) {
                for (int i = row + 1; i < data[row].length; i++) {
                    data[i - 1][column] = data[i][column];
                }
                size--;
                return node.getValue();
            }
        }
        return null;
    }

    public V get(K key) {
        int column = hash(key);
        for (int row = 0; row < data.length; row++) {
            Node<K, V> node = data[row][column];
            if (node == null) {
                return null;
            }
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
        }
        return null;
    }

    public List<K> keys() {
        List<K> keys = new ArrayList<>();
        for (int column = 0; column < data[0].length; column++) {
            for (int row = 0; row < data.length; row++) {
                Node<K, V> node = data[row][column];
                if (node == null) {
                    break;
                }
                keys.add(node.getKey());
            }
        }
        return keys;
    }

    public List<V> values() {
        List<V> values = new ArrayList<>();
        for (int column = 0; column < data[0].length; column++) {
            for (int row = 0; row < data.length; row++) {
                Node<K, V> node = data[row][column];
                if (node == null) {
                    break;
                }
                values.add(node.getValue());
            }
        }
        return values;
    }

    private void resize() {
        Node<K,V>[][] resizedData = new Node[data.length * GROW_FACTOR][data.length * GROW_FACTOR];
        size = 0;
        for (int row = 0; row < data.length; row++) {
            for (int column = 0; column < data[row].length; column++) {
                Node<K, V> node = data[row][column];
                put(node.key, node.value, resizedData);
            }
        }
        data = resizedData;
    }

    @Override
    public String toString() {
        return "MyHashMap{" +
                "size=" + size +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    static class Node<K, V> {
        private final K key;
        private final V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
