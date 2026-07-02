package com.nick.javafundamentals.exercises.java06;

import java.util.List;
import java.util.Map;

/**
 * Stream-based implementation of {@link TransactionAnalyzer} (exercise
 * {@code JAVA-06}). STUB — implement with {@code Collectors.groupingBy} +
 * {@code Collectors.summingLong} and compare readability against the loop version.
 */
public class StreamTransactionAnalyzer implements TransactionAnalyzer {

    @Override
    public Map<String, Long> totalAmountByMerchant(List<Transaction> transactions) {
        throw new UnsupportedOperationException(
                "TODO JAVA-06: sum amounts per merchant using the Stream API");
    }
}
