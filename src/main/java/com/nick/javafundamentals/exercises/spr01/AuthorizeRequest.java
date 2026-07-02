package com.nick.javafundamentals.exercises.spr01;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * Request DTO for {@code POST /authorizations} (exercise {@code SPR-01}).
 *
 * <p>Validation constraints live on the DTO so invalid input is rejected at the
 * edge, before any business logic runs.
 */
public record AuthorizeRequest(
        @NotBlank String merchantId,
        @Positive long amountCents,
        @NotBlank String currency) {
}
