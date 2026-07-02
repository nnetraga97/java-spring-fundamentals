package com.nick.javafundamentals.domain;

/**
 * Minimal persistence dependency for {@code JAVA-04}. A functional interface so a
 * test can supply a fake that succeeds or throws {@link RepositoryException}.
 */
@FunctionalInterface
public interface AuthorizationRepository {

    /** Persists an authorization and returns its stored id. May throw {@link RepositoryException}. */
    String persist(String authorizationId);
}
