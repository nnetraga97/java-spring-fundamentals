package com.nick.javafundamentals.exercises.onb02;

import java.math.BigDecimal;

/**
 * An amount of money in one currency (exercise {@code ONB-02}).
 *
 * <p>The production lesson: {@code double} cannot represent most decimal amounts
 * exactly — {@code 0.1 + 0.2 != 0.3} in floating point — so real payment systems
 * use {@link BigDecimal} (or integer minor units, i.e. cents). This type wraps
 * that discipline up so callers cannot get it wrong.
 *
 * <p>STUB: {@link #of(String, String)}, {@link #add(Money)} and {@link #times(int)}
 * are the parts you implement. The test {@code MoneyTest} is the specification —
 * remove its {@code @Disabled} and make it pass.
 */
public final class Money {

    private final BigDecimal amount;
    private final String currency;

    private Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Creates a money value from a decimal string like {@code "19.99"} and a
     * currency code like {@code "USD"}.
     *
     * <p>Rules the test enforces:
     * <ul>
     *   <li>{@code null} amount or currency is rejected with
     *       {@link IllegalArgumentException}.</li>
     *   <li>More than two decimal places is rejected — a payments system never
     *       silently rounds money it was given.</li>
     *   <li>The stored amount always has exactly two decimal places, so
     *       {@code "10"} and {@code "10.00"} are the same value (hint:
     *       {@link BigDecimal#setScale(int)}).</li>
     * </ul>
     */
    public static Money of(String amount, String currency) {
        throw new UnsupportedOperationException(
                "TODO ONB-02: parse with new BigDecimal(amount), validate, normalize scale to 2");
    }

    /** Adds two amounts of the same currency; different currencies throw {@link CurrencyMismatchException}. */
    public Money add(Money other) {
        throw new UnsupportedOperationException(
                "TODO ONB-02: check currency matches, then add the BigDecimals");
    }

    /** Multiplies by a whole quantity, e.g. 3 items at 19.99 each. */
    public Money times(int quantity) {
        throw new UnsupportedOperationException(
                "TODO ONB-02: multiply the BigDecimal, keep scale at 2");
    }

    public BigDecimal amount() {
        return amount;
    }

    public String currency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Money other
                && amount.equals(other.amount)
                && currency.equals(other.currency);
    }

    @Override
    public int hashCode() {
        return amount.hashCode() * 31 + currency.hashCode();
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}
