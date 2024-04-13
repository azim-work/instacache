package cache;

/**
 * Interface for a generic cache.
 * Defines basic cache operations.
 */
public interface Cache<K, V> {
    V get(K key);
    void put(K key, V value);
    void remove(K key);
    int size();
}
