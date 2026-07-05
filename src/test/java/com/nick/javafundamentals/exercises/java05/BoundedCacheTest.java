package com.nick.javafundamentals.exercises.java05;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code JAVA-05}. Remove {@code @Disabled}, then implement
 * {@link BoundedCache} until GREEN. Eviction policy: oldest inserted entry leaves
 * first.
 */
class BoundedCacheTest {

    @Test
    @DisplayName("Stores and retrieves values under capacity")
    void storesUnderCapacity() {
        var cache = new BoundedCache<String, String>(2);
        cache.put("a", "1");
        cache.put("b", "2");

        assertThat(cache.size()).isEqualTo(2);
        assertThat(cache.get("a")).contains("1");
        assertThat(cache.get("missing")).isEmpty();
    }

    @Test
    @DisplayName("Never grows beyond capacity; oldest entry is evicted")
    void evictsOldestWhenFull() {
        var cache = new BoundedCache<String, String>(2);
        cache.put("a", "1");
        cache.put("b", "2");
        cache.put("c", "3"); // pushes "a" out

        assertThat(cache.size()).isEqualTo(2);
        assertThat(cache.get("a")).isEmpty();
        assertThat(cache.get("b")).contains("2");
        assertThat(cache.get("c")).contains("3");
        assertThat(cache.evictionCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Stays bounded across many insertions")
    void staysBoundedUnderLoad() {
        var cache = new BoundedCache<Integer, Integer>(10);
        for (int i = 0; i < 1_000; i++) {
            cache.put(i, i);
            assertThat(cache.size()).isLessThanOrEqualTo(10);
        }
        assertThat(cache.size()).isEqualTo(10);
        assertThat(cache.evictionCount()).isEqualTo(990);
    }
}
