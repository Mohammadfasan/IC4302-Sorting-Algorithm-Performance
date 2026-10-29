
package com.sortinganalyzer.algorithms;

public class MergeSort {

    public static void sort(double[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        double[] temp = new double[array.length];
        mergeSort(array, temp, 0, array.length - 1);
    }

    private static void mergeSort(
            double[] array, double[] temp, int left, int right) {

        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(array, temp, left, mid);
        mergeSort(array, temp, mid + 1, right);

        merge(array, temp, left, mid, right);
    }

    private static void merge(
            double[] array, double[] temp,
            int left, int mid, int right) {

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = array[i++];
        }

        while (j <= right) {
            temp[k++] = array[j++];
        }

        for (int index = left; index <= right; index++) {
            array[index] = temp[index];
        }
    }
}
