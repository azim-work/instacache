package cache;

import java.util.Map;
import java.util.HashMap;

/**
 * LFUCache implements a least frequently used (LFU) caching strategy.
 * It uses generic types for keys and values and limits the size of the cache.
 */
public class LFUCache<K, V> implements Cache<K, V> {
    private final int capacity; // Maximum number of entries in the cache
    private Map<K, V> cache;    // Stores cache entries

    /**
     * Constructs an LFUCache with the specified capacity.
     * @param capacity the maximum number of entries the cache can hold
     */
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    @Override
    public V get(K key) {
        // To be implemented: Retrieve item from cache.
        return null;
    }

    @Override
    public void put(K key, V value) {
        // To be implemented: Insert or update an item in the cache.
    }

    @Override
    public void remove(K key) {
        // To be implemented: Remove an item from the cache by key.
    }

    @Override
    public int size() {
        // Return the current number of elements in the cache.
        return cache.size();
    }
}
