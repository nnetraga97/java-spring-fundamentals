package com.nick.javafundamentals.concurrency;

import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * A minimal circuit breaker (exercise {@code CONC-04}).
 *
 * <p>Time is injected as a {@link LongSupplier} (millis) so tests can advance the
 * clock deterministically instead of sleeping — this is a deliberate design choice
 * worth being able to explain.
 *
 * <p>STUB: implement {@link #call}. Expected behavior:
 * <ul>
 *   <li>{@code CLOSED}: run the action; success resets the failure count; on the
 *       {@code failureThreshold}-th consecutive failure, trip to {@code OPEN} and
 *       record the time.</li>
 *   <li>{@code OPEN}: if less than {@code openMillis} has elapsed, throw
 *       {@link CircuitOpenException} without calling the action; once elapsed, move
 *       to {@code HALF_OPEN} and allow one trial.</li>
 *   <li>{@code HALF_OPEN}: a successful trial closes the circuit; a failing trial
 *       re-opens it.</li>
 * </ul>
 * See {@code CircuitBreakerTest}.
 */
public class CircuitBreaker {

    private final int failureThreshold;
    private final long openMillis;
    private final LongSupplier clock;

    private CircuitState state = CircuitState.CLOSED;

    public CircuitBreaker(int failureThreshold, long openMillis, LongSupplier clock) {
        if (failureThreshold < 1) {
            throw new IllegalArgumentException("failureThreshold must be >= 1");
        }
        this.failureThreshold = failureThreshold;
        this.openMillis = openMillis;
        this.clock = clock;
    }

    public CircuitState state() {
        return state;
    }

    protected int failureThreshold() {
        return failureThreshold;
    }

    protected long openMillis() {
        return openMillis;
    }

    protected long now() {
        return clock.getAsLong();
    }

    protected void setState(CircuitState next) {
        this.state = next;
    }

    public <T> T call(Supplier<T> action) {
        throw new UnsupportedOperationException(
                "TODO CONC-04: implement CLOSED/OPEN/HALF_OPEN transitions and fail-fast");
    }
}
