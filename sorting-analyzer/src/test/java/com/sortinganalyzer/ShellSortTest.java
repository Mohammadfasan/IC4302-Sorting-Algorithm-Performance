
package com.sortinganalyzer.algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ShellSortTest {

    @Test
    void testNormalNumbers() {
        double[] numbers = {75, 90, 65, 85, 40};

        ShellSort.sort(numbers);

        assertArrayEquals(
            new double[]{40, 65, 75, 85, 90},
            numbers
        );
    }

    @Test
    void testDuplicates() {
        double[] numbers = {5, 2, 5, 1};

        ShellSort.sort(numbers);

        assertArrayEquals(
            new double[]{1, 2, 5, 5},
            numbers
        );
    }

    @Test
    void testNegativeNumbers() {
        double[] numbers = {-3, 10, 0, -7};

        ShellSort.sort(numbers);

        assertArrayEquals(
            new double[]{-7, -3, 0, 10},
            numbers
        );
    }

    @Test
    void testEmptyArray() {
        double[] numbers = {};

        ShellSort.sort(numbers);

        assertArrayEquals(new double[]{}, numbers);
    }
}
