package com.nick.javafundamentals.exercises.patt04;

/**
 * Decorator that retries a failed charge exactly once
 * (exercise {@code PATT-04}; the full-featured retry policy is {@code CONC-03} —
 * here the point is the *shape*, not the policy).
 *
 * <p>STUB: {@link #charge} is yours: call the delegate; on a
 * {@link RuntimeException}, try once more; if the second attempt also fails,
 * let that exception propagate.
 */
public class RetryingGateway implements PaymentGateway {

    private final PaymentGateway delegate;

    public RetryingGateway(PaymentGateway delegate) {
        this.delegate = delegate;
    }

    @Override
    public String charge(String merchantId, long amountCents) {
        throw new UnsupportedOperationException(
                "TODO PATT-04: one retry on RuntimeException, then propagate");
    }
}
