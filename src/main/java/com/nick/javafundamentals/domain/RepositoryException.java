package com.nick.javafundamentals.domain;

/**
 * A low-level persistence failure (exercise {@code JAVA-04}). Stands in for the
 * kind of driver/ORM exception that carries implementation detail you do NOT want
 * to leak past the service boundary.
 */
public class RepositoryException extends RuntimeException {

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
