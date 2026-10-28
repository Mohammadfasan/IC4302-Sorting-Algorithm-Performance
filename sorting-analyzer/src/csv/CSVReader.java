
package com.sortinganalyzer.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CSVReader {

    private final List<String> headers = new ArrayList<>();
    private final List<String[]> rows = new ArrayList<>();

    public void loadCSV(String filePath) throws IOException {
        headers.clear();
        rows.clear();

        if (filePath == null ||
                !filePath.toLowerCase().endsWith(".csv")) {
            throw new IOException("Please select a valid CSV file.");
        }

        try (BufferedReader reader =
                Files.newBufferedReader(Path.of(filePath))) {

            String line = reader.readLine();

            if (line == null || line.isBlank()) {
                throw new IOException("CSV file is empty.");
            }

            headers.addAll(
                Arrays.asList(line.split(",", -1))
            );

            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                String[] values = line.split(",", -1);

                if (values.length != headers.size()) {
                    throw new IOException(
                        "Invalid CSV format at line " + lineNumber
                    );
                }

                rows.add(values);
            }

            if (rows.isEmpty()) {
                throw new IOException(
                    "CSV file contains no data rows."
                );
            }
        } catch (IOException | RuntimeException e) {
            headers.clear();
            rows.clear();
            throw e;
        }
    }

    public List<String> getHeaders() {
        return Collections.unmodifiableList(headers);
    }

    public List<String[]> getRows() {
        List<String[]> copy = new ArrayList<>();

        for (String[] row : rows) {
            copy.add(row.clone());
        }

        return Collections.unmodifiableList(copy);
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return headers.size();
    }

    public double[] getNumericColumn(int columnIndex) {

        if (columnIndex < 0 ||
                columnIndex >= headers.size()) {
            throw new IllegalArgumentException(
                "Invalid column index."
            );
        }

        double[] values = new double[rows.size()];

        for (int i = 0; i < rows.size(); i++) {

            String value = rows.get(i)[columnIndex].trim();

            try {
                values[i] = Double.parseDouble(value);

                if (!Double.isFinite(values[i])) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                    "Non-numeric value at row " + (i + 2)
                );
            }
        }

        return values;
    }

    public List<Integer> getNumericColumnIndexes() {

        List<Integer> numericColumns = new ArrayList<>();

        for (int i = 0; i < headers.size(); i++) {
            try {
                getNumericColumn(i);
                numericColumns.add(i);
            } catch (IllegalArgumentException e) {
                // Ignore non-numeric columns
            }
        }

        return numericColumns;
    }
}
