package com.nick.javafundamentals.exercises.patt02;

/**
 * An immutable payment request built through a fluent {@link Builder}
 * (exercise {@code PATT-02}).
 *
 * <p>Why Builder here: the request has required fields, optional fields, and
 * defaults. A telescoping constructor ({@code new PaymentRequest(a, b, c, null,
 * true, null)}) is unreadable and easy to mis-order; setters would make the
 * object mutable and allow half-initialized instances to escape. The builder
 * gives named, order-free assembly and one validation choke point:
 * {@link Builder#build()}.
 *
 * <p>STUB: the builder methods and {@code build()} are yours. The test
 * {@code PaymentRequestTest} is the specification — remove its
 * {@code @Disabled} and make it pass.
 */
public final class PaymentRequest {

    private final String merchantId;
    private final long amountCents;
    private final String currency;
    private final boolean captureImmediately;

    private PaymentRequest(String merchantId, long amountCents, String currency,
            boolean captureImmediately) {
        this.merchantId = merchantId;
        this.amountCents = amountCents;
        this.currency = currency;
        this.captureImmediately = captureImmediately;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String merchantId() {
        return merchantId;
    }

    public long amountCents() {
        return amountCents;
    }

    public String currency() {
        return currency;
    }

    public boolean captureImmediately() {
        return captureImmediately;
    }

    /**
     * Fluent builder. Rules enforced by {@link #build()}, not by the setters:
     * <ul>
     *   <li>{@code merchantId} is required (non-null, non-blank).</li>
     *   <li>{@code amountCents} is required and must be positive.</li>
     *   <li>{@code currency} defaults to {@code "USD"} when not set.</li>
     *   <li>{@code captureImmediately} defaults to {@code true}.</li>
     * </ul>
     * Violations throw {@link IllegalStateException} naming the field, so the
     * caller learns about *all* assembly mistakes at the same place.
     */
    public static final class Builder {

        private Builder() {
        }

        public Builder merchantId(String merchantId) {
            throw new UnsupportedOperationException("TODO PATT-02: remember the value, return this");
        }

        public Builder amountCents(long amountCents) {
            throw new UnsupportedOperationException("TODO PATT-02: remember the value, return this");
        }

        public Builder currency(String currency) {
            throw new UnsupportedOperationException("TODO PATT-02: remember the value, return this");
        }

        public Builder captureImmediately(boolean captureImmediately) {
            throw new UnsupportedOperationException("TODO PATT-02: remember the value, return this");
        }

        /** Validates required fields, applies defaults, constructs the immutable request. */
        public PaymentRequest build() {
            throw new UnsupportedOperationException(
                    "TODO PATT-02: validate merchantId/amountCents, default currency+capture, construct");
        }
    }
}
