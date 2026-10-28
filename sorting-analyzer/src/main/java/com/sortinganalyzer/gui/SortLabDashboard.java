
package com.sortinganalyzer.gui;

import com.sortinganalyzer.csv.CSVReader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SortLabDashboard {

    private boolean darkMode = true;

    private BorderPane root;
    private Scene scene;
    private VBox sidebar;
    private StackPane mainContent;
    private Button themeButton;

    private Label rowsValue;
    private Label numericValue;

    private DatasetExplorer datasetExplorer;

    public void show(Stage stage) {

        root = new BorderPane();

        // Sidebar
        sidebar = new VBox(15);
        sidebar.setPadding(new Insets(25));
        sidebar.setPrefWidth(230);
        sidebar.getStyleClass().add("sidebar");

        Label logo = new Label("◈ SortLab");
        logo.setStyle(
            "-fx-font-size: 26px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #10B981;"
        );

        Button dashboardBtn = new Button("▦  Dashboard");
        Button datasetBtn = new Button("▤  Dataset Explorer");
        Button resultsBtn = new Button("▥  Performance Results");
        Button algorithmsBtn = new Button("◉  Algorithm Explorer");

        Button[] buttons = {
            dashboardBtn, datasetBtn, resultsBtn, algorithmsBtn
        };

        for (Button button : buttons) {
            button.setMaxWidth(Double.MAX_VALUE);
            button.setAlignment(Pos.CENTER_LEFT);
        }

        sidebar.getChildren().add(logo);
        sidebar.getChildren().addAll(buttons);
        // Header
        Label appTitle = new Label("Sorting Performance Analyzer");
        appTitle.setStyle(
            "-fx-font-size: 21px; -fx-font-weight: bold;"
        );

        themeButton = new Button("☀ Light Mode");
        themeButton.setOnAction(e -> toggleTheme());

        HBox header = new HBox(15, appTitle, themeButton);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20));

        HBox.setHgrow(appTitle, Priority.ALWAYS);

        // Main content
        mainContent = new StackPane();
        mainContent.setPadding(new Insets(10));
        mainContent.getStyleClass().add("content");

        VBox centerLayout = new VBox(5, header, mainContent);
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        root.setLeft(sidebar);
        root.setCenter(centerLayout);

        // Create Dataset Explorer only once
        datasetExplorer = new DatasetExplorer(
            this::updateDashboardStatistics
        );

        // Sidebar navigation
        dashboardBtn.setOnAction(e -> showDashboard());
        datasetBtn.setOnAction(e -> showDatasetExplorer());

        resultsBtn.setOnAction(e ->
            showPlaceholder(
                "Performance Results",
                "Sorting performance chart will be added next."
            )
        );

        algorithmsBtn.setOnAction(e ->
            showPlaceholder(
                "Algorithm Explorer",
                "Five sorting algorithm details will be added next."
            )
        );

        scene = new Scene(root, 1150, 750);

        scene.getStylesheets().add(
            getClass().getResource("/styles/dashboard.css")
                .toExternalForm()
        );

        applyTheme();
        showDashboard();

        stage.setTitle("SortLab - Sorting Performance Analyzer");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    private VBox statCard(String title, Label value) {

        Label name = new Label(title);
        name.setStyle("-fx-font-size: 14px;");

        value.setStyle(
            "-fx-font-size: 30px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #10B981;"
        );

        VBox card = new VBox(12, name, value);
        card.setPadding(new Insets(25));
        card.setPrefWidth(230);
        card.getStyleClass().add("stat-card");

        return card;
    }

    private void showDashboard() {

        Label title = new Label("Dashboard Overview");
        title.setStyle(
            "-fx-font-size: 26px; -fx-font-weight: bold;"
        );

        rowsValue = new Label(
            String.valueOf(datasetExplorer.getCSVReader().getRowCount())
        );

        numericValue = new Label(
            String.valueOf(
                datasetExplorer.getCSVReader()
                    .getNumericColumnIndexes().size()
            )
        );

        Label algorithmsValue = new Label("5");

        HBox stats = new HBox(
            20,
            statCard("Dataset Rows", rowsValue),
            statCard("Numeric Columns", numericValue),
            statCard("Sorting Algorithms", algorithmsValue)
        );

        Label description = new Label(
            "Upload a CSV dataset to start comparing sorting algorithms."
        );

        Button uploadButton = new Button("Upload CSV Dataset");
        uploadButton.setOnAction(e -> showDatasetExplorer());

        VBox dashboard = new VBox(
            25, title, stats, description, uploadButton
        );

        dashboard.setPadding(new Insets(25));
        dashboard.getStyleClass().add("content");

        mainContent.getChildren().setAll(dashboard);
    }

   
private void showDatasetExplorer() {

    ScrollPane scroll = new ScrollPane(datasetExplorer);

    scroll.setFitToWidth(true);
    scroll.setFitToHeight(true);

    scroll.getStyleClass().add("content");

    scroll.setStyle(
        "-fx-background-color: transparent;" +
        "-fx-background-insets: 0;"
    );

    mainContent.getChildren().setAll(scroll);
}


    private void updateDashboardStatistics(CSVReader csvReader) {

        if (rowsValue != null) {
            rowsValue.setText(
                String.valueOf(csvReader.getRowCount())
            );
        }

        if (numericValue != null) {
            numericValue.setText(
                String.valueOf(
                    csvReader.getNumericColumnIndexes().size()
                )
            );
        }
    }

    private void showPlaceholder(String title, String message) {

        Label heading = new Label(title);
        heading.setStyle(
            "-fx-font-size: 26px; -fx-font-weight: bold;"
        );

        Label description = new Label(message);

        VBox content = new VBox(20, heading, description);
        content.setPadding(new Insets(25));
        content.getStyleClass().add("content");

        mainContent.getChildren().setAll(content);
    }

    private void toggleTheme() {
        darkMode = !darkMode;

        themeButton.setText(
            darkMode ? "☀ Light Mode" : "☾ Dark Mode"
        );

        applyTheme();
    }

    private void applyTheme() {
        root.getStyleClass().removeAll("dark", "light");
        root.getStyleClass().add(
            darkMode ? "dark" : "light"
        );
    }
}
