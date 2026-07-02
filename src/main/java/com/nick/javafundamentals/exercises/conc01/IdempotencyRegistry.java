package com.nick.javafundamentals.exercises.conc01;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

/**
 * Thread-safe idempotency registry (exercise {@code CONC-01}): for a given key the
 * expensive computation must run <strong>exactly once</strong>, even when many
 * threads race on the same key, and every caller gets the same result.
 *
 * <p>STUB: implement {@link #getOrCompute}. The whole exercise is one insight —
 * "check if present, else compute and store" must be a single atomic step. A plain
 * {@code if (!map.containsKey) map.put(...)} is check-then-act and will run the
 * computation more than once. See {@code IdempotencyRegistryTest}.
 *
 * @param <V> the computed result type
 */
public class IdempotencyRegistry<V> {

    private final ConcurrentMap<String, V> results = new ConcurrentHashMap<>();

    protected ConcurrentMap<String, V> store() {
        return results;
    }

    /** Returns the existing result for {@code key}, or computes and stores it once. */
    public V getOrCompute(String key, Supplier<V> computation) {
        throw new UnsupportedOperationException(
                "TODO CONC-01: make check-and-compute atomic so the supplier runs once per key");
    }
}
