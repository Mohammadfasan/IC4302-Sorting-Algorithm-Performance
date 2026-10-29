
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

    // Store execution time for each algorithm
    public record Result(
            String algorithm,
            long nanoseconds,
            double milliseconds,
            double seconds
    ) {}

    // Run all five sorting algorithms
    public static List<Result> run(double[] originalData) {

        if (originalData == null || originalData.length == 0) {
            throw new IllegalArgumentException(
                "Please select a non-empty numeric dataset."
            );
        }

        List<Result> results = new ArrayList<>();

        benchmark(
            results,
            "Insertion Sort",
            originalData,
            InsertionSort::sort
        );

        benchmark(
            results,
            "Shell Sort",
            originalData,
            ShellSort::sort
        );

        benchmark(
            results,
            "Merge Sort",
            originalData,
            MergeSort::sort
        );

        benchmark(
            results,
            "Quick Sort",
            originalData,
            QuickSort::sort
        );

        benchmark(
            results,
            "Heap Sort",
            originalData,
            HeapSort::sort
        );

        return results;
    }

    // Measure execution time of one sorting algorithm
    private static void benchmark(
            List<Result> results,
            String name,
            double[] originalData,
            Consumer<double[]> algorithm) {

        // Create a separate copy for fair comparison
        double[] data = originalData.clone();

        // Prepare the expected sorted result
        double[] expected = originalData.clone();
        Arrays.sort(expected);

        // Start performance measurement
        long startTime = System.nanoTime();

        // Execute sorting algorithm
        algorithm.accept(data);

        // End performance measurement
        long endTime = System.nanoTime();

        // Check whether sorting is correct
        if (!Arrays.equals(data, expected)) {
            throw new IllegalStateException(
                name + " produced an incorrect sorted result."
            );
        }

        // Calculate execution time
        long elapsedNanoseconds = endTime - startTime;

        double elapsedMilliseconds =
                elapsedNanoseconds / 1_000_000.0;

        double elapsedSeconds =
                elapsedNanoseconds / 1_000_000_000.0;

        // Save result
        results.add(new Result(
            name,
            elapsedNanoseconds,
            elapsedMilliseconds,
            elapsedSeconds
        ));
    }

    // Find the fastest sorting algorithm
    public static Result findFastest(List<Result> results) {

        if (results == null || results.isEmpty()) {
            throw new IllegalArgumentException(
                "No benchmark results available."
            );
        }

        return results.stream()
                .min(Comparator.comparingLong(
                    Result::nanoseconds
                ))
                .orElseThrow();
    }
}
