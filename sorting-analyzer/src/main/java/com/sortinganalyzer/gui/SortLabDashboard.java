
package com.sortinganalyzer.gui;

import com.sortinganalyzer.csv.CSVReader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

    // Application pages
    private DatasetExplorer datasetExplorer;
    private PerformanceResults performanceResults;
    private AlgorithmExplorer algorithmExplorer;

    public void show(Stage stage) {

        root = new BorderPane();

        // ========== SIDEBAR ==========

        sidebar = new VBox(15);
        sidebar.setPadding(new Insets(25));
        sidebar.setPrefWidth(230);
        sidebar.getStyleClass().add("sidebar");

        Label logo = new Label("◈ SortLab");
        logo.setStyle(
                "-fx-font-size: 26px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #10B981;"
        );

        Button dashboardBtn =
                new Button("▦  Dashboard");

        Button datasetBtn =
                new Button("▤  Dataset Explorer");

        Button resultsBtn =
                new Button("▥  Performance Results");

        Button algorithmsBtn =
                new Button("◉  Algorithm Explorer");

        Button[] buttons = {
                dashboardBtn,
                datasetBtn,
                resultsBtn,
                algorithmsBtn
        };

        for (Button button : buttons) {
            button.setMaxWidth(Double.MAX_VALUE);
            button.setAlignment(Pos.CENTER_LEFT);
        }

        sidebar.getChildren().add(logo);
        sidebar.getChildren().addAll(buttons);

        // ========== HEADER ==========

        Label appTitle = new Label(
                "Sorting Performance Analyzer"
        );

        appTitle.setStyle(
                "-fx-font-size: 21px;"
                + "-fx-font-weight: bold;"
        );

        themeButton = new Button("☀ Light Mode");
        themeButton.setOnAction(e -> toggleTheme());

        HBox header = new HBox(
                15,
                appTitle,
                themeButton
        );

        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20));

        HBox.setHgrow(appTitle, Priority.ALWAYS);

        // ========== MAIN CONTENT ==========

        mainContent = new StackPane();
        mainContent.setPadding(new Insets(10));
        mainContent.getStyleClass().add("content");

        VBox centerLayout = new VBox(
                5,
                header,
                mainContent
        );

        VBox.setVgrow(
                mainContent,
                Priority.ALWAYS
        );

        root.setLeft(sidebar);
        root.setCenter(centerLayout);

        // ========== INITIALIZE PAGES ==========

        // Create Dataset Explorer only once
        datasetExplorer = new DatasetExplorer(
                this::updateDashboardStatistics
        );

        // Performance Results uses selected CSV data
        performanceResults = new PerformanceResults(
                datasetExplorer
        );

        // Algorithm information and examples
        algorithmExplorer = new AlgorithmExplorer();

        // ========== SIDEBAR NAVIGATION ==========

        dashboardBtn.setOnAction(
                e -> showDashboard()
        );

        datasetBtn.setOnAction(
                e -> showDatasetExplorer()
        );

        resultsBtn.setOnAction(
                e -> showPerformanceResults()
        );

        algorithmsBtn.setOnAction(
                e -> showAlgorithmExplorer()
        );

        // ========== APPLICATION SCENE ==========

        scene = new Scene(root, 1150, 750);

        scene.getStylesheets().add(
                getClass()
                        .getResource("/styles/dashboard.css")
                        .toExternalForm()
        );

        applyTheme();

        // Default page
        showDashboard();

        stage.setTitle(
                "SortLab - Sorting Performance Analyzer"
        );

        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    // ========== STATISTICS CARD ==========

    private VBox statCard(
            String title,
            Label value) {

        Label name = new Label(title);

        name.setStyle(
                "-fx-font-size: 14px;"
        );

        value.setStyle(
                "-fx-font-size: 30px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #10B981;"
        );

        VBox card = new VBox(
                12,
                name,
                value
        );

        card.setPadding(new Insets(25));
        card.setPrefWidth(230);
        card.getStyleClass().add("stat-card");

        return card;
    }

    // ========== DASHBOARD PAGE ==========

    private void showDashboard() {

        Label title = new Label(
                "Dashboard Overview"
        );

        title.setStyle(
                "-fx-font-size: 26px;"
                + "-fx-font-weight: bold;"
        );

        rowsValue = new Label(
                String.valueOf(
                        datasetExplorer
                                .getCSVReader()
                                .getRowCount()
                )
        );

        numericValue = new Label(
                String.valueOf(
                        datasetExplorer
                                .getCSVReader()
                                .getNumericColumnIndexes()
                                .size()
                )
        );

        Label algorithmsValue = new Label("5");

        HBox stats = new HBox(
                20,
                statCard(
                        "Dataset Rows",
                        rowsValue
                ),
                statCard(
                        "Numeric Columns",
                        numericValue
                ),
                statCard(
                        "Sorting Algorithms",
                        algorithmsValue
                )
        );

        stats.setAlignment(Pos.CENTER_LEFT);

        Label description = new Label(
                "Upload a CSV dataset to compare "
                + "five sorting algorithms."
        );

        description.setWrapText(true);

        Button uploadButton = new Button(
                "Upload CSV Dataset"
        );

        uploadButton.setOnAction(
                e -> showDatasetExplorer()
        );

        Button resultsButton = new Button(
                "View Performance Results"
        );

        resultsButton.setOnAction(
                e -> showPerformanceResults()
        );

        HBox actions = new HBox(
                15,
                uploadButton,
                resultsButton
        );

        VBox dashboard = new VBox(
                25,
                title,
                stats,
                description,
                actions
        );

        dashboard.setPadding(new Insets(25));
        dashboard.getStyleClass().add("content");

        mainContent.getChildren().setAll(
                dashboard
        );
    }

    // ========== DATASET EXPLORER PAGE ==========

    private void showDatasetExplorer() {

        ScrollPane scroll = createPageScroll(
                datasetExplorer
        );

        scroll.setFitToHeight(true);

        mainContent.getChildren().setAll(
                scroll
        );
    }

    // ========== PERFORMANCE RESULTS PAGE ==========

    private void showPerformanceResults() {

        ScrollPane scroll = createPageScroll(
                performanceResults
        );

        mainContent.getChildren().setAll(
                scroll
        );
    }

    // ========== ALGORITHM EXPLORER PAGE ==========

    private void showAlgorithmExplorer() {

        ScrollPane scroll = createPageScroll(
                algorithmExplorer
        );

        mainContent.getChildren().setAll(
                scroll
        );
    }

    // ========== SHARED SCROLLPANE STYLE ==========

    private ScrollPane createPageScroll(
            VBox page) {

        ScrollPane scroll = new ScrollPane(page);

        scroll.setFitToWidth(true);

        scroll.getStyleClass().add("content");

        scroll.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-background-insets: 0;"
        );

        return scroll;
    }

    // ========== UPDATE DASHBOARD STATS ==========

    private void updateDashboardStatistics(
            CSVReader csvReader) {

        if (rowsValue != null) {
            rowsValue.setText(
                    String.valueOf(
                            csvReader.getRowCount()
                    )
            );
        }

        if (numericValue != null) {
            numericValue.setText(
                    String.valueOf(
                            csvReader
                                    .getNumericColumnIndexes()
                                    .size()
                    )
            );
        }
    }

    // ========== DARK / LIGHT MODE ==========

    private void toggleTheme() {

        darkMode = !darkMode;

        themeButton.setText(
                darkMode
                        ? "☀ Light Mode"
                        : "☾ Dark Mode"
        );

        applyTheme();
    }

    private void applyTheme() {

        root.getStyleClass().removeAll(
                "dark",
                "light"
        );

        root.getStyleClass().add(
                darkMode ? "dark" : "light"
        );
    }
}
