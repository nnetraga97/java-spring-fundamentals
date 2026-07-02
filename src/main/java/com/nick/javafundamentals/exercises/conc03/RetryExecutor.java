package com.nick.javafundamentals.exercises.conc03;

import java.util.function.Supplier;

/**
 * Runs an action with bounded retries (exercise {@code CONC-03}).
 *
 * <p>STUB: implement {@link #call} so that it:
 * <ul>
 *   <li>retries when the action throws {@link RetryableException}, up to
 *       {@code maxAttempts} total attempts;</li>
 *   <li>never retries {@link NonRetryableException} — it propagates on the first
 *       occurrence;</li>
 *   <li>after exhausting attempts, propagates the last {@link RetryableException}.</li>
 * </ul>
 *
 * <p>Real clients also add jitter between attempts and a per-attempt timeout; keep
 * those as extensions once the retry semantics are green. See
 * {@code RetryExecutorTest}.
 */
public class RetryExecutor {

    private final int maxAttempts;

    public RetryExecutor(int maxAttempts) {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be >= 1");
        }
        this.maxAttempts = maxAttempts;
    }

    protected int maxAttempts() {
        return maxAttempts;
    }

    public <T> T call(Supplier<T> action) {
        throw new UnsupportedOperationException(
                "TODO CONC-03: retry RetryableException up to maxAttempts; never retry NonRetryableException");
    }
}
