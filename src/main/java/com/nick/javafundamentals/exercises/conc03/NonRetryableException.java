package com.nick.javafundamentals.exercises.conc03;

/**
 * Marks a failure that must NOT be retried — a validation error, 400, or anything
 * where retrying only wastes work or causes harm (exercise {@code CONC-03}).
 */
public class NonRetryableException extends RuntimeException {

    public NonRetryableException(String message) {
        super(message);
    }
}
