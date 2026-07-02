package com.nick.javafundamentals.collections;

import java.util.Optional;

/**
 * A cache with a fixed maximum size that evicts its oldest entry when full
 * (exercise {@code JAVA-05}). This is the fix for the "unbounded cache grows until
 * the JVM struggles" leak the exercise describes.
 *
 * <p>STUB: implement {@link #put}, {@link #get}, {@link #size}, and
 * {@link #evictionCount}. Eviction policy for this exercise is insertion-order
 * (evict the oldest inserted entry); a {@link java.util.LinkedHashMap} makes this
 * a few lines. See {@code BoundedCacheTest}.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class BoundedCache<K, V> {

    private final int capacity;

    public BoundedCache(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        }
        this.capacity = capacity;
    }

    public int capacity() {
        return capacity;
    }

    public void put(K key, V value) {
        throw new UnsupportedOperationException(
                "TODO JAVA-05: insert; if size would exceed capacity, evict the oldest entry");
    }

    public Optional<V> get(K key) {
        throw new UnsupportedOperationException("TODO JAVA-05: look up the value");
    }

    public int size() {
        throw new UnsupportedOperationException("TODO JAVA-05: current number of entries");
    }

    /** How many entries have been evicted due to capacity over the cache's lifetime. */
    public long evictionCount() {
        throw new UnsupportedOperationException("TODO JAVA-05: count evictions");
    }
}
