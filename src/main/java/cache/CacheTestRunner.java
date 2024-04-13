package main.java.cache;

/**
 * CacheTestRunner class to run tests for the LFUCache implementation.
 * Demonstrates the functionality of the LFU cache using various test cases.
 */
public class CacheTestRunner {
    public static void main(String[] args) {
        LFUCache<Integer, String> cache = new LFUCache<>(3);

        // Simulate caching of authentication tokens or session data
        cache.put(1, "Token:XYZ123;User:Admin;Permissions:ReadWrite");
        cache.put(2, "Token:ABC456;User:Guest;Permissions:Read");
        cache.put(3, "Token:DEF789;User:Manager;Permissions:Write");

        // Add another entry which should evict the least frequently used item
        cache.put(4, "Token:GHI012;User:Developer;Permissions:Read");

        // Output the current state of the cache
        // Use the placeholder policy for now, to evict the first key
        System.out.println("Current Cache Content:");
        System.out.println("Token for Admin: " + cache.get(1)); // Should be evicted
        System.out.println("Token for Guest: " + cache.get(2)); // Should still exist
        System.out.println("Token for Manager: " + cache.get(3)); // Should still exist
        System.out.println("Token for Developer: " + cache.get(4)); // Should exist
    }
}
