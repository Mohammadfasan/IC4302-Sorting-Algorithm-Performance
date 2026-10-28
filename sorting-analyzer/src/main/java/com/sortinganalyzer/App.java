
package com.sortinganalyzer;

import com.sortinganalyzer.gui.SortLabDashboard;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        new SortLabDashboard().show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
