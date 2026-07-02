package com.nick.javafundamentals.messaging;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Processes a message at most once even under at-least-once delivery
 * (exercise {@code DIST-02}).
 *
 * <p>STUB: implement {@link #process}. Rules:
 * <ul>
 *   <li>if the message id was already processed, skip it and return {@code false};</li>
 *   <li>otherwise run the handler; only after it succeeds, record the id and return
 *       {@code true};</li>
 *   <li>if the handler throws, do NOT record the id — a later redelivery must be
 *       able to process it. (Think about why recording before vs after the handler
 *       matters.)</li>
 * </ul>
 * See {@code IdempotentConsumerTest}.
 */
public class IdempotentConsumer {

    private final Set<String> processedIds = ConcurrentHashMap.newKeySet();

    protected Set<String> processedIds() {
        return processedIds;
    }

    /** @return {@code true} if the handler ran, {@code false} if this was a duplicate */
    public boolean process(Message message, Consumer<Message> handler) {
        throw new UnsupportedOperationException(
                "TODO DIST-02: skip duplicates; record the id only after the handler succeeds");
    }
}
