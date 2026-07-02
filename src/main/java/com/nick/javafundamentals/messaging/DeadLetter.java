package com.nick.javafundamentals.messaging;

/**
 * A message that could not be processed, plus the metadata an operator needs to
 * understand why (exercise {@code DIST-04}).
 *
 * @param message  the original message
 * @param attempts how many times processing was attempted
 * @param reason   the failure reason (e.g. the last exception message)
 */
public record DeadLetter(Message message, int attempts, String reason) {
}
