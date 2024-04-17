package cache;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * LFUCache implements a least frequently used (LFU) caching strategy.
 * It uses generic types for keys and values and limits the size of the cache.
 */
public class LFUCache<K, V> implements Cache<K, V> {
    private final int capacity; // Maximum number of entries in the cache
    private Map<K, CacheEntry<K, V>> cache;    // Stores cache entries
    private Map<Integer, LinkedHashSet<K>> frequencies; // Maps frequency to keys with that frequency
    private int minFrequency; // Tracks the smallest frequency of a key in the cache
    /**
     * Constructs an LFUCache with the specified capacity.
     * @param capacity the maximum number of entries the cache can hold
     */
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.frequencies = new HashMap<>();
        this.minFrequency = 0;
    }

    @Override
    public V get(K key) {
        // Return the value for the key if it exists
        return cache.get(key);
    }

    @Override
    public void put(K key, V value) {
        if (cache.size() >= capacity && !cache.containsKey(key)) {
            // Note: Implement eviction logic here
            // Placeholder: Remove the first key
            K firstKey = cache.keySet().iterator().next();
            cache.remove(firstKey);
        }
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        // Remove the item from the cache by key
        cache.remove(key);
    }

    @Override
    public int size() {
        // Return the current number of elements in the cache.
        return cache.size();
    }
}
