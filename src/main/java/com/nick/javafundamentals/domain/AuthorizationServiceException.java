package com.nick.javafundamentals.domain;

/**
 * A stable, caller-facing service exception (exercise {@code JAVA-04}).
 *
 * <p>Callers can depend on this type without depending on how persistence works
 * underneath. The original low-level cause should be attached (for logs), but its
 * details must not appear in the caller-facing message.
 */
public class AuthorizationServiceException extends RuntimeException {

    public AuthorizationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
