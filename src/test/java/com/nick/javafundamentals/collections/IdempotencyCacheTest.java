package com.nick.javafundamentals.collections;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Reference tests for exercise {@code JAVA-01}. They prove the correct key works,
 * expose the broken-key bug, and document the HashMap contract by example.
 */
class IdempotencyCacheTest {

    @Test
    @DisplayName("Equivalent record keys retrieve the same stored value")
    void equivalentKeysHitTheSameEntry() {
        IdempotencyCache<String> cache = new IdempotencyCache<>();
        IdempotencyKey stored = new IdempotencyKey("merchant-1", "req-abc");

        cache.putIfAbsent(stored, "APPROVED");

        // A different instance with the same components must resolve to the entry.
        IdempotencyKey lookup = new IdempotencyKey("merchant-1", "req-abc");
        assertThat(cache.find(lookup)).contains("APPROVED");
        assertThat(cache.hits()).isEqualTo(1);
    }

    @Test
    @DisplayName("First result wins; a duplicate store replays the original outcome")
    void duplicateStoreReplaysOriginalResult() {
        IdempotencyCache<String> cache = new IdempotencyCache<>();
        IdempotencyKey key = new IdempotencyKey("merchant-1", "req-abc");

        assertThat(cache.putIfAbsent(key, "APPROVED")).isEqualTo("APPROVED");
        // Retry computes a different candidate result, but the original wins.
        assertThat(cache.putIfAbsent(key, "DECLINED")).isEqualTo("APPROVED");
        assertThat(cache.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Missing keys are reported as misses, not empty hits")
    void missingKeyCountsAsMiss() {
        IdempotencyCache<String> cache = new IdempotencyCache<>();

        assertThat(cache.find(new IdempotencyKey("m", "nope"))).isEmpty();
        assertThat(cache.misses()).isEqualTo(1);
        assertThat(cache.hits()).isZero();
    }

    @Test
    @DisplayName("Broken key (no hashCode) loses the value even though equals() is true")
    void brokenKeyExposesTheContractViolation() {
        Map<BrokenKey, String> map = new HashMap<>();
        BrokenKey stored = new BrokenKey("same-value");
        BrokenKey lookup = new BrokenKey("same-value");

        map.put(stored, "APPROVED");

        // The two keys are "equal"...
        assertThat(stored).isEqualTo(lookup);
        // ...but because hashCode() was not overridden, they hash to different
        // buckets and the lookup misses. This is the bug the exercise exposes.
        assertThat(map.get(lookup)).isNull();
        assertThat(map.get(stored)).isEqualTo("APPROVED");
    }
}
