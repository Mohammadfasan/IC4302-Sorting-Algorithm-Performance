
package com.sortinganalyzer.performance;

import com.sortinganalyzer.algorithms.InsertionSort;
import com.sortinganalyzer.algorithms.ShellSort;
import com.sortinganalyzer.algorithms.MergeSort;
import com.sortinganalyzer.algorithms.QuickSort;
import com.sortinganalyzer.algorithms.HeapSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class SortingBenchmark {

    private static final int WARMUP_RUNS = 5;
    private static final int MEASURED_RUNS = 15;

    public record Result(
            String algorithm,
            long nanoseconds,
            double milliseconds,
            double seconds
    ) {}

    public static List<Result> run(double[] originalData) {

        if (originalData == null || originalData.length == 0) {
            throw new IllegalArgumentException(
                    "Please select a non-empty numeric dataset."
            );
        }

        List<Result> results = new ArrayList<>();

        benchmark(results, "Insertion Sort",
                originalData, InsertionSort::sort);

        benchmark(results, "Shell Sort",
                originalData, ShellSort::sort);

        benchmark(results, "Merge Sort",
                originalData, MergeSort::sort);

        benchmark(results, "Quick Sort",
                originalData, QuickSort::sort);

        benchmark(results, "Heap Sort",
                originalData, HeapSort::sort);

        return results;
    }

    private static void benchmark(
            List<Result> results,
            String name,
            double[] originalData,
            Consumer<double[]> algorithm) {

        double[] expected = originalData.clone();
        Arrays.sort(expected);

        // Warm-up runs are not included in the results.
        for (int i = 0; i < WARMUP_RUNS; i++) {
            double[] data = originalData.clone();

            algorithm.accept(data);
            verifySorted(name, data, expected);
        }

        long[] times = new long[MEASURED_RUNS];

        // Measure sorting only, not array copying.
        for (int i = 0; i < MEASURED_RUNS; i++) {

            double[] data = originalData.clone();

            long start = System.nanoTime();
            algorithm.accept(data);
            long end = System.nanoTime();

            verifySorted(name, data, expected);

            times[i] = end - start;
        }

        Arrays.sort(times);

        // 15 runs: the middle (8th) value is the median.
        long medianNanoseconds = times[MEASURED_RUNS / 2];

        results.add(new Result(
                name,
                medianNanoseconds,
                medianNanoseconds / 1_000_000.0,
                medianNanoseconds / 1_000_000_000.0
        ));
    }

    private static void verifySorted(
            String name,
            double[] actual,
            double[] expected) {

        if (!Arrays.equals(actual, expected)) {
            throw new IllegalStateException(
                    name + " produced an incorrect sorted result."
            );
        }
    }

    public static Result findFastest(List<Result> results) {

        if (results == null || results.isEmpty()) {
            throw new IllegalArgumentException(
                    "No benchmark results available."
            );
        }

        return results.stream()
                .min(Comparator.comparingLong(Result::nanoseconds))
                .orElseThrow();
    }
}
