package com.nick.javafundamentals.exercises.patt03;

/**
 * Notifies interested parties (ledger, notifications, analytics) about
 * transaction events without the publisher knowing who they are
 * (exercise {@code PATT-03}).
 *
 * <p>Why Observer: the authorization code should not accumulate a call to every
 * team's side effect. Subscribers come and go; the publisher stays untouched.
 * This is the in-process ancestor of the message broker patterns in
 * {@code DIST-*} — same decoupling idea, before the network gets involved.
 *
 * <p>STUB: all three methods are yours. The test {@code TransactionEventBusTest}
 * is the specification — remove its {@code @Disabled} and make it pass. The
 * subtle requirement: one listener throwing must not prevent the remaining
 * listeners from being notified.
 */
public class TransactionEventBus {

    /** Registers a listener; it will receive every subsequent event, in subscription order. */
    public void subscribe(TransactionListener listener) {
        throw new UnsupportedOperationException("TODO PATT-03: remember the listener");
    }

    /** Removes a previously registered listener; unknown listeners are a no-op. */
    public void unsubscribe(TransactionListener listener) {
        throw new UnsupportedOperationException("TODO PATT-03: forget the listener");
    }

    /**
     * Delivers the event to every current listener. A listener that throws is
     * skipped (in production you would also log it) — the others still run.
     */
    public void publish(TransactionEvent event) {
        throw new UnsupportedOperationException(
                "TODO PATT-03: notify all listeners, isolating each from the others' failures");
    }
}
