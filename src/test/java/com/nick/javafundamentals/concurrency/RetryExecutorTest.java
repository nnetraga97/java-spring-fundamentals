package com.nick.javafundamentals.concurrency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code CONC-03}. Remove {@code @Disabled}, then implement
 * {@link RetryExecutor#call} until GREEN.
 */
@Disabled("CONC-03 — remove this line to begin the exercise")
class RetryExecutorTest {

    @Test
    @DisplayName("Succeeds after transient failures, within the attempt budget")
    void retriesUntilSuccess() {
        var executor = new RetryExecutor(3);
        var attempts = new AtomicInteger();

        String result = executor.call(() -> {
            if (attempts.incrementAndGet() < 3) {
                throw new RetryableException("transient");
            }
            return "ok";
        });

        assertThat(result).isEqualTo("ok");
        assertThat(attempts.get()).isEqualTo(3);
    }

    @Test
    @DisplayName("Gives up after maxAttempts and propagates the last failure")
    void stopsAfterMaxAttempts() {
        var executor = new RetryExecutor(3);
        var attempts = new AtomicInteger();

        assertThatThrownBy(() -> executor.call(() -> {
            attempts.incrementAndGet();
            throw new RetryableException("always down");
        })).isInstanceOf(RetryableException.class);

        assertThat(attempts.get()).isEqualTo(3);
    }

    @Test
    @DisplayName("Never retries a non-retryable failure")
    void doesNotRetryNonRetryable() {
        var executor = new RetryExecutor(3);
        var attempts = new AtomicInteger();

        assertThatThrownBy(() -> executor.call(() -> {
            attempts.incrementAndGet();
            throw new NonRetryableException("bad request");
        })).isInstanceOf(NonRetryableException.class);

        assertThat(attempts.get()).isEqualTo(1);
    }
}
