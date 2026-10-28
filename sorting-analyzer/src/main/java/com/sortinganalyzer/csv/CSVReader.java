
package com.sortinganalyzer.csv;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CSVReader {

    private final List<String> headers = new ArrayList<>();
    private final List<String[]> rows = new ArrayList<>();

    public void loadCSV(String filePath) throws IOException {
        headers.clear();
        rows.clear();

        if (!filePath.toLowerCase().endsWith(".csv")) {
            throw new IOException("Please select a CSV file.");
        }

        try (BufferedReader reader =
                Files.newBufferedReader(Path.of(filePath))) {

            String line = reader.readLine();

            if (line == null || line.isBlank()) {
                throw new IOException("CSV file is empty.");
            }

            headers.addAll(Arrays.asList(line.split(",", -1)));

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] values = line.split(",", -1);

                if (values.length != headers.size()) {
                    throw new IOException("Invalid CSV row format.");
                }

                rows.add(values);
            }
        }
    }

    public List<String> getHeaders() {
        return Collections.unmodifiableList(headers);
    }

    public double[] getNumericColumn(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= headers.size()) {
            throw new IllegalArgumentException("Invalid column.");
        }

        double[] numbers = new double[rows.size()];

        for (int i = 0; i < rows.size(); i++) {
            String value = rows.get(i)[columnIndex].trim();

            try {
                numbers[i] = Double.parseDouble(value);
                if (!Double.isFinite(numbers[i])) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                    "Invalid numeric value at row " + (i + 2)
                );
            }
        }

        return numbers;
    }

    public int getRowCount() {
        return rows.size();
    }

   
    // Return the total number of columns
    public int getColumnCount() {
        return headers.size();
    }

    // Return a safe copy of all dataset rows
    public List<String[]> getRows() {
        List<String[]> copy = new ArrayList<>();

        for (String[] row : rows) {
            copy.add(row.clone());
        }

        return Collections.unmodifiableList(copy);
    }

    // Identify columns containing only numeric values
    public List<Integer> getNumericColumnIndexes() {
        List<Integer> numericColumns = new ArrayList<>();

        for (int i = 0; i < headers.size(); i++) {
            try {
                getNumericColumn(i);
                numericColumns.add(i);
            } catch (IllegalArgumentException e) {
                // Skip non-numeric columns
            }
        }

        return numericColumns;
    }

}
