package com.nick.javafundamentals.exercises.conc04;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code CONC-04}. Time is a controllable {@link AtomicLong} so there is
 * no sleeping and no flakiness. Remove {@code @Disabled}, then implement
 * {@link CircuitBreaker#call} until GREEN.
 */
@Disabled("CONC-04 — remove this line to begin the exercise")
class CircuitBreakerTest {

    private final AtomicLong clock = new AtomicLong(0);
    private final AtomicInteger invocations = new AtomicInteger();

    private CircuitBreaker breaker() {
        return new CircuitBreaker(2, 1_000, clock::get);
    }

    private <T> T failing(CircuitBreaker cb) {
        return cb.call(() -> {
            invocations.incrementAndGet();
            throw new RuntimeException("downstream down");
        });
    }

    @Test
    @DisplayName("Opens after the failure threshold is reached")
    void opensAfterThreshold() {
        var cb = breaker();

        assertThatThrownBy(() -> failing(cb)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> failing(cb)).isInstanceOf(RuntimeException.class);

        assertThat(cb.state()).isEqualTo(CircuitState.OPEN);
    }

    @Test
    @DisplayName("While open, calls fail fast without touching the dependency")
    void openCircuitFailsFast() {
        var cb = breaker();
        assertThatThrownBy(() -> failing(cb)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> failing(cb)).isInstanceOf(RuntimeException.class);
        int invocationsAfterTrip = invocations.get();

        assertThatThrownBy(() -> cb.call(() -> {
            invocations.incrementAndGet();
            return "should not run";
        })).isInstanceOf(CircuitOpenException.class);

        assertThat(invocations.get()).isEqualTo(invocationsAfterTrip); // action never ran
    }

    @Test
    @DisplayName("After the cooldown, a successful trial closes the circuit")
    void halfOpenSuccessCloses() {
        var cb = breaker();
        assertThatThrownBy(() -> failing(cb)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> failing(cb)).isInstanceOf(RuntimeException.class);

        clock.addAndGet(1_000); // cooldown elapses -> HALF_OPEN trial allowed

        String result = cb.call(() -> "recovered");

        assertThat(result).isEqualTo("recovered");
        assertThat(cb.state()).isEqualTo(CircuitState.CLOSED);
    }
}
