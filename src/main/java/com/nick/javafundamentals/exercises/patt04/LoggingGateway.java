package com.nick.javafundamentals.exercises.patt04;

import java.util.List;

/**
 * Decorator that records one log line per {@code charge} call
 * (exercise {@code PATT-04}).
 *
 * <p>Why Decorator: logging, retries, metrics, and masking are cross-cutting —
 * they apply to *any* {@link PaymentGateway}. Baking them into the real gateway
 * couples unrelated concerns; subclassing fixes the combination at compile
 * time. Decorators wrap the same interface, so they compose in any order at
 * runtime. (Spring builds {@code @Transactional} and {@code @Cacheable} out of
 * exactly this idea — the proxy in {@code SPR-04} is a generated decorator.)
 *
 * <p>STUB: {@link #charge} is yours. Log format (see the test):
 * {@code "charge m-1 500 -> OK(AUTH-1)"} on success,
 * {@code "charge m-1 500 -> FAILED(gateway busy)"} on failure — and the failure
 * still propagates to the caller after being logged.
 */
public class LoggingGateway implements PaymentGateway {

    private final PaymentGateway delegate;
    private final List<String> logSink;

    public LoggingGateway(PaymentGateway delegate, List<String> logSink) {
        this.delegate = delegate;
        this.logSink = logSink;
    }

    @Override
    public String charge(String merchantId, long amountCents) {
        throw new UnsupportedOperationException(
                "TODO PATT-04: delegate, log the outcome, rethrow failures after logging");
    }
}
