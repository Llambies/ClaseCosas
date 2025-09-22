package com.adrian.tema01.mapeoManual;

import java.util.ArrayList;
import java.util.List;

public class Mapeador<K, V> {

    Object[] baser = new Object[256];


    public ArrayList<Integer> calcularIndices(K key) {
        int hash = key.hashCode();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        while (hash > 256) {
            indices.add(hash % 256);
            hash = hash / 256;
        }
        indices.add(hash);
        indices.add(0);
        return indices;
    }

    public void agregar(K key, V value) {
        ArrayList<Integer> indices = calcularIndices(key);
        insertar(indices, 0, value, baser);
    }

    void insertar(ArrayList<Integer> key, int keyActual, V value, Object[] lista) {
        if (keyActual == key.size() - 1) {
            lista[key.get(keyActual)] = value;
            return;
        }

        if (lista[key.get(keyActual)] == null) {
            lista[key.get(keyActual)] = new Object[256];
        }

        insertar(key, keyActual + 1, value, (V[]) lista[key.get(keyActual)]);
    }

    public V buscar(K key) {
        ArrayList<Integer> indices = calcularIndices(key);
        return obtener(indices, 0, baser);

    }

    V obtener(ArrayList<Integer> key, int keyActual, Object[] lista) {

        if (lista[key.get(keyActual)] == null) {
            return null;
        }
        if (keyActual == key.size() - 1) {
            return (V) lista[key.get(keyActual)];
        } else {
            return obtener(key, keyActual + 1, (Object[]) lista[key.get(keyActual)]);
        }

    }

}
