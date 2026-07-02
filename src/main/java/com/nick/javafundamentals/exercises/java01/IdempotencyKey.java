package com.nick.javafundamentals.exercises.java01;

import java.util.Objects;

/**
 * Immutable composite key for an idempotency cache, keyed by merchant and request.
 *
 * <p>Records are the right tool here: they generate a correct, value-based
 * {@code equals}/{@code hashCode} from all components, and they are immutable, so
 * a key can never mutate after it has been placed in a hash bucket. Both
 * properties are exactly what a {@link java.util.HashMap} key contract requires.
 *
 * <p>Compact constructor enforces the invariant that neither component is null,
 * so a malformed key fails loudly at construction rather than silently hashing to
 * a surprising bucket later.
 */
public record IdempotencyKey(String merchantId, String requestId) {

    public IdempotencyKey {
        Objects.requireNonNull(merchantId, "merchantId must not be null");
        Objects.requireNonNull(requestId, "requestId must not be null");
    }
}
