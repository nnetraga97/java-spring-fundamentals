package com.nick.javafundamentals.testing;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * PLACEHOLDER for exercise {@code TEST-01}: domain unit tests without Spring.
 *
 * <p>Unlike the other spec tests in this repo, here <strong>you write the
 * assertions</strong> — that is the exercise. The other tests show you the shape
 * of a good test; this one asks you to produce one.
 *
 * <p>How to use it:
 * <ol>
 *   <li>Pick a plain-Java domain class you have implemented (a good first target is
 *       {@code AuthorizationStateMachine} once JAVA-02 is done).</li>
 *   <li>Remove {@code @Disabled}.</li>
 *   <li>Fill in each test below using Arrange / Act / Assert. Notice there is no
 *       Spring context and no mocks — that is the point of TEST-01.</li>
 * </ol>
 *
 * <p>Checklist for a good unit test (from the exercise): fast, isolated, one
 * behavior per test, a name that reads like a requirement, and it fails for the
 * right reason if the code is wrong.
 */
@Disabled("TEST-01 — you write these. Remove @Disabled and implement the test bodies.")
class Test01DomainUnitTests {

    @Test
    void approvedTransitionIsAllowed() {
        // Arrange: create the subject in a known starting state.
        // Act: perform the behavior under test.
        // Assert: state the expected outcome with AssertJ (assertThat(...)).
        // TODO: write this test. Delete this comment when done.
    }

    @Test
    void illegalTransitionIsRejected() {
        // TODO: assert the subject throws the domain exception on an illegal move.
        // Hint: assertThatThrownBy(() -> ...).isInstanceOf(...);
    }

    @Test
    void edgeCaseOfYourChoice() {
        // TODO: pick one meaningful edge case (terminal state, null input, boundary)
        // and pin it down with a test.
    }
}
