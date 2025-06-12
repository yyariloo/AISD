package homework_MyHashMap;

public class MyHashMap<K, V> {

    public static class Node<K, V> {
        Node<K, V> next;
        final int hash;
        final K key;
        V value;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

    }

    private int capacity = 16;
    private final float resizeCondition = 0.75f;
    private Node<K, V>[] table;
    private int size;

    public MyHashMap() {
        this.table = (Node<K, V>[]) new Node[capacity];
    }

    private int hash(K key) {
        return key == null ? 0 : ((Key)key).myHashCode();
    }

    public void resize() {
        int newCapacity = capacity * 2;
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];
        for (Node<K, V> node: table) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
        table = newTable;
        capacity = newCapacity;

    }

    public V put(K key, V value) {
        int hash = hash(key);
        int index = (table.length - 1) & hash;
        Node<K, V> node = table[index];

        if (node == null) {
            table[index] = new Node<K, V>(hash, key, value, null);
            size++;
        } else {
            Node<K, V> prev = null;

            while (node != null) {
                if (node.hash == hash && (node.key != key || ((Key)node.key).myEquals(((Key) key).getKey()))) {
                    V previousValue = node.value;
                    node.value = value;
                    return previousValue;
                }
                prev = node;
                node = node.next;
            }
            prev.next = new Node<>(hash, key, value, null);
            if (size >= capacity * resizeCondition) {
                resize();
            }
        }
        return null;
    }

    public V get(K key) {
        int hash = hash(key);
        int index = (table.length - 1) & hash;
        V result = null;

        for (Node<K, V> node = table[index]; node != null; node = node.next) {
            if (node.hash == hash && (node.key == key || ((Key)node.key).myEquals(((Key)key).getKey()))) {
                result = node.value;
            }
        }
        return result;
    }

    public boolean containsKey(K key) {
        int hash = hash(key);
        int index = (table.length - 1) & hash;
        boolean res = false;

        for (Node<K, V> node = table[index]; node != null; node = node.next) {
            if (hash == node.hash && (key == node.key || ((Key)node.key).myEquals(((Key)key).getKey()))) {
                res = true;
            }
        }
        return res;
    }

    public boolean containsValue(V value) {
        boolean res = false;

        for (int i = 0; i < table.length; i++) {

            for (Node<K, V> node = table[i]; node != null; node = node.next) {

                if ((value == null && node.value == null) || (value != null && value.equals(node.value))) {
                    res = true;

                }

            }

        }
        return res;

    }

    public void remove(K key) {
        int hash = hash(key);
        int index = (table.length - 1) & hash;
        Node<K, V> node = table[index];
        Node<K, V> prev = null;

        while(node != null) {
            if (hash == node.hash && (key == node.key || ((Key)node.key).myEquals(((Key)key).getKey()))) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
            }
            prev = node;
            node = node.next;
        }
    }
}
