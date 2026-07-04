package com.nick.javafundamentals.exercises.onb02;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code ONB-02}. Remove {@code @Disabled}, run it (RED), then implement
 * {@link Money#of}, {@link Money#add} and {@link Money#times} until it is GREEN.
 */
@Disabled("ONB-02 — remove this line to begin the exercise")
class MoneyTest {

    @Test
    @DisplayName("0.10 + 0.20 is exactly 0.30 — the reason money is never a double")
    void decimalAdditionIsExact() {
        // With double this is 0.30000000000000004. That error compounds across
        // millions of transactions; with BigDecimal it does not exist.
        var sum = Money.of("0.10", "USD").add(Money.of("0.20", "USD"));

        assertThat(sum).isEqualTo(Money.of("0.30", "USD"));
    }

    @Test
    @DisplayName("'10' and '10.00' are the same amount of money")
    void scaleIsNormalizedToTwoDecimalPlaces() {
        assertThat(Money.of("10", "USD")).isEqualTo(Money.of("10.00", "USD"));
        assertThat(Money.of("10", "USD").amount()).isEqualTo(new BigDecimal("10.00"));
    }

    @Test
    @DisplayName("times multiplies by a whole quantity")
    void timesMultipliesExactly() {
        var lineTotal = Money.of("19.99", "USD").times(3);

        assertThat(lineTotal).isEqualTo(Money.of("59.97", "USD"));
    }

    @Test
    @DisplayName("adding different currencies is a bug, not a conversion")
    void addingDifferentCurrenciesThrows() {
        var usd = Money.of("10.00", "USD");
        var eur = Money.of("10.00", "EUR");

        assertThatThrownBy(() -> usd.add(eur))
                .isInstanceOf(CurrencyMismatchException.class);
    }

    @Test
    @DisplayName("more than two decimal places is rejected, never silently rounded")
    void subCentPrecisionIsRejected() {
        assertThatThrownBy(() -> Money.of("10.999", "USD"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("null amount or currency is rejected")
    void nullsAreRejected() {
        assertThatThrownBy(() -> Money.of(null, "USD"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Money.of("10.00", null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
