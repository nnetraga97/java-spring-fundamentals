package com.nick.javafundamentals.exercises.java02;

/**
 * Thrown when the state machine is asked to make a transition that the business
 * rules forbid (exercise {@code JAVA-02}). Unchecked because an illegal transition
 * is a programming/logic error at the call site, not a recoverable condition.
 */
public class InvalidTransitionException extends RuntimeException {

    public InvalidTransitionException(AuthorizationState from, AuthorizationState to) {
        super("Illegal transition: " + from + " -> " + to);
    }
}
