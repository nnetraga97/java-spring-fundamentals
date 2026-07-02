package com.nick.javafundamentals.exercises.java06;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Spec for {@code JAVA-06}. The same assertions run against BOTH the loop and the
 * stream implementation, proving they behave identically. Remove {@code @Disabled}
 * and implement both analyzers until GREEN, then decide which you'd keep.
 */
@Disabled("JAVA-06 — remove this line to begin the exercise")
class TransactionAnalyzerTest {

    static Stream<TransactionAnalyzer> analyzers() {
        return Stream.of(new LoopTransactionAnalyzer(), new StreamTransactionAnalyzer());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("analyzers")
    void totalsAreGroupedByMerchant(TransactionAnalyzer analyzer) {
        var transactions = List.of(
                new Transaction("m1", 100),
                new Transaction("m2", 50),
                new Transaction("m1", 200));

        assertThat(analyzer.totalAmountByMerchant(transactions))
                .isEqualTo(Map.of("m1", 300L, "m2", 50L));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("analyzers")
    void emptyInputProducesEmptyMap(TransactionAnalyzer analyzer) {
        assertThat(analyzer.totalAmountByMerchant(List.of())).isEmpty();
    }
}
