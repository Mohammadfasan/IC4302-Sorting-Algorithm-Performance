
package com.sortinganalyzer.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class AlgorithmExplorer extends VBox {

    private final ComboBox<String> algorithmSelector =
            new ComboBox<>();

    private final VBox detailsContainer = new VBox(18);

    private final Map<String, AlgorithmInfo> algorithms =
            new LinkedHashMap<>();

    private record AlgorithmInfo(
            String description,
            String best,
            String average,
            String worst,
            String space,
            String stable,
            String howItWorks,
            String advantages,
            String disadvantages,
            String example
    ) {}

    public AlgorithmExplorer() {

        setSpacing(22);
        setPadding(new Insets(25));
        setMaxWidth(Double.MAX_VALUE);

        loadAlgorithmInformation();
        buildUI();
    }

    private void loadAlgorithmInformation() {

        algorithms.put("Insertion Sort", new AlgorithmInfo(
                "Insertion Sort builds a sorted list one element at a time.",
                "O(n)",
                "O(n²)",
                "O(n²)",
                "O(1)",
                "Yes",
                "1. Start from the second element.\n"
                        + "2. Compare it with previous elements.\n"
                        + "3. Move larger elements to the right.\n"
                        + "4. Insert the current element in its correct position.\n"
                        + "5. Repeat until all values are sorted.",
                "Simple to understand and implement.\n"
                        + "Fast for small or nearly sorted datasets.\n"
                        + "Uses very little additional memory.",
                "Slow for large unsorted datasets.\n"
                        + "Requires many comparisons and shifts.",
                "Input:  [5, 3, 8, 1]\n"
                        + "Step 1: [3, 5, 8, 1]\n"
                        + "Step 2: [3, 5, 8, 1]\n"
                        + "Step 3: [1, 3, 5, 8]\n"
                        + "Output: [1, 3, 5, 8]"
        ));

        algorithms.put("Shell Sort", new AlgorithmInfo(
                "Shell Sort improves Insertion Sort by comparing "
                        + "elements separated by a gap.",
                "Depends on gap sequence",
                "Depends on gap sequence",
                "O(n²)",
                "O(1)",
                "No",
                "1. Choose an initial gap, usually n/2.\n"
                        + "2. Compare elements separated by the gap.\n"
                        + "3. Perform insertion sorting within gaps.\n"
                        + "4. Reduce the gap by half.\n"
                        + "5. Continue until the gap becomes 1.",
                "Usually faster than Insertion Sort for medium datasets.\n"
                        + "Uses little extra memory.\n"
                        + "Easy to implement.",
                "Performance depends on the gap sequence.\n"
                        + "Not stable.\n"
                        + "Can be slower than O(n log n) algorithms.",
                "Input:  [8, 5, 3, 1]\n"
                        + "Gap 2: [3, 1, 8, 5]\n"
                        + "Gap 1: [1, 3, 5, 8]\n"
                        + "Output: [1, 3, 5, 8]"
        ));

        algorithms.put("Merge Sort", new AlgorithmInfo(
                "Merge Sort uses divide and conquer to split "
                        + "and merge sorted parts.",
                "O(n log n)",
                "O(n log n)",
                "O(n log n)",
                "O(n)",
                "Yes",
                "1. Divide the array into two halves.\n"
                        + "2. Continue dividing until each part has one value.\n"
                        + "3. Compare values from the two halves.\n"
                        + "4. Merge them in sorted order.\n"
                        + "5. Continue until the full array is sorted.",
                "Predictable O(n log n) performance.\n"
                        + "Stable sorting.\n"
                        + "Good for large datasets.",
                "Requires extra memory.\n"
                        + "Recursive processing adds overhead.",
                "Input:  [8, 3, 5, 1]\n"
                        + "Split: [8, 3] | [5, 1]\n"
                        + "Split: [8] [3] | [5] [1]\n"
                        + "Merge: [3, 8] | [1, 5]\n"
                        + "Output: [1, 3, 5, 8]"
        ));

        algorithms.put("Quick Sort", new AlgorithmInfo(
                "Quick Sort selects a pivot and partitions "
                        + "the array around it.",
                "O(n log n)",
                "O(n log n)",
                "O(n²)",
                "O(log n) average",
                "No",
                "1. Select a pivot element.\n"
                        + "2. Place smaller values before the pivot.\n"
                        + "3. Place larger values after the pivot.\n"
                        + "4. Repeat for the left partition.\n"
                        + "5. Repeat for the right partition.",
                "Usually fast for large datasets.\n"
                        + "In-place partitioning.\n"
                        + "Good average performance.",
                "Worst-case time is O(n²).\n"
                        + "Not stable.\n"
                        + "Uses stack memory for recursion.",
                "Input:  [8, 3, 5, 1]\n"
                        + "Pivot: 5\n"
                        + "Smaller: [3, 1]\n"
                        + "Larger: [8]\n"
                        + "Output: [1, 3, 5, 8]"
        ));

        algorithms.put("Heap Sort", new AlgorithmInfo(
                "Heap Sort uses a binary heap to repeatedly "
                        + "move the largest value to the end.",
                "O(n log n)",
                "O(n log n)",
                "O(n log n)",
                "O(1)",
                "No",
                "1. Build a max heap.\n"
                        + "2. Move the largest value to the end.\n"
                        + "3. Reduce the heap size.\n"
                        + "4. Restore the heap property.\n"
                        + "5. Repeat until the array is sorted.",
                "Guaranteed O(n log n) time.\n"
                        + "Uses constant additional memory.\n"
                        + "Works well for large arrays.",
                "Not stable.\n"
                        + "Can be slower than Quick Sort in practice.\n"
                        + "Heap operations are less intuitive.",
                "Input:  [8, 3, 5, 1]\n"
                        + "Max Heap: [8, 3, 5, 1]\n"
                        + "Move 8: [5, 3, 1, 8]\n"
                        + "Move 5: [3, 1, 5, 8]\n"
                        + "Output: [1, 3, 5, 8]"
        ));
    }

    private void buildUI() {

        Label title = new Label("Algorithm Explorer");
        title.setStyle(
                "-fx-font-size: 26px; -fx-font-weight: bold;"
        );

        Label subtitle = new Label(
                "Explore the five sorting algorithms, "
                        + "their complexities and sorting steps."
        );
        subtitle.setWrapText(true);

        Label selectLabel = new Label("Select Sorting Algorithm");
        selectLabel.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold;"
        );

        algorithmSelector.getItems().addAll(
                algorithms.keySet()
        );

        algorithmSelector.setMaxWidth(Double.MAX_VALUE);
        algorithmSelector.setPrefWidth(350);

        algorithmSelector.setOnAction(e -> {
            String selected = algorithmSelector.getValue();
            if (selected != null) {
                displayAlgorithm(selected);
            }
        });

        VBox selectionCard = new VBox(
                12,
                selectLabel,
                algorithmSelector
        );

        selectionCard.setPadding(new Insets(20));
        selectionCard.getStyleClass().add("stat-card");

        detailsContainer.setMaxWidth(Double.MAX_VALUE);

        getChildren().addAll(
                title,
                subtitle,
                selectionCard,
                detailsContainer
        );

        algorithmSelector.getSelectionModel().selectFirst();
    }

    private void displayAlgorithm(String algorithmName) {

        AlgorithmInfo info = algorithms.get(algorithmName);

        if (info == null) {
            return;
        }

        detailsContainer.getChildren().clear();

        Label algorithmTitle = new Label(algorithmName);
        algorithmTitle.setStyle(
                "-fx-font-size: 23px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #10B981;"
        );

        Label description = new Label(info.description());
        description.setWrapText(true);

        VBox introduction = new VBox(
                12,
                algorithmTitle,
                description
        );

        introduction.setPadding(new Insets(20));
        introduction.getStyleClass().add("stat-card");

        VBox bestCard = createInfoCard(
                "Best Case", info.best()
        );

        VBox averageCard = createInfoCard(
                "Average Case", info.average()
        );

        VBox worstCard = createInfoCard(
                "Worst Case", info.worst()
        );

        HBox complexityRow = new HBox(
                15,
                bestCard,
                averageCard,
                worstCard
        );

        for (VBox card : new VBox[]{
                bestCard, averageCard, worstCard
        }) {
            HBox.setHgrow(card, Priority.ALWAYS);
            card.setMaxWidth(Double.MAX_VALUE);
        }

        VBox propertiesCard = createSection(
                "Additional Properties",
                "Space Complexity: " + info.space()
                        + "\nStable Sort: " + info.stable()
        );

        VBox stepsCard = createSection(
                "How It Works",
                info.howItWorks()
        );

        VBox advantagesCard = createSection(
                "Advantages",
                info.advantages()
        );

        VBox disadvantagesCard = createSection(
                "Disadvantages",
                info.disadvantages()
        );

        HBox prosCons = new HBox(
                15, advantagesCard, disadvantagesCard
        );

        HBox.setHgrow(advantagesCard, Priority.ALWAYS);
        HBox.setHgrow(disadvantagesCard, Priority.ALWAYS);

        advantagesCard.setMaxWidth(Double.MAX_VALUE);
        disadvantagesCard.setMaxWidth(Double.MAX_VALUE);

        VBox exampleCard = createSection(
                "Sorting Example",
                info.example()
        );

        detailsContainer.getChildren().addAll(
                introduction,
                complexityRow,
                propertiesCard,
                stepsCard,
                prosCons,
                exampleCard
        );
    }

    private VBox createInfoCard(
            String heading,
            String value) {

        Label title = new Label(heading);
        title.setStyle("-fx-font-weight: bold;");

        Label result = new Label(value);
        result.setStyle(
                "-fx-font-size: 20px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #10B981;"
        );

        VBox card = new VBox(12, title, result);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("stat-card");

        return card;
    }

    private VBox createSection(
            String heading,
            String text) {

        Label title = new Label(heading);
        title.setStyle(
                "-fx-font-size: 17px;"
                        + "-fx-font-weight: bold;"
        );

        Label content = new Label(text);
        content.setWrapText(true);

        VBox card = new VBox(14, title, content);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("stat-card");

        return card;
    }
}
