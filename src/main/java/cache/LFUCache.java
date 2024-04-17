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
        if (capacity < 0) {
            throw new IllegalArgumentException("Cache capacity cannot be negative.");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.frequencies = new HashMap<>();
        this.minFrequency = 0;
    }

    private static class CacheEntry<K, V> {
        K key;
        V value;
        int frequency;

        CacheEntry(K key, V value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    @Override
    public V get(K key) {
        // Edge case: cache miss
        if (!cache.containsKey(key)) {
            return null;
        }
        CacheEntry<K, V> entry = cache.get(key);
        updateFrequency(entry); // Increment the frequency count of this entry
        return entry.value; // Return the value associated with the key from that entry
    }

    @Override
    public void put(K key, V value) {
        if (capacity == 0) {
            return; // Edge case: initial capacity is 0
        }

        // If key exists, update the value entry
        if (cache.containsKey(key)) {
            CacheEntry<K, V> entry = cache.get(key);
            entry.value = value; // Update the value
            updateFrequency(entry); // Increment the frequency of the entry
            return;
        }

        // Cache is full, evict the Least Frequntly Used item
        if (cache.size() == capacity) {
            evict();
        }

        // New key, so create a new entry
        CacheEntry<K, V> newEntry = new CacheEntry<>(key, value);
        cache.put(key, newEntry);
        addToFrequencyMap(key, 1);  // New keys have initial frequency of 1
        minFrequency = 1; // Whenever there is a new entry, minimum frequency becomes 1
    }

    private void addToFrequencyMap(K key, int frequency) {
        LinkedHashSet<K> set = frequencies.get(frequency);
    // Create a new set for this frequency if it doesn't exist
    if (set == null) {
        set = new LinkedHashSet<>();
        frequencies.put(frequency, set);
    }
    set.add(key); // Add the key to the set for this frequency
    }

    private void updateFrequency(CacheEntry<K, V> entry) {
        // Remove the key from its current frequency set
        int currentFreq = entry.frequency;
        LinkedHashSet<K> currentSet = frequencies.get(currentFreq);
        currentSet.remove(entry.key);

        // Current frequency set is empty but frequency is minFrequency, so increment
        if (currentSet.isEmpty() && currentFreq == minFrequency) {
            minFrequency++;
        }

        entry.frequency++; // Increment the frequency count of the entry
        addToFrequencyMap(entry.key, entry.frequency); // Add it to its new frequency set
    }

    private void evict() {
        LinkedHashSet<K> leastFreqKeys = frequencies.get(minFrequency);
        // Sanity check
        if (leastFreqKeys == null || leastFreqKeys.isEmpty()) {
            return;
        }

        K toEvict = leastFreqKeys.iterator().next(); // Get the next key with this frequency
        leastFreqKeys.remove(toEvict); // Remove it from it's now old frequency set
        cache.remove(toEvict); // Remove the entry from the actual cache

        // If set is now empty, no keys have this frequency
        if (leastFreqKeys.isEmpty()) {
            frequencies.remove(minFrequency); // So, remove this set
            // Find the next minimum frequency
            minFrequency = findNextMinFrequency();
        }
    }

    private int findNextMinFrequency() {
        for (Integer freq : frequencies.keySet()) {
            if (!frequencies.get(freq).isEmpty()) {
                return freq;
            }
        }
        return -1; // A valid minimum frequency was not found
    }

    @Override
    public void remove(K key) {
        if (cache.containsKey(key)) { // Sanity check
            // Remove key from its frequency set
            CacheEntry<K, V> entry = cache.get(key);
            int freq = entry.frequency;
            LinkedHashSet<K> set = frequencies.get(freq);
            set.remove(key);

            // If set is now empty, no keys have this frequency
            if (set.isEmpty()) {
                frequencies.remove(freq);
                // If this freq was the minimum, find the next minimum frequency
                if (freq == minFrequency) {
                    minFrequency = findNextMinFrequency();  // Find new min frequency
                }
            }

            // Remove the entry from the original cache
            cache.remove(key);
        }
    }

    @Override
    public int size() {
        // Return the current number of elements in the cache.
        return cache.size();
    }
}
