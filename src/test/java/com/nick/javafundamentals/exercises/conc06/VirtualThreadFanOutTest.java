package com.nick.javafundamentals.exercises.conc06;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.concurrent.Callable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code CONC-06}. Remove {@code @Disabled}, then implement
 * {@link VirtualThreadFanOut#all} until GREEN.
 */
@Disabled("CONC-06 — remove this line to begin the exercise")
class VirtualThreadFanOutTest {

    private final VirtualThreadFanOut fanOut = new VirtualThreadFanOut();

    @Test
    @DisplayName("Returns all results in input order when every task succeeds")
    void returnsResultsInOrder() {
        List<Callable<Integer>> tasks = List.of(
                () -> 1,
                () -> 2,
                () -> 3);

        assertThat(fanOut.all(tasks)).containsExactly(1, 2, 3);
    }

    @Test
    @DisplayName("Propagates a failure when any task throws")
    void propagatesFailure() {
        List<Callable<Integer>> tasks = List.of(
                () -> 1,
                () -> {
                    throw new IllegalStateException("boom");
                },
                () -> 3);

        assertThatThrownBy(() -> fanOut.all(tasks)).isInstanceOf(RuntimeException.class);
    }
}
