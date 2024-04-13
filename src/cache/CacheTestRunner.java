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
        System.out.println("Expected 'value1', got: " + cache.get(1));  // Should output 'value1'

        cache.put(3, "value3");  // According to the placeholder logic, remove key 1
        System.out.println("Expected null, got: " + cache.get(1));  // Should output null
        System.out.println("Expected 'value2', got: " + cache.get(2));  // Should output 'value2'
        System.out.println("Expected 'value3', got: " + cache.get(3));  // Should output 'value3'
    }
}
