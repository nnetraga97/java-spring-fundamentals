package com.nick.javafundamentals.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A tiny single-threaded idempotency cache: the first result stored for a key
 * wins, and later stores for the same key are ignored so a retried request
 * replays the original outcome instead of recomputing it.
 *
 * <p>This is the worked reference for exercise {@code JAVA-01}. It deliberately
 * stays single-threaded to keep the focus on the {@link java.util.HashMap} key
 * contract. The thread-safe version (many callers racing on the same key) is a
 * separate exercise, {@code CONC-01}, which reaches for
 * {@code ConcurrentHashMap#computeIfAbsent}.
 *
 * @param <V> the stored result type
 */
public class IdempotencyCache<V> {

    private final Map<IdempotencyKey, V> store = new HashMap<>();

    private long hits;
    private long misses;

    /**
     * Stores {@code result} under {@code key} only if the key is not already
     * present, and returns whatever value the cache holds for the key afterward.
     *
     * <p>Modeled on how a real idempotency layer behaves: the first caller's
     * result is authoritative; a duplicate caller gets that same result back
     * rather than overwriting it.
     *
     * @return the stored value for the key (the existing one if this was a
     *         duplicate, otherwise the value just inserted)
     */
    public V putIfAbsent(IdempotencyKey key, V result) {
        V existing = store.putIfAbsent(key, result);
        return existing != null ? existing : result;
    }

    /**
     * Looks up a previously stored result, tracking hit/miss counts so
     * {@code JAVA-05} has something concrete to reason about.
     */
    public Optional<V> find(IdempotencyKey key) {
        V value = store.get(key);
        if (value != null) {
            hits++;
        } else {
            misses++;
        }
        return Optional.ofNullable(value);
    }

    public int size() {
        return store.size();
    }

    public long hits() {
        return hits;
    }

    public long misses() {
        return misses;
    }
}
