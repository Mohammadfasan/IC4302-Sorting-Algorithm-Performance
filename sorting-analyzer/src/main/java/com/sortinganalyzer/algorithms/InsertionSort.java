
package com.sortinganalyzer.algorithms;

public class InsertionSort {

    public static void sort(double[] array) {

        for (int i = 1; i < array.length; i++) {

            double key = array[i];
            int j = i - 1;

            // Move larger elements one position right
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }

            // Insert the value in the correct position
            array[j + 1] = key;
        }
    }
}
