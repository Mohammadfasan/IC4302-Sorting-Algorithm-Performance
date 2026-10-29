
package com.sortinganalyzer.algorithms;

public class ShellSort {

    public static void sort(double[] array) {

        int n = array.length;

        // Start with a large gap, then reduce it
        for (int gap = n / 2; gap > 0; gap /= 2) {

            // Perform insertion sort for this gap
            for (int i = gap; i < n; i++) {

                double temp = array[i];
                int j = i;

                // Move larger values to the right
                while (j >= gap && array[j - gap] > temp) {
                    array[j] = array[j - gap];
                    j -= gap;
                }

                array[j] = temp;
            }
        }
    }
}
