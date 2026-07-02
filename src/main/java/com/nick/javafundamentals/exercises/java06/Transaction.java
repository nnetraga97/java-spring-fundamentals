package com.nick.javafundamentals.exercises.java06;

/**
 * A minimal transaction used by the streams-vs-loops exercise ({@code JAVA-06}).
 *
 * @param merchantId the merchant the transaction belongs to
 * @param amountCents the amount in minor units (avoids floating point money bugs)
 */
public record Transaction(String merchantId, long amountCents) {
}
