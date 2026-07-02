package com.nick.javafundamentals.exercises.spr01;

import java.util.List;

/**
 * Stable, caller-facing error body (exercises {@code SPR-01}, {@code TEST-04}).
 *
 * <p>Deliberately small and free of internals — no stack traces, no exception
 * class names. Clients can rely on {@code code} and {@code messages}.
 *
 * @param code     a short machine-readable error code, e.g. {@code VALIDATION_FAILED}
 * @param messages human-readable violation messages
 */
public record ApiError(String code, List<String> messages) {
}
