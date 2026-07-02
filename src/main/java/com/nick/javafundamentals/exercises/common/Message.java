package com.nick.javafundamentals.exercises.common;

/**
 * A message that may be delivered more than once (exercises {@code DIST-02},
 * {@code DIST-04}). The {@code id} is what lets a consumer recognize duplicates.
 */
public record Message(String id, String payload) {
}
