package com.nick.javafundamentals.exercises.patt03;

/** An event describing something that happened to a transaction (exercise {@code PATT-03}). */
public record TransactionEvent(String transactionId, String type) {
}
