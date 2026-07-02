package com.nick.javafundamentals.exercises.java03;

import java.util.List;

/**
 * Validates a value and returns any violation messages (exercise {@code JAVA-03}).
 * An empty list means "valid".
 *
 * <p>Functional interface so validators can be written as lambdas in tests.
 *
 * @param <T> the type this validator inspects
 */
@FunctionalInterface
public interface Validator<T> {

    List<String> validate(T value);
}
