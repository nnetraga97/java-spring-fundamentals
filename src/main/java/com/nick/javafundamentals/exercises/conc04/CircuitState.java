package com.nick.javafundamentals.exercises.conc04;

/**
 * Circuit breaker states (exercise {@code CONC-04}).
 *
 * <ul>
 *   <li>{@code CLOSED} — calls flow through; failures are counted.</li>
 *   <li>{@code OPEN} — calls fail fast without touching the dependency.</li>
 *   <li>{@code HALF_OPEN} — a single trial call is allowed to test recovery.</li>
 * </ul>
 */
public enum CircuitState {
    CLOSED,
    OPEN,
    HALF_OPEN
}
