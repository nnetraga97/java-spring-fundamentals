package com.nick.javafundamentals.exercises.java02;

import java.util.Objects;

/**
 * Enforces the legal transitions described in {@link AuthorizationState}
 * (exercise {@code JAVA-02}).
 *
 * <p>STUB: {@link #transitionTo(AuthorizationState)} is the part you implement.
 * The test {@code AuthorizationStateMachineTest} is the specification — remove its
 * {@code @Disabled} and make it pass.
 *
 * <p>Design hint: the returned value lets you decide immutable (return a new
 * machine) vs mutable (return {@code this}); the tests work with either.
 */
public class AuthorizationStateMachine {

    private final AuthorizationState current;

    public AuthorizationStateMachine(AuthorizationState initial) {
        this.current = Objects.requireNonNull(initial, "initial state must not be null");
    }

    public AuthorizationState current() {
        return current;
    }

    /**
     * Applies a transition if the business rules allow it, otherwise throws
     * {@link InvalidTransitionException}.
     */
    public AuthorizationStateMachine transitionTo(AuthorizationState target) {
        throw new UnsupportedOperationException(
                "TODO JAVA-02: validate current->target against the rules, then apply it");
    }
}
