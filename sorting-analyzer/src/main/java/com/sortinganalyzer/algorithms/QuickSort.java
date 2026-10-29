
package com.sortinganalyzer.algorithms;

public class QuickSort {

    public static void sort(double[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(
            double[] array, int low, int high) {

        while (low < high) {

            int pivotIndex = partition(array, low, high);

            // Sort smaller partition first
            // to reduce recursion depth.
            if (pivotIndex - low < high - pivotIndex) {
                quickSort(array, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                quickSort(array, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }

    private static int partition(
            double[] array, int low, int high) {

        int mid = low + (high - low) / 2;
        double pivot = array[mid];

        // Move pivot to the end.
        swap(array, mid, high);

        int i = low;

        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                swap(array, i, j);
                i++;
            }
        }

        swap(array, i, high);
        return i;
    }

    private static void swap(
            double[] array, int i, int j) {

        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
