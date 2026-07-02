package com.nick.javafundamentals.domain;

import java.util.Objects;

/**
 * Translates low-level {@link RepositoryException} failures into a stable
 * {@link AuthorizationServiceException} (exercise {@code JAVA-04}).
 *
 * <p>STUB: implement {@link #authorize(String)} so that:
 * <ul>
 *   <li>a repository failure surfaces as {@link AuthorizationServiceException},</li>
 *   <li>the original exception is kept as the {@code cause} (diagnostics preserved),</li>
 *   <li>the caller-facing message does NOT leak repository/SQL detail.</li>
 * </ul>
 * See {@code ExceptionTranslationTest}.
 */
public class AuthorizationService {

    private final AuthorizationRepository repository;

    public AuthorizationService(AuthorizationRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public String authorize(String authorizationId) {
        throw new UnsupportedOperationException(
                "TODO JAVA-04: call the repository and translate RepositoryException");
    }
}
