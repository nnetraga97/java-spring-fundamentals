package com.nick.javafundamentals.exercises.patt03;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code PATT-03}. Remove {@code @Disabled}, run it (RED), then
 * implement {@link TransactionEventBus} until it is GREEN.
 */
@Disabled("PATT-03 — remove this line to begin the exercise")
class TransactionEventBusTest {

    private final TransactionEventBus bus = new TransactionEventBus();

    @Test
    @DisplayName("all subscribers receive a published event in subscription order")
    void notifiesAllSubscribersInOrder() {
        List<String> received = new ArrayList<>();
        bus.subscribe(event -> received.add("ledger:" + event.transactionId()));
        bus.subscribe(event -> received.add("notify:" + event.transactionId()));

        bus.publish(new TransactionEvent("t-1", "APPROVED"));

        assertThat(received).containsExactly("ledger:t-1", "notify:t-1");
    }

    @Test
    @DisplayName("a throwing listener does not prevent the others from being notified")
    void isolatesListenerFailures() {
        List<String> received = new ArrayList<>();
        bus.subscribe(event -> {
            throw new IllegalStateException("analytics is down");
        });
        bus.subscribe(event -> received.add("still-notified"));

        bus.publish(new TransactionEvent("t-2", "DECLINED"));

        assertThat(received).containsExactly("still-notified");
    }

    @Test
    @DisplayName("an unsubscribed listener stops receiving events")
    void unsubscribeStopsDelivery() {
        List<String> received = new ArrayList<>();
        TransactionListener listener = event -> received.add(event.transactionId());
        bus.subscribe(listener);

        bus.publish(new TransactionEvent("t-3", "APPROVED"));
        bus.unsubscribe(listener);
        bus.publish(new TransactionEvent("t-4", "APPROVED"));

        assertThat(received).containsExactly("t-3");
    }

    @Test
    @DisplayName("publishing with no subscribers is harmless")
    void publishWithoutSubscribers() {
        bus.publish(new TransactionEvent("t-5", "APPROVED")); // must not throw
    }
}
