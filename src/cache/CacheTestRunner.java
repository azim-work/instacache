package cache;

/**
 * CacheTestRunner class to run tests for the LFUCache implementation.
 * Demonstrates the functionality of the LFU cache using various test cases.
 */
public class CacheTestRunner {
    public static void main(String[] args) {
        LFUCache<Integer, String> cache = new LFUCache<>(2);
        cache.put(1, "value1");
        cache.put(2, "value2");
        System.out.println(cache.get(1));  // Output value1
        cache.put(3, "value3");  // Evicts least frequently used
        System.out.println(cache.get(2));  // Output null since it was evicted
    }
}
