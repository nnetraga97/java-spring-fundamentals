package com.nick.javafundamentals.exercises.java03;

import com.nick.javafundamentals.domain.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code JAVA-03}. Remove {@code @Disabled}, then implement
 * {@link ValidatorPipeline#validate} until GREEN.
 */
@Disabled("JAVA-03 — remove this line to begin the exercise")
class ValidatorPipelineTest {

    record PaymentCommand(long amountCents, String currency) {
    }

    private static final Validator<PaymentCommand> AMOUNT_POSITIVE =
            c -> c.amountCents() > 0 ? List.of() : List.of("amount must be positive");

    private static final Validator<PaymentCommand> CURRENCY_PRESENT =
            c -> c.currency() != null && !c.currency().isBlank()
                    ? List.of()
                    : List.of("currency is required");

    // A validator of a SUPERTYPE (Object) must be accepted by a PaymentCommand
    // pipeline — this is the `? super T` part of the exercise.
    private static final Validator<Object> NOT_NULL =
            value -> value != null ? List.of() : List.of("value must not be null");

    @Test
    @DisplayName("Valid command produces no violations")
    void validCommandPasses() {
        var pipeline = ValidatorPipeline.<PaymentCommand>of(NOT_NULL, AMOUNT_POSITIVE, CURRENCY_PRESENT);

        assertThat(pipeline.validate(new PaymentCommand(100, "USD"))).isEmpty();
    }

    @Test
    @DisplayName("All failing validators contribute messages, in order")
    void aggregatesViolationsInOrder() {
        var pipeline = ValidatorPipeline.<PaymentCommand>of(AMOUNT_POSITIVE, CURRENCY_PRESENT);

        assertThat(pipeline.validate(new PaymentCommand(-5, "")))
                .containsExactly("amount must be positive", "currency is required");
    }

    @Test
    @DisplayName("Only the failing validator contributes a message")
    void partialFailure() {
        var pipeline = ValidatorPipeline.<PaymentCommand>of(AMOUNT_POSITIVE, CURRENCY_PRESENT);

        assertThat(pipeline.validate(new PaymentCommand(100, "")))
                .containsExactly("currency is required");
    }
}
