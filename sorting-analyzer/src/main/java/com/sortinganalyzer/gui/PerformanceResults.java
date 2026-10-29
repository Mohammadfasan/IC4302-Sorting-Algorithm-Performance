
package com.sortinganalyzer.gui;

import com.sortinganalyzer.performance.SortingBenchmark;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Locale;

public class PerformanceResults extends VBox {

    private final DatasetExplorer datasetExplorer;

    private final Label statusLabel =
            new Label("Upload a CSV and select a numeric column.");

    private final Label fastestLabel =
            new Label("Fastest Algorithm: Not calculated");

    private final VBox resultsContainer = new VBox(12);

    private final Button runButton =
            new Button("Run All Algorithms");

    private final BarChart<String, Number> chart;

    public PerformanceResults(DatasetExplorer datasetExplorer) {

        this.datasetExplorer = datasetExplorer;

        setSpacing(20);
        setPadding(new Insets(25));
        setMaxWidth(Double.MAX_VALUE);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Sorting Algorithms");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Execution Time (ms)");
        yAxis.setForceZeroInRange(true);

        chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Sorting Performance Comparison");
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setCategoryGap(20);
        chart.setPrefHeight(350);
        chart.setVisible(false);
        chart.setManaged(false);

        buildUI();
    }

    private void buildUI() {

        Label title = new Label("Performance Results");
        title.setStyle(
                "-fx-font-size: 26px; -fx-font-weight: bold;"
        );

        Label subtitle = new Label(
                "Compare the execution time of five sorting algorithms."
        );

        Label instruction = new Label(
                "Select a numeric column in Dataset Explorer, "
                + "then run the analysis."
        );
        instruction.setWrapText(true);

        runButton.setStyle(
                "-fx-background-color: #10B981;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 12 24;"
                + "-fx-background-radius: 8;"
        );

        runButton.setOnAction(e -> runBenchmark());

        VBox controlCard = new VBox(
                15,
                instruction,
                runButton,
                statusLabel
        );

        controlCard.setPadding(new Insets(20));
        controlCard.getStyleClass().add("stat-card");

        fastestLabel.setStyle(
                "-fx-font-size: 19px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #10B981;"
        );

        VBox fastestCard = new VBox(10, fastestLabel);
        fastestCard.setPadding(new Insets(20));
        fastestCard.getStyleClass().add("stat-card");

        Label detailsTitle =
                new Label("Individual Algorithm Results");

        detailsTitle.setStyle(
                "-fx-font-size: 19px; -fx-font-weight: bold;"
        );

        resultsContainer.setSpacing(12);

        getChildren().addAll(
                title,
                subtitle,
                controlCard,
                fastestCard,
                chart,
                detailsTitle,
                resultsContainer
        );
    }

    private void runBenchmark() {

        int columnIndex =
                datasetExplorer.getSelectedColumnIndex();

        if (columnIndex < 0) {
            showError(
                    "Please upload a CSV dataset "
                    + "and select a numeric column first."
            );
            return;
        }

        double[] values;

        try {
            values = datasetExplorer.getCSVReader()
                    .getNumericColumn(columnIndex);
        } catch (Exception ex) {
            showError(ex.getMessage());
            return;
        }

        if (values.length == 0) {
            showError("The selected numeric column is empty.");
            return;
        }

        statusLabel.setText("Running sorting algorithms...");
        runButton.setDisable(true);

        // Run benchmark outside the JavaFX UI thread.
        Task<List<SortingBenchmark.Result>> task =
                new Task<>() {

            @Override
            protected List<SortingBenchmark.Result> call() {
                return SortingBenchmark.run(values);
            }
        };

        task.setOnSucceeded(event -> {
            runButton.setDisable(false);

            List<SortingBenchmark.Result> results =
                    task.getValue();

            displayResults(results, values.length);
        });

        task.setOnFailed(event -> {
            runButton.setDisable(false);
            statusLabel.setText("Performance analysis failed.");

            Throwable error = task.getException();

            showError(error == null
                    ? "Unknown error"
                    : error.getMessage());
        });

        Thread worker = new Thread(task, "sortlab-benchmark");
        worker.setDaemon(true);
        worker.start();
    }

    private void displayResults(
            List<SortingBenchmark.Result> results,
            int numberOfValues) {

        resultsContainer.getChildren().clear();
        chart.getData().clear();

        SortingBenchmark.Result fastest =
                SortingBenchmark.findFastest(results);

        fastestLabel.setText(
                "Fastest Algorithm: "
                + fastest.algorithm()
                + " | "
                + format(fastest.milliseconds())
                + " ms"
        );

        statusLabel.setText(
                "Analysis completed successfully! Values: "
                + numberOfValues
        );

        XYChart.Series<String, Number> series =
                new XYChart.Series<>();

        for (SortingBenchmark.Result result : results) {

            // Add execution time to chart.
            XYChart.Data<String, Number> data =
                    new XYChart.Data<>(
                            result.algorithm(),
                            result.milliseconds()
                    );

            series.getData().add(data);

            Label name = new Label(result.algorithm());
            name.setStyle(
                    "-fx-font-weight: bold;"
                    + "-fx-font-size: 15px;"
            );

            Label time = new Label(
                    "Milliseconds: "
                    + format(result.milliseconds())
                    + " ms"
                    + "    |    Seconds: "
                    + format(result.seconds())
                    + " s"
            );

            VBox resultCard = new VBox(8, name, time);

            if (result == fastest) {
                Label winner = new Label("FASTEST");
                winner.setStyle(
                        "-fx-text-fill: #10B981;"
                        + "-fx-font-weight: bold;"
                );
                resultCard.getChildren().add(winner);
            }

            resultCard.setPadding(new Insets(15));
            resultCard.getStyleClass().add("stat-card");

            resultsContainer.getChildren().add(resultCard);
        }

        chart.getData().add(series);
        chart.setManaged(true);
        chart.setVisible(true);
    }

    private String format(double value) {
        return String.format(Locale.US, "%.9f", value);
    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Performance Analysis Error");
        alert.setHeaderText("Unable to run benchmark");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
