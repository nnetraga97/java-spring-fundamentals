package com.nick.javafundamentals.messaging;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code DIST-06}. The clock is a controllable {@link AtomicLong}, so the
 * test is deterministic. Remove {@code @Disabled}, then implement
 * {@link TokenBucketRateLimiter#tryAcquire} until GREEN.
 */
@Disabled("DIST-06 — remove this line to begin the exercise")
class TokenBucketRateLimiterTest {

    private final AtomicLong clock = new AtomicLong(0);

    // capacity 2, refill 1 token/sec
    private TokenBucketRateLimiter limiter() {
        return new TokenBucketRateLimiter(2, 1, clock::get);
    }

    @Test
    @DisplayName("Allows a burst up to capacity, then denies")
    void allowsBurstThenDenies() {
        var limiter = limiter();

        assertThat(limiter.tryAcquire("merchant-a")).isTrue();
        assertThat(limiter.tryAcquire("merchant-a")).isTrue();
        assertThat(limiter.tryAcquire("merchant-a")).isFalse(); // bucket empty
    }

    @Test
    @DisplayName("Refills over time")
    void refillsOverTime() {
        var limiter = limiter();
        limiter.tryAcquire("merchant-a");
        limiter.tryAcquire("merchant-a");
        assertThat(limiter.tryAcquire("merchant-a")).isFalse();

        clock.addAndGet(1_000); // one second -> one token back

        assertThat(limiter.tryAcquire("merchant-a")).isTrue();
        assertThat(limiter.tryAcquire("merchant-a")).isFalse();
    }

    @Test
    @DisplayName("Buckets are independent per key")
    void keysAreIndependent() {
        var limiter = limiter();
        limiter.tryAcquire("merchant-a");
        limiter.tryAcquire("merchant-a");

        // A different merchant has its own full bucket.
        assertThat(limiter.tryAcquire("merchant-b")).isTrue();
        assertThat(limiter.tryAcquire("merchant-b")).isTrue();
        assertThat(limiter.tryAcquire("merchant-b")).isFalse();
    }
}
