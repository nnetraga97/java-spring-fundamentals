package com.nick.javafundamentals.collections;

import java.util.List;
import java.util.Map;

/**
 * Imperative (loop-based) implementation of {@link TransactionAnalyzer}
 * (exercise {@code JAVA-06}). STUB — implement with an explicit loop and a map.
 */
public class LoopTransactionAnalyzer implements TransactionAnalyzer {

    @Override
    public Map<String, Long> totalAmountByMerchant(List<Transaction> transactions) {
        throw new UnsupportedOperationException(
                "TODO JAVA-06: sum amounts per merchant using an explicit loop");
    }
}
