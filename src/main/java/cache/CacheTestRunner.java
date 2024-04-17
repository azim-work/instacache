package cache;

/**
 * CacheTestRunner class to run tests for the LFUCache implementation.
 * Demonstrates the functionality of the LFU cache using various test cases.
 */
public class CacheTestRunner {
    public static void main(String[] args) throws InterruptedException {
        LFUCache<Integer, String> cache = new LFUCache<>(3, 5000); // Adding a 5-second expiration time

        System.out.println("\nAdding initial entries...");
        cache.put(1, "Token:XYZ123;User:Admin;Permissions:ReadWrite");
        cache.put(2, "Token:ABC456;User:Guest;Permissions:Read");
        cache.put(3, "Token:DEF789;User:Manager;Permissions:Write");

        System.out.println("\nUpdating token for an existing entry (Guest) to simulate real-time change...");
        cache.put(2, "Token:ABC456;User:Guest;Permissions:All");

        // Access some entries to increment their frequency
        System.out.println("\nAccessing Token for Admin and Manager...");
        cache.get(1);
        cache.get(3);

        // Add another entry which should evict the least frequently used item
        System.out.println("\nAdding new entry for Developer, should trigger eviction...");
        cache.put(4, "Token:GHI012;User:Developer;Permissions:Read");

        // Output the current state of the cache
        System.out.println("\nCurrent Cache Content:");
        System.out.println("Token for Admin: " + cache.get(1)); // Admin has been accessed, should not be evicted
        System.out.println("Token for Guest (should be null): " + cache.get(2)); // Guest SHOULD be evicted as it was the least accessed
        System.out.println("Token for Manager: " + cache.get(3)); // Manager was also accessed, should not be evicted
        System.out.println("Token for Developer: " + cache.get(4)); // Should exist as it was just added

        // Demonstrate time-based invalidation
        System.out.println("\nDemonstrating time-based invalidation by waiting 6 seconds...");
        Thread.sleep(6000); // Waiting longer than the expiration time limit
        System.out.println("Attempting to access all entries after expiration time...");
        System.out.println("Token for Admin (should be null): " + cache.get(1));
        System.out.println("Token for Guest (should be null): " + cache.get(2));
        System.out.println("Token for Manager (should be null): " + cache.get(3));
        System.out.println("Token for Developer (should be null): " + cache.get(4));

        System.out.println("\nAttempting to initialize cache with negative capacity...");
        try {
            LFUCache<Integer, String> invalidCache = new LFUCache<>(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught IllegalArgumentException as expected: " + e.getMessage());
        }
    }
}
