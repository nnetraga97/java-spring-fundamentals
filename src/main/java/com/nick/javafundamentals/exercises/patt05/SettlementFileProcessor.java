package com.nick.javafundamentals.exercises.patt05;

import java.util.List;

/**
 * Processes a settlement file with a fixed skeleton and per-format hooks
 * (exercise {@code PATT-05}).
 *
 * <p>Every settlement format (network files, bank files, partner files) goes
 * through the same steps — split lines, parse, validate, apply — but the
 * parsing and applying differ per format. Template Method pins the invariant
 * sequence in a {@code final} method here, and subclasses fill in only the
 * varying steps. Compare with {@code PATT-01}: Strategy composes the varying
 * part in, Template Method inherits it — know both and prefer composition when
 * the variation is a single step.
 *
 * <p>STUB: {@link #process} is yours (it is the template). The test
 * {@code SettlementFileProcessorTest} is the specification — remove its
 * {@code @Disabled} and make it pass.
 *
 * @param <T> the parsed record type of the concrete format
 */
public abstract class SettlementFileProcessor<T> {

    /**
     * The template method — the invariant skeleton, deliberately {@code final}
     * so no subclass can reorder settlement processing.
     *
     * <p>For each line, in order:
     * <ol>
     *   <li>Blank (or whitespace-only) lines are ignored entirely — they count
     *       as neither processed nor rejected.</li>
     *   <li>{@link #parseLine} turns the line into a record.</li>
     *   <li>If {@link #isValid} says no, the record is counted as rejected and
     *       {@link #apply} is not called.</li>
     *   <li>Otherwise {@link #apply} runs and the record counts as processed.</li>
     * </ol>
     */
    public final ProcessingSummary process(List<String> lines) {
        throw new UnsupportedOperationException(
                "TODO PATT-05: skip blanks, parse, validate, apply, count outcomes");
    }

    /** Parses one non-blank line of this format. */
    protected abstract T parseLine(String line);

    /** Validation hook; the default accepts everything. Subclasses override as needed. */
    protected boolean isValid(T record) {
        return true;
    }

    /** Applies one valid record (post to ledger, mark settled, ...). */
    protected abstract void apply(T record);
}
