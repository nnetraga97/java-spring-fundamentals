package com.nick.javafundamentals.collections;

/**
 * A minimal transaction used by the streams-vs-loops exercise ({@code JAVA-06}).
 *
 * @param merchantId the merchant the transaction belongs to
 * @param amountCents the amount in minor units (avoids floating point money bugs)
 */
public record Transaction(String merchantId, long amountCents) {
}
