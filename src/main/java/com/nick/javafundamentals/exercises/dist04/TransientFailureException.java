package com.nick.javafundamentals.exercises.dist04;

/**
 * A temporary processing failure that should be retried (exercise {@code DIST-04}).
 */
public class TransientFailureException extends RuntimeException {

    public TransientFailureException(String message) {
        super(message);
    }
}
