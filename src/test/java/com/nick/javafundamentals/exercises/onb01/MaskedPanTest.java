package com.nick.javafundamentals.exercises.onb01;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code ONB-01}. Remove {@code @Disabled}, run it (RED), then implement
 * {@link MaskedPan} until it is GREEN.
 *
 * <p>Beginner tip: run just this test with
 * {@code ./mvnw test -Dtest=MaskedPanTest} and read the failure message from the
 * top — it names the test that failed and the line that threw.
 */
@Disabled("ONB-01 — remove this line to begin the exercise")
class MaskedPanTest {

    @Test
    @DisplayName("a 16-digit card masks everything except the last four digits")
    void masksAllButLastFour() {
        var pan = MaskedPan.of("4111111111111111");

        assertThat(pan.masked()).isEqualTo("************1111");
        assertThat(pan.lastFour()).isEqualTo("1111");
    }

    @Test
    @DisplayName("spaces and dashes in the input are accepted and ignored")
    void acceptsCommonSeparators() {
        var spaced = MaskedPan.of("4111 1111 1111 1111");
        var dashed = MaskedPan.of("4111-1111-1111-1111");

        assertThat(spaced.masked()).isEqualTo("************1111");
        assertThat(dashed.masked()).isEqualTo("************1111");
    }

    @Test
    @DisplayName("toString is masked, so accidental logging cannot leak the number")
    void toStringNeverContainsFullPan() {
        var pan = MaskedPan.of("4111111111111111");

        // This is what a log line or exception message would contain.
        String logged = "authorization failed for card " + pan;

        assertThat(logged).doesNotContain("4111111111111111");
        assertThat(logged).contains("************1111");
    }

    @Test
    @DisplayName("shorter and longer valid lengths (12-19 digits) still keep four visible")
    void handlesDifferentValidLengths() {
        assertThat(MaskedPan.of("123456789012").masked()).isEqualTo("********9012");
        assertThat(MaskedPan.of("1234567890123456789").masked())
                .isEqualTo("***************6789");
    }

    @Test
    @DisplayName("null, too short, too long, and non-digit input are rejected")
    void rejectsInvalidInput() {
        assertThatThrownBy(() -> MaskedPan.of(null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> MaskedPan.of("41111111111")) // 11 digits
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> MaskedPan.of("41111111111111111111")) // 20 digits
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> MaskedPan.of("4111-1111-1111-abcd"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
