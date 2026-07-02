package com.nick.javafundamentals.testing;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * PLACEHOLDER for exercise {@code TEST-02}: web-layer slice test. You write the
 * assertions — that is the exercise.
 *
 * <p>Goal: test a controller in isolation with {@code @WebMvcTest}, without
 * starting the full app or touching a database.
 *
 * <p>Steps:
 * <ol>
 *   <li>Annotate this class with {@code @WebMvcTest(AuthorizationController.class)}.</li>
 *   <li>Inject {@code MockMvc}; mock the service with {@code @MockitoBean}.</li>
 *   <li>Assert: a valid POST returns the mapped response; an invalid POST returns
 *       the error contract; the service is never called for invalid input.</li>
 * </ol>
 * A completed version of this exists as {@code api/AuthorizationControllerTest}
 * for {@code SPR-01} — try to write yours before peeking.
 */
@Disabled("TEST-02 — you write these. Remove @Disabled and implement the test bodies.")
class Test02WebSliceTests {

    @Test
    void validRequestIsMappedToResponse() {
        // TODO: perform a valid POST and assert status + JSON body.
    }

    @Test
    void invalidRequestReturnsErrorContract() {
        // TODO: perform an invalid POST and assert 400 + stable error shape.
    }
}
