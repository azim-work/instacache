package cache;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CacheTest {

    private LFUCache<Integer, String> cache;

    /* Create a cache with capacity 2 for each test */
    @Before
    public void setUp() {
        cache = new LFUCache<>(2);
    }

    /* Test Basic Operations */
    @Test
    public void shouldReturnNullWhenRetrievingNonexistentKey() {
        assertNull("Expect null when key not found", cache.get(999));
    }

    @Test
    public void shouldStoreAndRetrieveValidEntry() {
        cache.put(101, "AuthToken:XYZ123;User:Admin;Permissions:Full");
        assertEquals("AuthToken:XYZ123;User:Admin;Permissions:Full", cache.get(101));
    }

    @Test
    public void shouldSuccessfullyRemoveEntry() {
        cache.put(102, "AuthToken:ABC456;User:Guest;Permissions:Read");
        cache.remove(102);
        assertNull("Expect null after key is removed", cache.get(102));
    }

    @Test
    public void shouldReplaceOldValueWhenSameKeyInsertedAgain() {
        cache.put(103, "AuthToken:OLD123;User:User1;Permissions:Read");
        cache.put(103, "AuthToken:NEW456;User:User1;Permissions:Read");
        assertEquals("AuthToken:NEW456;User:User1;Permissions:Read", cache.get(103));
    }

    @Test
    public void shouldUpdateCacheSizeCorrectly() {
        assertEquals("Cache size initially is 0", 0, cache.size());
        cache.put(103, "SessionID:789DEF;User:Developer;Status:Active");
        cache.put(104, "SessionID:101GHI;User:Analyst;Status:Inactive");
        assertEquals("Cache should report two entries after additions", 2, cache.size());
        cache.remove(103);
        assertEquals("Cache should report one entry after one removal", 1, cache.size());
    }

    /* Test Eviction Policy */
    @Test
    public void shouldEvictLeastFrequentlyUsedEntryOnCapacityOverflow() {
        cache.put(1, "User1:Data");
        cache.put(2, "User2:Data");
        // Increase User1's data access frequency
        cache.get(1);
        cache.get(1);
        // Add another entry, capacity is reached, so User2's data should be evicted
        cache.put(3, "User3:Data");
        // Assert User2's data was evicted
        assertNull("User2's data should be evicted", cache.get(2));
        assertNotNull("User1's data should remain", cache.get(1));
        assertNotNull("User3's data should be added", cache.get(3));
    }

    // Test tie breaker - multiple entries have the same frequency
    @Test
    public void shouldEvictOldestEntryWhenFrequenciesAreEqual() {
        // Bother User4 and User5 have the same frequency
        cache.put(4, "User4:Data");
        cache.put(5, "User5:Data");  // Both entries have frequency of 1

        // Add another entry, capacity is reached, User4 should be evicted due
        cache.put(6, "User6:Data");  // Should evict the oldest item with lowest frequency

        assertNull("User4's data should be evicted", cache.get(4));
        assertNotNull("User5's data should remain", cache.get(5));
        assertNotNull("User6's data should be added", cache.get(6));
    }

    /* Test Frequency Management */
    @Test
    public void shouldCorrectlyIncrementFrequencyOnGet() {
        cache.put(201, "User201:Data");
        // Frequency should be 3 for User201 after 1 put and 2 get calls
        cache.get(201);
        cache.get(201);
        // Add more items, so capacity is reached, and an entry is evicted
        cache.put(202, "User202:Data");
        cache.put(203, "User203:Data");

        // User201's data should not be evicted
        assertNotNull("Data201 should not be evicted, as it has higher frequency", cache.get(201));
        // User202's data, although more recent than User 201's data, should evicted
        assertNull("Data202 should be evicted as it has the least frequency", cache.get(202));
    }

    /* Test Edge Cases */
    @Test
    public void shouldHandleNullValuesInserted() {
        cache.put(105, null);
        assertNull("Expect null", cache.get(105));
    }

    @Test
    public void shouldNotAddEntriesWhenCapacityIsZero(){
        LFUCache<Integer, String> cache = new LFUCache<>(0);
        cache.put(1, "User1:Data");
        assertNull("No entries exist in a cache with capacity 0", cache.get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForNegativeCacheCapacity() {
        new LFUCache<Integer, String>(-1);
    }

    @Test
    public void shouldNotEvictEntriesWithLargeFrequency() {
        cache.put(1, "StableData");
        cache.put(2, "VolatileData");
        // Simulating high frequency through rapid accesses
        for (int i = 0; i < 100; i++) {
            cache.get(2);
        }
        // This should trigger eviction
        cache.put(3, "NewData");
        assertNotNull("Large frequency item should not be evicted", cache.get(2));
        assertNull("Low frequency item should be evicted", cache.get(1));
    }

}
