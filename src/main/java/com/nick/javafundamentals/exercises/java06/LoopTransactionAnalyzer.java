package com.nick.javafundamentals.exercises.java06;

import java.util.List;
import java.util.Map;

/**
 * Imperative (loop-based) implementation of {@link TransactionAnalyzer}
 * (exercise {@code JAVA-06}). STUB — implement with an explicit loop and a map.
 */
public class LoopTransactionAnalyzer implements TransactionAnalyzer {

    @Override
    public Map<String, Long> totalAmountByMerchant(List<Transaction> transactions) {
        Map<String, Long> totalsByMerchant = new java.util.HashMap<>();
        for (Transaction transaction : transactions) {
            String merchant = transaction.merchantId();
            long amount = transaction.amountCents();
            totalsByMerchant.put(merchant, totalsByMerchant.getOrDefault(merchant, 0L) + amount);
        }
        return totalsByMerchant;
    }
}
