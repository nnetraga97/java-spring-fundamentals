package com.nick.javafundamentals.exercises.java04;

import com.nick.javafundamentals.domain.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code JAVA-04}. Remove {@code @Disabled}, then implement
 * {@link AuthorizationService#authorize} until GREEN.
 */
@Disabled("JAVA-04 — remove this line to begin the exercise")
class ExceptionTranslationTest {

    // Simulates the leaky low-level detail we must not expose to callers.
    private static final String LEAKY_DETAIL =
            "duplicate key value violates unique constraint \"pk_authorization\"";

    private final AuthorizationRepository failingRepository = id -> {
        throw new RepositoryException(LEAKY_DETAIL);
    };

    @Test
    @DisplayName("A successful repository call returns the stored id")
    void happyPath() {
        AuthorizationRepository repo = id -> id;
        var service = new AuthorizationService(repo);

        assertThat(service.authorize("auth-1")).isEqualTo("auth-1");
    }

    @Test
    @DisplayName("A repository failure becomes a stable service exception")
    void translatesToServiceException() {
        var service = new AuthorizationService(failingRepository);

        assertThatThrownBy(() -> service.authorize("auth-1"))
                .isInstanceOf(AuthorizationServiceException.class);
    }

    @Test
    @DisplayName("The original cause is preserved for diagnostics")
    void preservesCause() {
        var service = new AuthorizationService(failingRepository);

        Throwable thrown = catchThrowable(() -> service.authorize("auth-1"));

        assertThat(thrown.getCause()).isInstanceOf(RepositoryException.class);
    }

    @Test
    @DisplayName("The caller-facing message does not leak persistence internals")
    void doesNotLeakInternals() {
        var service = new AuthorizationService(failingRepository);

        Throwable thrown = catchThrowable(() -> service.authorize("auth-1"));

        assertThat(thrown.getMessage())
                .doesNotContain("constraint")
                .doesNotContain("pk_authorization");
    }
}
