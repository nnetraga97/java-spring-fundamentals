package com.nick.javafundamentals.exercises.patt05;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code PATT-05}. Remove {@code @Disabled}, run it (RED), then
 * implement {@link SettlementFileProcessor#process} until it is GREEN.
 */
@Disabled("PATT-05 — remove this line to begin the exercise")
class SettlementFileProcessorTest {

    /** A concrete format: "merchantId,amountCents". Negative amounts are invalid. */
    private static final class CsvSettlementProcessor extends SettlementFileProcessor<long[]> {

        final List<String> applied = new ArrayList<>();
        final List<String> callOrder = new ArrayList<>();

        @Override
        protected long[] parseLine(String line) {
            callOrder.add("parse:" + line);
            String[] parts = line.split(",");
            return new long[] {Long.parseLong(parts[1])};
        }

        @Override
        protected boolean isValid(long[] record) {
            callOrder.add("validate:" + record[0]);
            return record[0] >= 0;
        }

        @Override
        protected void apply(long[] record) {
            callOrder.add("apply:" + record[0]);
            applied.add(String.valueOf(record[0]));
        }
    }

    @Test
    @DisplayName("valid lines are parsed, validated, and applied in order")
    void processesValidLines() {
        var processor = new CsvSettlementProcessor();

        ProcessingSummary summary = processor.process(List.of("m-1,100", "m-2,250"));

        assertThat(summary).isEqualTo(new ProcessingSummary(2, 0));
        assertThat(processor.applied).containsExactly("100", "250");
        assertThat(processor.callOrder).containsExactly(
                "parse:m-1,100", "validate:100", "apply:100",
                "parse:m-2,250", "validate:250", "apply:250");
    }

    @Test
    @DisplayName("invalid records are rejected and never applied")
    void rejectsInvalidRecords() {
        var processor = new CsvSettlementProcessor();

        ProcessingSummary summary = processor.process(List.of("m-1,100", "m-2,-50", "m-3,75"));

        assertThat(summary).isEqualTo(new ProcessingSummary(2, 1));
        assertThat(processor.applied).containsExactly("100", "75");
    }

    @Test
    @DisplayName("blank lines are ignored — neither processed nor rejected")
    void ignoresBlankLines() {
        var processor = new CsvSettlementProcessor();

        ProcessingSummary summary = processor.process(List.of("", "m-1,100", "   ", "m-2,200"));

        assertThat(summary).isEqualTo(new ProcessingSummary(2, 0));
    }

    @Test
    @DisplayName("an empty file yields an all-zero summary")
    void emptyFile() {
        assertThat(new CsvSettlementProcessor().process(List.of()))
                .isEqualTo(new ProcessingSummary(0, 0));
    }

    @Test
    @DisplayName("the template method is final — subclasses cannot reorder the skeleton")
    void templateMethodIsFinal() throws Exception {
        var method = SettlementFileProcessor.class.getMethod("process", List.class);

        assertThat(Modifier.isFinal(method.getModifiers()))
                .as("process(List) should be final — that is the point of the pattern")
                .isTrue();
    }
}
