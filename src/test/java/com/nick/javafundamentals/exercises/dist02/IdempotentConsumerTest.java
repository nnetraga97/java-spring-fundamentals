package com.nick.javafundamentals.exercises.dist02;

import com.nick.javafundamentals.messaging.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code DIST-02}. Remove {@code @Disabled}, then implement
 * {@link IdempotentConsumer#process} until GREEN.
 */
@Disabled("DIST-02 — remove this line to begin the exercise")
class IdempotentConsumerTest {

    private final IdempotentConsumer consumer = new IdempotentConsumer();
    private final AtomicInteger sideEffects = new AtomicInteger();

    @Test
    @DisplayName("First delivery runs the handler")
    void firstDeliveryProcesses() {
        boolean processed = consumer.process(new Message("m-1", "pay"), m -> sideEffects.incrementAndGet());

        assertThat(processed).isTrue();
        assertThat(sideEffects.get()).isEqualTo(1);
    }

    @Test
    @DisplayName("Duplicate delivery is skipped, side effect happens once")
    void duplicateIsSkipped() {
        var message = new Message("m-1", "pay");

        consumer.process(message, m -> sideEffects.incrementAndGet());
        boolean second = consumer.process(message, m -> sideEffects.incrementAndGet());

        assertThat(second).isFalse();
        assertThat(sideEffects.get()).isEqualTo(1);
    }

    @Test
    @DisplayName("A failed handler is not marked processed, so a redelivery can retry")
    void failureIsNotRecorded() {
        var message = new Message("m-1", "pay");

        assertThatThrownBy(() -> consumer.process(message, m -> {
            throw new RuntimeException("handler blew up");
        })).isInstanceOf(RuntimeException.class);

        // Redelivery must actually run the handler this time.
        boolean reprocessed = consumer.process(message, m -> sideEffects.incrementAndGet());

        assertThat(reprocessed).isTrue();
        assertThat(sideEffects.get()).isEqualTo(1);
    }
}
