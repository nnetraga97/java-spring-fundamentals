package com.nick.javafundamentals.exercises.patt04;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code PATT-04}. Remove {@code @Disabled}, run it (RED), then
 * implement {@link LoggingGateway} and {@link RetryingGateway} until GREEN.
 */
@Disabled("PATT-04 — remove this line to begin the exercise")
class GatewayDecoratorTest {

    /** A scripted gateway: each queued entry is either an auth code or a failure message. */
    private static final class ScriptedGateway implements PaymentGateway {

        private final Deque<String> script = new ArrayDeque<>();
        private int calls;

        ScriptedGateway succeed(String authCode) {
            script.add("OK:" + authCode);
            return this;
        }

        ScriptedGateway fail(String reason) {
            script.add("FAIL:" + reason);
            return this;
        }

        int calls() {
            return calls;
        }

        @Override
        public String charge(String merchantId, long amountCents) {
            calls++;
            String next = script.remove();
            if (next.startsWith("FAIL:")) {
                throw new IllegalStateException(next.substring(5));
            }
            return next.substring(3);
        }
    }

    @Test
    @DisplayName("logging decorator records success and returns the delegate's result")
    void logsSuccess() {
        List<String> log = new ArrayList<>();
        PaymentGateway gateway = new LoggingGateway(new ScriptedGateway().succeed("AUTH-1"), log);

        String auth = gateway.charge("m-1", 500);

        assertThat(auth).isEqualTo("AUTH-1");
        assertThat(log).containsExactly("charge m-1 500 -> OK(AUTH-1)");
    }

    @Test
    @DisplayName("logging decorator records the failure and still rethrows it")
    void logsFailureAndRethrows() {
        List<String> log = new ArrayList<>();
        PaymentGateway gateway = new LoggingGateway(new ScriptedGateway().fail("gateway busy"), log);

        assertThatThrownBy(() -> gateway.charge("m-1", 500))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("gateway busy");
        assertThat(log).containsExactly("charge m-1 500 -> FAILED(gateway busy)");
    }

    @Test
    @DisplayName("retrying decorator turns one failure into a success")
    void retriesOnce() {
        ScriptedGateway real = new ScriptedGateway().fail("blip").succeed("AUTH-2");
        PaymentGateway gateway = new RetryingGateway(real);

        assertThat(gateway.charge("m-1", 500)).isEqualTo("AUTH-2");
        assertThat(real.calls()).isEqualTo(2);
    }

    @Test
    @DisplayName("retrying decorator gives up after the second failure")
    void propagatesSecondFailure() {
        ScriptedGateway real = new ScriptedGateway().fail("down").fail("still down");
        PaymentGateway gateway = new RetryingGateway(real);

        assertThatThrownBy(() -> gateway.charge("m-1", 500))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("still down");
        assertThat(real.calls()).isEqualTo(2);
    }

    @Test
    @DisplayName("stacking order matters: log-around-retry sees one outcome, retry-around-log sees each attempt")
    void stackingOrderChangesObservedBehavior() {
        // log(retry(real)): the logger wraps the whole retried operation -> 1 line.
        List<String> outerLog = new ArrayList<>();
        ScriptedGateway realA = new ScriptedGateway().fail("blip").succeed("AUTH-3");
        new LoggingGateway(new RetryingGateway(realA), outerLog).charge("m-9", 100);
        assertThat(outerLog).containsExactly("charge m-9 100 -> OK(AUTH-3)");

        // retry(log(real)): the logger sits inside the retry -> one line per attempt.
        List<String> innerLog = new ArrayList<>();
        ScriptedGateway realB = new ScriptedGateway().fail("blip").succeed("AUTH-4");
        new RetryingGateway(new LoggingGateway(realB, innerLog)).charge("m-9", 100);
        assertThat(innerLog).containsExactly(
                "charge m-9 100 -> FAILED(blip)",
                "charge m-9 100 -> OK(AUTH-4)");
    }
}
