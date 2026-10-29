
package com.sortinganalyzer.algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class QuickSortTest {

    @Test
    void testNormalNumbers() {
        double[] numbers = {75, 90, 65, 85, 40};

        QuickSort.sort(numbers);

        assertArrayEquals(
            new double[]{40, 65, 75, 85, 90},
            numbers
        );
    }

    @Test
    void testDuplicates() {
        double[] numbers = {5, 2, 5, 1};

        QuickSort.sort(numbers);

        assertArrayEquals(
            new double[]{1, 2, 5, 5},
            numbers
        );
    }

    @Test
    void testNegativeNumbers() {
        double[] numbers = {-3, 10, 0, -7};

        QuickSort.sort(numbers);

        assertArrayEquals(
            new double[]{-7, -3, 0, 10},
            numbers
        );
    }

    @Test
    void testEmptyArray() {
        double[] numbers = {};

        QuickSort.sort(numbers);

        assertArrayEquals(new double[]{}, numbers);
    }

    @Test
    void testAlreadySorted() {
        double[] numbers = {1, 2, 3, 4, 5};

        QuickSort.sort(numbers);

        assertArrayEquals(
            new double[]{1, 2, 3, 4, 5},
            numbers
        );
    }
}
