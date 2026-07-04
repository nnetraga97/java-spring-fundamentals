package com.nick.javafundamentals.exercises.patt01;

/**
 * The Strategy interface for exercise {@code PATT-01}: one way to compute a
 * processing fee. Each card product prices differently, but the checkout code
 * should not care which — it holds a {@code FeePolicy} and calls it.
 */
public interface FeePolicy {

    /** The fee in cents for a payment of {@code amountCents}. */
    long feeCents(long amountCents);
}
