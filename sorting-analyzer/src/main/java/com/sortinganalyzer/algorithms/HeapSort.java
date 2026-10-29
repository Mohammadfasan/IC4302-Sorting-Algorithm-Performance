
package com.sortinganalyzer.algorithms;

public class HeapSort {

    public static void sort(double[] array) {

        if (array == null || array.length <= 1) {
            return;
        }

        int n = array.length;

        // Step 1: Build a max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        // Step 2: Move largest element to the end
        for (int i = n - 1; i > 0; i--) {

            double temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            // Restore heap property
            heapify(array, i, 0);
        }
    }

    private static void heapify(
            double[] array, int n, int root) {

        while (true) {

            int largest = root;
            int left = 2 * root + 1;
            int right = 2 * root + 2;

            // Check left child
            if (left < n && array[left] > array[largest]) {
                largest = left;
            }

            // Check right child
            if (right < n && array[right] > array[largest]) {
                largest = right;
            }

            if (largest == root) {
                break;
            }

            // Swap root with largest child
            double temp = array[root];
            array[root] = array[largest];
            array[largest] = temp;

            root = largest;
        }
    }
}
