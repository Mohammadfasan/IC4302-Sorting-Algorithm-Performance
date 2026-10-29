
package com.sortinganalyzer.algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class InsertionSortTest {

    @Test
    void testNormalNumbers() {
        double[] numbers = {75, 90, 65, 85};

        InsertionSort.sort(numbers);

        assertArrayEquals(
            new double[]{65, 75, 85, 90},
            numbers
        );
    }

    @Test
    void testDuplicates() {
        double[] numbers = {5, 2, 5, 1};

        InsertionSort.sort(numbers);

        assertArrayEquals(
            new double[]{1, 2, 5, 5},
            numbers
        );
    }

    @Test
    void testNegativeNumbers() {
        double[] numbers = {-3, 10, 0, -7};

        InsertionSort.sort(numbers);

        assertArrayEquals(
            new double[]{-7, -3, 0, 10},
            numbers
        );
    }

    @Test
    void testEmptyArray() {
        double[] numbers = {};

        InsertionSort.sort(numbers);

        assertArrayEquals(new double[]{}, numbers);
    }
}
