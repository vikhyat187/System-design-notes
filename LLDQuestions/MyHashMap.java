package org.vikhyat;

import java.util.Map;

public class HashMap<K, V> {
    private static final int INITIAL_SIZE = 1 << 4;
    private static final int MAX_SIZE = 1 << 30;

    Entry[] hashTable;

    HashMap() {
        hashTable = new Entry[INITIAL_SIZE];
    }

    HashMap(int size) {
        int tableSize = tableSizeFor(size);
        hashTable = new Entry[tableSize];
    }

    final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAX_SIZE) ? MAX_SIZE : n + 1;
    }

    class Entry<K, V> {

        K key;
        V value;
        Entry next;

        Entry(K k, V v) {
            key = k;
            value = v;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }


    }
    public void put(K key, V value){
        int hashCode = key.hashCode() % hashTable.length;
        Entry node = hashTable[hashCode];

        if (node == null){
            Entry newNode = new Entry(key, value);
            hashTable[hashCode] = newNode;
        } else{
            Entry prevNode = node;
            while (node != null) {
                if (node.key == key){
                    node.value = value;
                    return;
                }
                prevNode = node;
                node = node.next;
            }
            Entry newNode = new Entry(key, value);
            prevNode.next = newNode;
        }
    }

    public V get(K key){
        int hashCode = key.hashCode() % hashTable.length;

        Entry node = hashTable[hashCode];
        while(node != null){
            if (node.key.equals(key)){
                return (V)node.value;
            }
            node = node.next;
        }
        return null;
    }

    public static void main(String[] args) {
        HashMap<Integer, Integer> results = new HashMap<>();
        System.out.println("The results are " +   results.get(1));
        results.put(1,1);
        System.out.println("The results are " +   results.get(1));
    }
}
