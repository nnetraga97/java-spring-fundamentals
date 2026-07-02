package com.nick.javafundamentals.exercises.java01;

/**
 * A key type that is <strong>intentionally broken</strong> to demonstrate why the
 * {@code equals}/{@code hashCode} contract matters (exercise {@code JAVA-01}).
 *
 * <p>It overrides {@link #equals(Object)} for value equality but leaves
 * {@link #hashCode()} as the identity hash inherited from {@link Object}. Two
 * "equal" keys therefore usually land in different {@link java.util.HashMap}
 * buckets, so a value stored under one instance cannot be found using an equal-but
 * -different instance. This is the single most common real-world HashMap bug.
 *
 * <p>Do not copy this pattern into real code — {@link IdempotencyKey} (a record)
 * shows the correct approach.
 */
public final class BrokenKey {

    private final String value;

    public BrokenKey(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrokenKey other)) {
            return false;
        }
        return value.equals(other.value);
    }

    // Bug on purpose: no hashCode() override, so equal keys rarely share a bucket.

    @Override
    public String toString() {
        return "BrokenKey[" + value + "]";
    }
}
