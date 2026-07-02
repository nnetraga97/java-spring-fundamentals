package com.nick.javafundamentals.exercises.test04;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * PLACEHOLDER for exercise {@code TEST-04}: contract test for error responses. You
 * write the assertions — that is the exercise.
 *
 * <p>Goal: pin down the stable error shape clients depend on, and prove internals
 * never leak.
 *
 * <p>Cover these negative paths and assert the SAME response structure for each:
 * <ul>
 *   <li>validation failure (400),</li>
 *   <li>conflict / duplicate (409),</li>
 *   <li>unauthorized (401),</li>
 *   <li>unexpected error (500).</li>
 * </ul>
 * For every case, also assert the body contains NO stack trace and NO internal
 * class names.
 */
@Disabled("TEST-04 — you write these. Remove @Disabled and implement the test bodies.")
class Test04ErrorContractTests {

    @Test
    void errorBodyShapeIsStableAcrossFailures() {
        // TODO: exercise each failure path and assert the shared error contract.
    }

    @Test
    void errorBodyDoesNotLeakInternals() {
        // TODO: assert no stack trace / exception class names appear in the body.
    }
}
