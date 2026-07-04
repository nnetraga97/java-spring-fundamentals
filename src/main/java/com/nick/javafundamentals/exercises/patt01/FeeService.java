package com.nick.javafundamentals.exercises.patt01;

/**
 * Selects and applies the right {@link FeePolicy} per card product
 * (exercise {@code PATT-01}).
 *
 * <p>Why Strategy instead of a {@code switch} inside one big method: each
 * policy becomes independently testable, new products mean adding a class
 * instead of editing shared logic (Open/Closed Principle), and pricing rules
 * can be swapped at runtime — e.g. per market.
 *
 * <p>STUB: build the three policies and the selection. The test
 * {@code FeeServiceTest} is the specification — remove its {@code @Disabled}
 * and make it pass. Policies to implement (lambdas or small classes both work):
 *
 * <ul>
 *   <li>{@code DEBIT} — flat 25 cents.</li>
 *   <li>{@code CREDIT} — 2% of the amount, rounded down.</li>
 *   <li>{@code PREMIUM_CREDIT} — 3% of the amount, rounded down, but never less
 *       than 50 cents.</li>
 * </ul>
 */
public class FeeService {

    /** The fee for one payment, delegated to the product's policy. */
    public long feeCents(CardProduct product, long amountCents) {
        throw new UnsupportedOperationException(
                "TODO PATT-01: look up the product's FeePolicy and delegate");
    }
}
