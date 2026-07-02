package com.nick.javafundamentals.exercises.dist04;

import com.nick.javafundamentals.messaging.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code DIST-04}. Remove {@code @Disabled}, then implement
 * {@link RetryingDispatcher#dispatch} until GREEN.
 */
@Disabled("DIST-04 — remove this line to begin the exercise")
class RetryingDispatcherTest {

    private final RetryingDispatcher dispatcher = new RetryingDispatcher(3);
    private final AtomicInteger handlerCalls = new AtomicInteger();

    @Test
    @DisplayName("Transient failure that recovers is not dead-lettered")
    void recoversWithinBudget() {
        dispatcher.dispatch(new Message("m-1", "x"), m -> {
            if (handlerCalls.incrementAndGet() < 2) {
                throw new TransientFailureException("temporary");
            }
        });

        assertThat(handlerCalls.get()).isEqualTo(2);
        assertThat(dispatcher.deadLetters()).isEmpty();
    }

    @Test
    @DisplayName("Exhausted retries land in the dead-letter store with attempt count")
    void exhaustedIsDeadLettered() {
        dispatcher.dispatch(new Message("m-2", "x"), m -> {
            handlerCalls.incrementAndGet();
            throw new TransientFailureException("always failing");
        });

        assertThat(dispatcher.deadLetters()).hasSize(1);
        DeadLetter dead = dispatcher.deadLetters().get(0);
        assertThat(dead.message().id()).isEqualTo("m-2");
        assertThat(dead.attempts()).isEqualTo(3);
        assertThat(dead.reason()).contains("always failing");
    }

    @Test
    @DisplayName("A poison message is dead-lettered immediately, without retries")
    void poisonIsNotRetried() {
        dispatcher.dispatch(new Message("m-3", "x"), m -> {
            handlerCalls.incrementAndGet();
            throw new PoisonMessageException("cannot ever succeed");
        });

        assertThat(handlerCalls.get()).isEqualTo(1);
        assertThat(dispatcher.deadLetters()).hasSize(1);
        assertThat(dispatcher.deadLetters().get(0).attempts()).isEqualTo(1);
    }
}
