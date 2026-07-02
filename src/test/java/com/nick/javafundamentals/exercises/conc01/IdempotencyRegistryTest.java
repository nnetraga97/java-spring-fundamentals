package com.nick.javafundamentals.exercises.conc01;

import com.nick.javafundamentals.concurrency.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Spec for {@code CONC-01}. Remove {@code @Disabled}, then implement
 * {@link IdempotencyRegistry#getOrCompute} until GREEN.
 */
@Disabled("CONC-01 — remove this line to begin the exercise")
class IdempotencyRegistryTest {

    @Test
    @DisplayName("50 concurrent callers on one key compute exactly once")
    void computesOncePerKeyUnderContention() throws Exception {
        int callers = 50;
        var registry = new IdempotencyRegistry<String>();
        var computeCount = new AtomicInteger();
        var startGun = new CountDownLatch(1);

        ExecutorService pool = Executors.newFixedThreadPool(callers);
        try {
            List<Callable<String>> tasks = java.util.stream.IntStream.range(0, callers)
                    .<Callable<String>>mapToObj(i -> () -> {
                        startGun.await(); // line everyone up so they truly race
                        return registry.getOrCompute("same-key", () -> {
                            computeCount.incrementAndGet();
                            return "RESULT";
                        });
                    })
                    .toList();

            List<Future<String>> futures = tasks.stream().map(pool::submit).toList();
            startGun.countDown(); // fire

            for (Future<String> f : futures) {
                assertThat(f.get()).isEqualTo("RESULT");
            }
            assertThat(computeCount.get()).isEqualTo(1);
        } finally {
            pool.shutdownNow();
        }
    }
}
