package com.nick.javafundamentals.collections;

import java.util.List;
import java.util.Map;

/**
 * Aggregates transactions into per-merchant totals (exercise {@code JAVA-06}).
 *
 * <p>Two implementations exist — {@link LoopTransactionAnalyzer} and
 * {@link StreamTransactionAnalyzer} — so you can write the same logic imperatively
 * and with streams, then run both against the <em>same</em> test and decide which
 * you'd keep and why.
 */
public interface TransactionAnalyzer {

    /** Sum of {@code amountCents} grouped by {@code merchantId}. */
    Map<String, Long> totalAmountByMerchant(List<Transaction> transactions);
}
