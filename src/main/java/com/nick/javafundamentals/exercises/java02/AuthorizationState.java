package com.nick.javafundamentals.exercises.java02;

/**
 * States in the transaction authorization lifecycle (exercise {@code JAVA-02}).
 *
 * <p>The set of legal transitions is intentionally small so the state machine has
 * clear rules to enforce:
 * <ul>
 *   <li>{@code RECEIVED} → {@code APPROVED} or {@code DECLINED}</li>
 *   <li>{@code APPROVED} → {@code REVERSED}</li>
 *   <li>{@code DECLINED} and {@code REVERSED} are terminal</li>
 * </ul>
 */
public enum AuthorizationState {
    RECEIVED,
    APPROVED,
    DECLINED,
    REVERSED
}
