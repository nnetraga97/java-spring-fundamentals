package com.nick.javafundamentals.exercises.patt02;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code PATT-02}. Remove {@code @Disabled}, run it (RED), then
 * implement {@link PaymentRequest.Builder} until it is GREEN.
 */
@Disabled("PATT-02 — remove this line to begin the exercise")
class PaymentRequestTest {

    @Test
    @DisplayName("a fully specified request carries every value")
    void buildsFullySpecifiedRequest() {
        PaymentRequest request = PaymentRequest.builder()
                .merchantId("m-42")
                .amountCents(1999)
                .currency("EUR")
                .captureImmediately(false)
                .build();

        assertThat(request.merchantId()).isEqualTo("m-42");
        assertThat(request.amountCents()).isEqualTo(1999);
        assertThat(request.currency()).isEqualTo("EUR");
        assertThat(request.captureImmediately()).isFalse();
    }

    @Test
    @DisplayName("currency defaults to USD and capture defaults to true")
    void defaultsApply() {
        PaymentRequest request = PaymentRequest.builder()
                .merchantId("m-42")
                .amountCents(500)
                .build();

        assertThat(request.currency()).isEqualTo("USD");
        assertThat(request.captureImmediately()).isTrue();
    }

    @Test
    @DisplayName("a missing merchantId fails at build() and names the field")
    void missingMerchantFails() {
        var builder = PaymentRequest.builder().amountCents(500);

        assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("merchantId");
    }

    @Test
    @DisplayName("a non-positive amount fails at build() and names the field")
    void nonPositiveAmountFails() {
        var builder = PaymentRequest.builder().merchantId("m-42").amountCents(0);

        assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("amountCents");
    }

    @Test
    @DisplayName("setter order does not matter")
    void orderFreeAssembly() {
        PaymentRequest request = PaymentRequest.builder()
                .captureImmediately(false)
                .currency("GBP")
                .amountCents(250)
                .merchantId("m-1")
                .build();

        assertThat(request.merchantId()).isEqualTo("m-1");
        assertThat(request.currency()).isEqualTo("GBP");
    }
}
