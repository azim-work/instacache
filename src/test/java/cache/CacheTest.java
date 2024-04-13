package cache;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CacheTest {

    private LFUCache<Integer, String> cache;

    // Create a cache with capacity 2 for each test
    @Before
    public void setUp() {
        cache = new LFUCache<>(2);
    }

    @Test
    public void shouldReturnNullWhenKeyNotFound() {
        assertNull("Expect null when key not found", cache.get(999));
    }

    @Test
    public void shouldStoreAndRetrieveUserAuthenticationToken() {
        cache.put(101, "AuthToken:XYZ123;User:Admin;Permissions:Full");
        assertEquals("AuthToken:XYZ123;User:Admin;Permissions:Full", cache.get(101));
    }

    @Test
    public void shouldRemoveExistingKey() {
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

    // Test for placeholder implementation
    @Test
    public void shouldEvictFirstInsertedItem() {
        cache.put(1, "AuthToken:XYZ123;User:Admin;Permissions:Full");
        cache.put(2, "AuthToken:ABC456;User:Guest;Permissions:Read");
        cache.put(3, "AuthToken:DEF789;User:Manager;Permissions:Modify"); // Should evict key 1 based on current placeholder logic
        assertNull("Expect null since key 1 should be evicted first", cache.get(1));
        assertNotNull("Key 2 should not be evicted", cache.get(2));
        assertNotNull("Key 3 should not be evicted", cache.get(3));
    }

    // Test for an edge case
    @Test
    public void shouldHandleNullValuesInserted() {
        cache.put(105, null);
        assertNull("Expect null", cache.get(105));
    }
}
