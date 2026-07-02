package com.nick.javafundamentals.exercises.conc04;

/**
 * Thrown when a call is rejected because the circuit is {@code OPEN}
 * (exercise {@code CONC-04}). The dependency is deliberately not invoked.
 */
public class CircuitOpenException extends RuntimeException {

    public CircuitOpenException() {
        super("Circuit is open; call rejected");
    }
}
