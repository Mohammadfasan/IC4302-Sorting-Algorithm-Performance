
package com.sortinganalyzer.gui;

import com.sortinganalyzer.csv.CSVReader;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DatasetExplorer extends VBox {

    private final CSVReader csvReader = new CSVReader();

    private final ComboBox<String> columnCombo = new ComboBox<>();
    private final TableView<ObservableList<String>> dataTable =
            new TableView<>();

    private final Label fileLabel = new Label("No CSV file selected");
    private final Label statusLabel = new Label("Upload a CSV to begin.");
    private final Label rowsLabel = new Label("Rows: 0");
    private final Label columnsLabel = new Label("Columns: 0");
    private final Label numericLabel = new Label("Numeric Columns: 0");

    private final List<Integer> numericIndexes = new ArrayList<>();

    private final Consumer<CSVReader> onDatasetLoaded;

    public DatasetExplorer(Consumer<CSVReader> onDatasetLoaded) {
        this.onDatasetLoaded = onDatasetLoaded;

        setSpacing(20);
        setPadding(new Insets(25));
        setMaxWidth(Double.MAX_VALUE);

        buildUI();
    }

    private void buildUI() {

        Label title = new Label("Dataset Explorer");
        title.setStyle(
            "-fx-font-size: 25px; -fx-font-weight: bold;"
        );

        Label subtitle = new Label(
            "Upload your CSV dataset and select a numeric column."
        );

        VBox heading = new VBox(5, title, subtitle);

        // Upload section
        Label uploadTitle = new Label("CSV Dataset");
        uploadTitle.setStyle(
            "-fx-font-size: 17px; -fx-font-weight: bold;"
        );

        Button browseButton = new Button("Browse CSV");
        browseButton.setOnAction(e -> uploadCSV());

        HBox fileRow = new HBox(20, fileLabel, browseButton);
        fileRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(fileLabel, Priority.ALWAYS);

        VBox uploadCard = new VBox(
            12, uploadTitle, fileRow, statusLabel
        );
        uploadCard.getStyleClass().add("stat-card");
        uploadCard.setPadding(new Insets(20));

        // Statistics
        HBox stats = new HBox(
            20, rowsLabel, columnsLabel, numericLabel
        );
        stats.setAlignment(Pos.CENTER_LEFT);

        // Column selector
        Label selectTitle = new Label("Select Numeric Column");
        selectTitle.setStyle("-fx-font-weight: bold;");

        columnCombo.setPromptText("Select a numeric column");
        columnCombo.setPrefWidth(250);
        columnCombo.setDisable(true);

        Button previewButton = new Button("Show Selected Values");
        previewButton.setOnAction(e -> showSelectedValues());

        HBox selector = new HBox(
            15, columnCombo, previewButton
        );
        selector.setAlignment(Pos.CENTER_LEFT);

        // Dataset table
        Label tableTitle = new Label("Dataset Preview");
        tableTitle.setStyle(
            "-fx-font-size: 18px; -fx-font-weight: bold;"
        );

        dataTable.setPlaceholder(
            new Label("Upload a CSV to preview the dataset.")
        );
        dataTable.setPrefHeight(300);
        dataTable.setColumnResizePolicy(
            TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );

        VBox.setVgrow(dataTable, Priority.ALWAYS);

        getChildren().addAll(
            heading,
            uploadCard,
            stats,
            selectTitle,
            selector,
            tableTitle,
            dataTable
        );
    }

    private void uploadCSV() {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select CSV Dataset");

        chooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter(
                "CSV Files (*.csv)", "*.csv"
            )
        );

        File file = chooser.showOpenDialog(
            getScene().getWindow()
        );

        if (file == null) {
            return;
        }

        try {
            csvReader.loadCSV(file.getAbsolutePath());

            List<String> headers = csvReader.getHeaders();

            List<Integer> validIndexes =
                csvReader.getNumericColumnIndexes();

            if (validIndexes.isEmpty()) {
                throw new IllegalArgumentException(
                    "Dataset has no valid numeric columns."
                );
            }

            numericIndexes.clear();
            numericIndexes.addAll(validIndexes);

            columnCombo.getItems().clear();

            for (int index : numericIndexes) {
                columnCombo.getItems().add(
                    headers.get(index)
                );
            }

            columnCombo.setDisable(false);
            columnCombo.getSelectionModel().selectFirst();

            fileLabel.setText(file.getName());
            statusLabel.setText("CSV loaded successfully!");

            rowsLabel.setText(
                "Rows: " + csvReader.getRowCount()
            );

            columnsLabel.setText(
                "Columns: " + csvReader.getColumnCount()
            );

            numericLabel.setText(
                "Numeric Columns: " + numericIndexes.size()
            );

            populateTable();

            if (onDatasetLoaded != null) {
                onDatasetLoaded.accept(csvReader);
            }

        } catch (Exception ex) {

            numericIndexes.clear();
            columnCombo.getItems().clear();
            columnCombo.setDisable(true);

            fileLabel.setText("No valid CSV loaded");
            statusLabel.setText("Upload failed.");
            rowsLabel.setText("Rows: 0");
            columnsLabel.setText("Columns: 0");
            numericLabel.setText("Numeric Columns: 0");
            dataTable.getColumns().clear();
            dataTable.getItems().clear();

            new Alert(
                Alert.AlertType.ERROR,
                "CSV Error: " + ex.getMessage(),
                ButtonType.OK
            ).showAndWait();
        }
    }

    private void populateTable() {

        dataTable.getColumns().clear();
        dataTable.getItems().clear();

        List<String> headers = csvReader.getHeaders();

        for (int i = 0; i < headers.size(); i++) {

            final int columnIndex = i;

            TableColumn<ObservableList<String>, String> column =
                    new TableColumn<>(headers.get(i));

            column.setCellValueFactory(data -> {
                ObservableList<String> row = data.getValue();

                String value = columnIndex < row.size()
                        ? row.get(columnIndex)
                        : "";

                return new ReadOnlyStringWrapper(value);
            });

            column.setPrefWidth(150);
            dataTable.getColumns().add(column);
        }

        List<String[]> rows = csvReader.getRows();

        int limit = Math.min(rows.size(), 100);

        for (int i = 0; i < limit; i++) {

            ObservableList<String> row =
                FXCollections.observableArrayList(rows.get(i));

            dataTable.getItems().add(row);
        }
    }

    private void showSelectedValues() {

        int selected = columnCombo.getSelectionModel()
                .getSelectedIndex();

        if (selected < 0) {
            new Alert(
                Alert.AlertType.WARNING,
                "Please select a numeric column."
            ).showAndWait();
            return;
        }

        int actualIndex = numericIndexes.get(selected);

        double[] values =
            csvReader.getNumericColumn(actualIndex);

        StringBuilder result = new StringBuilder();

        int limit = Math.min(values.length, 20);

        for (int i = 0; i < limit; i++) {
            result.append(values[i]).append("\n");
        }

        if (values.length > limit) {
            result.append("... and ")
                  .append(values.length - limit)
                  .append(" more values");
        }

        TextArea textArea = new TextArea(result.toString());
        textArea.setEditable(false);
        textArea.setPrefSize(350, 300);

        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Numeric Column Values");
        dialog.setHeaderText(
            "Selected Column: " + columnCombo.getValue()
        );
        dialog.getDialogPane().setContent(textArea);
        dialog.showAndWait();
    }

    public CSVReader getCSVReader() {
        return csvReader;
    }

    public int getSelectedColumnIndex() {
        int selected = columnCombo.getSelectionModel()
                .getSelectedIndex();

        if (selected < 0) {
            return -1;
        }

        return numericIndexes.get(selected);
    }
}

