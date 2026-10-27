
package com.sortinganalyzer;

import com.sortinganalyzer.csv.CSVReader;

public class App {
    public static void main(String[] args) {
        CSVReader csv = new CSVReader();

        try {
            csv.loadCSV("datasets/students.csv");

            System.out.println("CSV uploaded successfully!");
            System.out.println("Columns: " + csv.getHeaders());
            System.out.println("Total rows: " + csv.getRowCount());

            double[] marks = csv.getNumericColumn(2);

            System.out.println("Selected column: Marks");

            for (double mark : marks) {
                System.out.println(mark);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
