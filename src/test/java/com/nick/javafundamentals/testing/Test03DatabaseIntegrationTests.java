package com.nick.javafundamentals.testing;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * PLACEHOLDER for exercise {@code TEST-03}: database integration test with
 * Testcontainers. You write it — that is the exercise.
 *
 * <p>Goal: test repository/constraint behavior against a real PostgreSQL engine,
 * not H2, because database-specific behavior (unique constraints, types, locking)
 * does not always match on H2.
 *
 * <p>Steps:
 * <ol>
 *   <li>Annotate with {@code @DataJpaTest} and
 *       {@code @AutoConfigureTestDatabase(replace = NONE)}.</li>
 *   <li>Start a container: a static {@code @Container PostgreSQLContainer<>} wired
 *       with {@code @ServiceConnection} (the testcontainers deps are already on the
 *       test classpath).</li>
 *   <li>Assert a database-level behavior — e.g. inserting a duplicate key violates a
 *       unique constraint and throws {@code DataIntegrityViolationException}.</li>
 * </ol>
 */
@Disabled("TEST-03 — you write these. Remove @Disabled and implement the test bodies.")
class Test03DatabaseIntegrationTests {

    @Test
    void uniqueConstraintIsEnforced() {
        // TODO: save a row, then save a conflicting row, and assert the DB rejects it.
    }
}
