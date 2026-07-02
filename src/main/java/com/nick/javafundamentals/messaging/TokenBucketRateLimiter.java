package com.nick.javafundamentals.messaging;

import java.util.function.LongSupplier;

/**
 * Per-key token-bucket rate limiter (exercise {@code DIST-06}).
 *
 * <p>Time is injected as a millis {@link LongSupplier} so tests advance the clock
 * instead of sleeping. Each key gets its own bucket that starts full, drains one
 * token per {@link #tryAcquire}, and refills at {@code refillPerSecond} up to
 * {@code capacity}.
 *
 * <p>STUB: implement {@link #tryAcquire}. To make this correct across service
 * instances you would keep the bucket state in a shared store (e.g. Redis); that
 * is the distributed extension discussed in the exercise. See
 * {@code TokenBucketRateLimiterTest}.
 */
public class TokenBucketRateLimiter {

    private final long capacity;
    private final long refillPerSecond;
    private final LongSupplier clockMillis;

    public TokenBucketRateLimiter(long capacity, long refillPerSecond, LongSupplier clockMillis) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        }
        this.capacity = capacity;
        this.refillPerSecond = refillPerSecond;
        this.clockMillis = clockMillis;
    }

    protected long capacity() {
        return capacity;
    }

    protected long refillPerSecond() {
        return refillPerSecond;
    }

    protected long now() {
        return clockMillis.getAsLong();
    }

    /** @return {@code true} if a token was available and consumed for {@code key} */
    public boolean tryAcquire(String key) {
        throw new UnsupportedOperationException(
                "TODO DIST-06: refill based on elapsed time, then consume a token if available");
    }
}
