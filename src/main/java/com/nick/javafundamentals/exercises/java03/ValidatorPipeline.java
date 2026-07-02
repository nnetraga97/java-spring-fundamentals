package com.nick.javafundamentals.exercises.java03;

import java.util.List;

/**
 * Runs a sequence of validators over a value and aggregates their violations
 * (exercise {@code JAVA-03}).
 *
 * <p>The {@code Validator<? super T>} bound is the point of the exercise: a
 * pipeline for {@code PaymentCommand} can accept a {@code Validator<Object>}
 * (e.g. a generic null check), because a validator of a supertype can validate a
 * subtype. This is the "consumer super" half of PECS.
 *
 * <p>STUB: {@link #validate(Object)} is yours to implement. See
 * {@code ValidatorPipelineTest}.
 *
 * @param <T> the type validated by this pipeline
 */
public final class ValidatorPipeline<T> {

    private final List<Validator<? super T>> validators;

    private ValidatorPipeline(List<Validator<? super T>> validators) {
        this.validators = List.copyOf(validators);
    }

    @SafeVarargs
    public static <T> ValidatorPipeline<T> of(Validator<? super T>... validators) {
        return new ValidatorPipeline<T>(List.<Validator<? super T>>of(validators));
    }

    /** Runs every validator in order and returns all violation messages combined. */
    public List<String> validate(T value) {
        throw new UnsupportedOperationException(
                "TODO JAVA-03: run each validator in order and collect all messages");
    }
}
