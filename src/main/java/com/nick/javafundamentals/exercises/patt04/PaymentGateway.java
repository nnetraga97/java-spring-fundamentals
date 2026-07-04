package com.nick.javafundamentals.exercises.patt04;

/**
 * The component interface for exercise {@code PATT-04}: something that can
 * charge a payment. Both the real gateway and every decorator implement this,
 * which is what makes them stackable.
 */
public interface PaymentGateway {

    /**
     * Charges the merchant and returns an authorization code.
     *
     * @throws RuntimeException when the charge fails
     */
    String charge(String merchantId, long amountCents);
}
