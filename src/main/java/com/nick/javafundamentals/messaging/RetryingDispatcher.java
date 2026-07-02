package com.nick.javafundamentals.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Retries transient failures and dead-letters what cannot be processed
 * (exercise {@code DIST-04}).
 *
 * <p>STUB: implement {@link #dispatch}. Rules:
 * <ul>
 *   <li>{@link TransientFailureException}: retry up to {@code maxAttempts}; if the
 *       last attempt still fails, record a {@link DeadLetter} with
 *       {@code attempts == maxAttempts} and the failure reason;</li>
 *   <li>{@link PoisonMessageException}: dead-letter immediately with
 *       {@code attempts == 1} (do not retry a poison message);</li>
 *   <li>success: return without dead-lettering.</li>
 * </ul>
 * See {@code RetryingDispatcherTest}.
 */
public class RetryingDispatcher {

    private final int maxAttempts;
    private final List<DeadLetter> deadLetters = new ArrayList<>();

    public RetryingDispatcher(int maxAttempts) {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be >= 1");
        }
        this.maxAttempts = maxAttempts;
    }

    protected int maxAttempts() {
        return maxAttempts;
    }

    protected List<DeadLetter> deadLetterStore() {
        return deadLetters;
    }

    public List<DeadLetter> deadLetters() {
        return List.copyOf(deadLetters);
    }

    public void dispatch(Message message, Consumer<Message> handler) {
        throw new UnsupportedOperationException(
                "TODO DIST-04: retry transient failures, dead-letter exhausted and poison messages");
    }
}
