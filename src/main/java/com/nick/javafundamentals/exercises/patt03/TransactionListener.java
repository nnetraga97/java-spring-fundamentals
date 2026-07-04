package com.nick.javafundamentals.exercises.patt03;

/** The Observer interface for exercise {@code PATT-03}. */
@FunctionalInterface
public interface TransactionListener {

    void onEvent(TransactionEvent event);
}
