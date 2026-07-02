package com.nick.javafundamentals.persistence;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.OptimisticLockingFailureException;

/**
 * Spec for {@code DATA-04}. Runs against an embedded database via
 * {@code @DataJpaTest}. Two stale copies of the same row are updated in sequence;
 * with a {@code @Version} field the second update must fail.
 *
 * <p>Remove {@code @Disabled} and add the {@code @Version} field to
 * {@link AuthorizationRecord} to turn this GREEN.
 */
@Disabled("DATA-04 — remove this line to begin the exercise")
@DataJpaTest
class AuthorizationRecordOptimisticLockTest {

    @Autowired
    private AuthorizationRecordRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("A concurrent update on a stale version is rejected (no lost update)")
    void concurrentUpdateIsRejected() {
        repository.saveAndFlush(new AuthorizationRecord("a1", "RECEIVED"));
        entityManager.clear();

        // Two independent, detached copies read the same version.
        AuthorizationRecord copyA = repository.findById("a1").orElseThrow();
        entityManager.clear();
        AuthorizationRecord copyB = repository.findById("a1").orElseThrow();
        entityManager.clear();

        // First writer wins.
        copyA.setStatus("APPROVED");
        repository.saveAndFlush(copyA);
        entityManager.clear();

        // Second writer is working from a now-stale version.
        copyB.setStatus("DECLINED");
        assertThatThrownBy(() -> repository.saveAndFlush(copyB))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }
}
