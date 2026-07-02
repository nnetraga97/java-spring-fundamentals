package com.nick.javafundamentals.concurrency;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Runs several tasks concurrently on virtual threads and collects their results
 * (exercise {@code CONC-06}).
 *
 * <p>STUB: implement {@link #all} using
 * {@code Executors.newVirtualThreadPerTaskExecutor()}. Requirements:
 * <ul>
 *   <li>results are returned in the same order as the input tasks;</li>
 *   <li>if any task throws, {@code all} throws (wrap checked exceptions in a
 *       {@link RuntimeException}).</li>
 * </ul>
 *
 * <p>Stretch (not asserted): cancel the still-running siblings when one task
 * fails, and reason about {@code StructuredTaskScope} as the cleaner primitive.
 * See {@code VirtualThreadFanOutTest}.
 */
public class VirtualThreadFanOut {

    public <T> List<T> all(List<Callable<T>> tasks) {
        throw new UnsupportedOperationException(
                "TODO CONC-06: run tasks on virtual threads, preserve order, propagate failure");
    }
}
