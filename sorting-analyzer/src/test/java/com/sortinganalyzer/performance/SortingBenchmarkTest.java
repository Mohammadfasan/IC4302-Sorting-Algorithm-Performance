
package com.sortinganalyzer.performance;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SortingBenchmarkTest {

    @Test
    void testAllFiveAlgorithmsRun() {
        double[] numbers = {75, 90, 65, 85, 40};

        List<SortingBenchmark.Result> results =
                SortingBenchmark.run(numbers);

        assertEquals(5, results.size());

        assertEquals("Insertion Sort", results.get(0).algorithm());
        assertEquals("Shell Sort", results.get(1).algorithm());
        assertEquals("Merge Sort", results.get(2).algorithm());
        assertEquals("Quick Sort", results.get(3).algorithm());
        assertEquals("Heap Sort", results.get(4).algorithm());
    }

    @Test
    void testOriginalDataUnchanged() {
        double[] numbers = {75, 90, 65, 85};

        SortingBenchmark.run(numbers);

        assertArrayEquals(
                new double[]{75, 90, 65, 85},
                numbers
        );
    }

    @Test
    void testExecutionTimesAreNonNegative() {
        double[] numbers = {9, 5, 3, 7, 1};

        List<SortingBenchmark.Result> results =
                SortingBenchmark.run(numbers);

        for (SortingBenchmark.Result result : results) {
            assertTrue(result.nanoseconds() >= 0);
            assertEquals(
                    result.nanoseconds() / 1_000_000.0,
                    result.milliseconds()
            );
            assertEquals(
                    result.nanoseconds() / 1_000_000_000.0,
                    result.seconds()
            );
        }
    }

    @Test
    void testFastestAlgorithm() {
        List<SortingBenchmark.Result> results = List.of(
                new SortingBenchmark.Result(
                        "Insertion Sort", 5000, 0.005, 0.000005),
                new SortingBenchmark.Result(
                        "Shell Sort", 2000, 0.002, 0.000002),
                new SortingBenchmark.Result(
                        "Merge Sort", 3000, 0.003, 0.000003)
        );

        SortingBenchmark.Result fastest =
                SortingBenchmark.findFastest(results);

        assertEquals("Shell Sort", fastest.algorithm());
    }

    @Test
    void testEmptyDatasetRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> SortingBenchmark.run(new double[]{})
        );
    }

    @Test
    void testNullDatasetRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> SortingBenchmark.run(null)
        );
    }

    @Test
    void testEmptyResultsRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> SortingBenchmark.findFastest(List.of())
        );
    }
}
