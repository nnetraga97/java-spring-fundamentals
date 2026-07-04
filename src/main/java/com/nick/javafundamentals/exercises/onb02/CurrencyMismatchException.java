package com.nick.javafundamentals.exercises.onb02;

/**
 * Thrown when arithmetic is attempted across two different currencies
 * (exercise {@code ONB-02}). Adding USD to EUR is a bug, never a rounding detail.
 */
public class CurrencyMismatchException extends RuntimeException {

    public CurrencyMismatchException(String message) {
        super(message);
    }
}
