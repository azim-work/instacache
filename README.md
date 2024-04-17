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

## Library Management

Due to the time constraints of this assignment and the need for a straightforward project setup for evaluation, libraries required by the project (JUnit for testing) are currently included directly in the `lib/` directory. Under normal circumstances, it would be preferable to manage these dependencies through a build tool like Maven or Gradle.

## Future Improvements

**LFU Cache Limitations**

While LFU caching effectively prioritizes frequently accessed data, it still has drawbacks. For example, data accessed intensively over a short period may not be needed again soon, yet its high access frequency prevents its eviction.

Additionally, new items that were just added to the cache may be removed too soon, because the initial frequency count is low, even though they may be potentially accessed more frequently in the future. This situation might cause data that isn't accessed often but is used regularly over time to be removed from the cache too soon.

These challenges suggest that a more effective cache implementation may be integrating hybrid strategies that combine LFU principles with other caching mechanisms to optimize both frequency and recency of data access.

- **Implement Dependency Management**: Post-assignment, plan to transition to using Maven or Gradle for dependency management. This will not only automate the management of library versions and dependencies but also align the project with standard Java project practices.
- **Remove Direct Library Inclusions**: Once a proper dependency management tool is configured, the libraries will be removed from the `lib/` directory and managed exclusively through the build tool's configuration files.
