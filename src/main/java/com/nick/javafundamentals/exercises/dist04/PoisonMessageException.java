package com.nick.javafundamentals.exercises.dist04;

/**
 * A permanent failure — the message can never succeed, so it should go straight to
 * the dead-letter store without wasting retries (exercise {@code DIST-04}).
 */
public class PoisonMessageException extends RuntimeException {

    public PoisonMessageException(String message) {
        super(message);
    }
}
