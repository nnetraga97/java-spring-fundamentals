package com.nick.javafundamentals.exercises.onb01;

/**
 * A card number (PAN — Primary Account Number) that can never leak into logs
 * (exercise {@code ONB-01}).
 *
 * <p>The production lesson: in a payments system, logging a full card number is a
 * compliance incident (PCI DSS), not a style issue. The safest fix is a type whose
 * {@code toString()} is already masked, so even a careless
 * {@code log.info("card: {}", pan)} cannot leak the digits.
 *
 * <p>STUB: {@link #of(String)}, {@link #masked()}, {@link #lastFour()} and
 * {@link #toString()} are the parts you implement. The test {@code MaskedPanTest}
 * is the specification — remove its {@code @Disabled} and make it pass.
 */
public final class MaskedPan {

    private final String digits;

    private MaskedPan(String digits) {
        this.digits = digits;
    }

    /**
     * Parses and validates a raw card number.
     *
     * <p>Accepts digits optionally separated by spaces or dashes (people paste
     * card numbers as {@code "4111 1111 1111 1111"}). After removing separators
     * the value must be 12 to 19 digits, otherwise throw
     * {@link IllegalArgumentException}. A {@code null} input is also rejected.
     */
    public static MaskedPan of(String rawPan) {
        throw new UnsupportedOperationException(
                "TODO ONB-01: strip spaces/dashes, validate 12-19 digits, then construct");
    }

    /**
     * The masked form: every digit replaced by {@code '*'} except the last four,
     * e.g. {@code "************1111"} for a 16-digit card.
     */
    public String masked() {
        throw new UnsupportedOperationException("TODO ONB-01: mask all but the last four digits");
    }

    /** The last four digits, e.g. for "which card ending in ...?" support flows. */
    public String lastFour() {
        throw new UnsupportedOperationException("TODO ONB-01: return the last four digits");
    }

    /**
     * Must return the masked form — this is the safety net that makes the type
     * safe to pass to loggers, exceptions, and debuggers.
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("TODO ONB-01: return masked(), never the raw digits");
    }
}
