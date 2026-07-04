package com.nick.javafundamentals.exercises.patt01;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code PATT-01}. Remove {@code @Disabled}, run it (RED), then
 * implement the policies and {@link FeeService} until it is GREEN.
 */
@Disabled("PATT-01 — remove this line to begin the exercise")
class FeeServiceTest {

    private final FeeService service = new FeeService();

    @Test
    @DisplayName("debit is a flat 25 cents regardless of amount")
    void debitIsFlat() {
        assertThat(service.feeCents(CardProduct.DEBIT, 100)).isEqualTo(25);
        assertThat(service.feeCents(CardProduct.DEBIT, 1_000_000)).isEqualTo(25);
    }

    @Test
    @DisplayName("credit is 2% rounded down")
    void creditIsPercentage() {
        assertThat(service.feeCents(CardProduct.CREDIT, 10_000)).isEqualTo(200);
        assertThat(service.feeCents(CardProduct.CREDIT, 99)).isEqualTo(1); // 1.98 rounds down
    }

    @Test
    @DisplayName("premium credit is 3% rounded down with a 50-cent minimum")
    void premiumHasMinimum() {
        assertThat(service.feeCents(CardProduct.PREMIUM_CREDIT, 10_000)).isEqualTo(300);
        assertThat(service.feeCents(CardProduct.PREMIUM_CREDIT, 100)).isEqualTo(50); // 3 < minimum
    }

    @Test
    @DisplayName("every product has a policy — none falls through to a default")
    void everyProductIsCovered() {
        for (CardProduct product : CardProduct.values()) {
            assertThat(service.feeCents(product, 5_000))
                    .as("fee for %s", product)
                    .isPositive();
        }
    }
}
