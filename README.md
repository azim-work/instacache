# About

This project is a coding assignment for the recruitment as a Software Developer at Arctic Wolf.

Goal: Implementation of an in-memory data structure that acts as a cache.

Programming language: Java.

## Project Structure

- `src/main/java/cache/`: Contains the source files for the LFU cache implementation.
- `src/test/java/cache/`: Contains all JUnit test cases for testing the cache functionality.
- `bin/`: Directory for compiled bytecode files.
- `lib/`: Contains any libraries the project uses (if applicable).
- `README.md`: Provides the project overview, setup, design decisions, assumptions, and usage/testing instructions.

## Implementation Details

- **Methods**:
  - `get(K key)`: Retrieves the value associated with the specified key. If the key does not exist in the cache, the method returns `null`. This method is designed to handle cache misses gracefully without throwing exceptions.
  - `put(K key, V value)`: Inserts a new key-value pair into the cache. If the key already exists, the existing value is replaced with the new value. If inserting a new key-value pair causes the cache to exceed its capacity, the least frequently used item will be evicted.
  - `remove(K key)`: Removes the entry associated with the specified key from the cache. If the key does not exist, the operation has no effect.
  - `size()`: Returns the current number of entries in the cache.
