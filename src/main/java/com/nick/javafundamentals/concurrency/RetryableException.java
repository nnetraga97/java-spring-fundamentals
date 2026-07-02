package com.nick.javafundamentals.concurrency;

/**
 * Marks a failure that is safe to retry — a transient downstream hiccup, timeout,
 * or 503 (exercise {@code CONC-03}).
 */
public class RetryableException extends RuntimeException {

    public RetryableException(String message) {
        super(message);
    }
}
