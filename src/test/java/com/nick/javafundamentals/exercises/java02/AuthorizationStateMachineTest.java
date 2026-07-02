package com.nick.javafundamentals.exercises.java02;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code JAVA-02}. Remove {@code @Disabled}, run it (RED), then implement
 * {@link AuthorizationStateMachine#transitionTo} until it is GREEN.
 */
@Disabled("JAVA-02 — remove this line to begin the exercise")
class AuthorizationStateMachineTest {

    @Test
    @DisplayName("RECEIVED can transition to APPROVED")
    void receivedToApprovedIsAllowed() {
        var machine = new AuthorizationStateMachine(AuthorizationState.RECEIVED);

        var next = machine.transitionTo(AuthorizationState.APPROVED);

        assertThat(next.current()).isEqualTo(AuthorizationState.APPROVED);
    }

    @Test
    @DisplayName("RECEIVED can transition to DECLINED")
    void receivedToDeclinedIsAllowed() {
        var machine = new AuthorizationStateMachine(AuthorizationState.RECEIVED);

        assertThat(machine.transitionTo(AuthorizationState.DECLINED).current())
                .isEqualTo(AuthorizationState.DECLINED);
    }

    @Test
    @DisplayName("APPROVED can be REVERSED")
    void approvedToReversedIsAllowed() {
        var machine = new AuthorizationStateMachine(AuthorizationState.APPROVED);

        assertThat(machine.transitionTo(AuthorizationState.REVERSED).current())
                .isEqualTo(AuthorizationState.REVERSED);
    }

    @Test
    @DisplayName("RECEIVED cannot jump straight to REVERSED")
    void illegalTransitionThrows() {
        var machine = new AuthorizationStateMachine(AuthorizationState.RECEIVED);

        assertThatThrownBy(() -> machine.transitionTo(AuthorizationState.REVERSED))
                .isInstanceOf(InvalidTransitionException.class);
    }

    @Test
    @DisplayName("DECLINED is terminal — no further transitions")
    void terminalStateRejectsTransitions() {
        var machine = new AuthorizationStateMachine(AuthorizationState.DECLINED);

        assertThatThrownBy(() -> machine.transitionTo(AuthorizationState.APPROVED))
                .isInstanceOf(InvalidTransitionException.class);
    }
}
