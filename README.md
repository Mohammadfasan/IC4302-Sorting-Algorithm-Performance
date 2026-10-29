
# SortLab – Sorting Algorithm Performance Analyzer

A JavaFX-based desktop application developed to evaluate and compare the performance of five sorting algorithms using numeric data from CSV files.

**Course:** IC4302 – Programming III  
**Project:** Sorting Algorithm Performance Evaluation  
**University:** University of Colombo – Faculty of Technology  
**Technology:** Java 21, JavaFX, Apache Maven, JUnit 5

---

## 1. Project Overview

SortLab is a desktop application that helps users understand and compare the performance of different sorting algorithms.

Users can upload a CSV dataset, select a numeric column, execute five sorting algorithms, and compare their execution times through an interactive JavaFX dashboard.

The application also provides an Algorithm Explorer with explanations, complexity information, advantages, disadvantages, and sorting examples.

## 2. Main Features

### CSV Dataset Explorer

- Upload CSV datasets from a local computer.
- Display column names and dataset statistics.
- Identify numeric columns automatically.
- Select a numeric column for sorting.
- Preview the first 100 dataset rows.
- View numeric values from the selected column.
- Display error messages for invalid datasets.

### Sorting Algorithms

Five sorting algorithms are implemented independently:

1. Insertion Sort
2. Shell Sort
3. Merge Sort
4. Quick Sort
5. Heap Sort

Each algorithm sorts numeric values in ascending order.

### Performance Analysis

- Execute all five sorting algorithms.
- Use separate copies of the same dataset for comparison.
- Perform five warm-up runs per algorithm.
- Perform fifteen measured runs per algorithm.
- Calculate the median execution time.
- Display execution time in milliseconds and seconds.
- Identify the fastest algorithm based on the measured median time.
- Verify sorting results against Java's built-in sorting result.

### Performance Visualization

- Interactive bar chart comparing five algorithms.
- Individual execution-time result cards.
- Fastest algorithm highlighting.
- Dark and Light theme support.

### Algorithm Explorer

Provides information about each sorting algorithm:

- Description
- Best-case time complexity
- Average-case time complexity
- Worst-case time complexity
- Space complexity
- Stability
- Step-by-step explanation
- Advantages and disadvantages
- Sorting examples

## 3. Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Application development |
| JavaFX | Desktop graphical user interface |
| JavaFX CSS | Dark and Light themes |
| Apache Maven | Dependency management and build automation |
| JUnit 5 | Automated unit testing |
| Git | Version control |
| GitHub | Repository hosting and collaboration |

## 4. Sorting Algorithms and Complexity

| Algorithm | Best Case | Average Case | Worst Case | Extra Space |
|---|---|---|---|---|
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) |
| Shell Sort | Gap-dependent | Gap-dependent | O(n²) | O(1) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) average |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) |

Shell Sort uses a gap-halving sequence. Its performance depends on the input data and gap sequence.

## 5. Project Structure

```text
IC4302-Sorting-Algorithm-Performance/
├── README.md
└── sorting-analyzer/
    ├── pom.xml
    ├── datasets/
    │   ├── students.csv
    │   └── student1.csv
    └── src/
        ├── main/
        │   ├── java/com/sortinganalyzer/
        │   │   ├── App.java
        │   │   ├── algorithms/
        │   │   │   ├── InsertionSort.java
        │   │   │   ├── ShellSort.java
        │   │   │   ├── MergeSort.java
        │   │   │   ├── QuickSort.java
        │   │   │   └── HeapSort.java
        │   │   ├── csv/
        │   │   │   └── CSVReader.java
        │   │   ├── gui/
        │   │   │   ├── DatasetExplorer.java
        │   │   │   ├── SortLabDashboard.java
        │   │   │   ├── PerformanceResults.java
        │   │   │   └── AlgorithmExplorer.java
        │   │   └── performance/
        │   │       └── SortingBenchmark.java
        │   └── resources/styles/
        │       └── dashboard.css
        └── test/java/com/sortinganalyzer/
            ├── AppTest.java
            ├── algorithms/
            │   ├── InsertionSortTest.java
            │   ├── ShellSortTest.java
            │   ├── MergeSortTest.java
            │   ├── QuickSortTest.java
            │   └── HeapSortTest.java
            └── performance/
                └── SortingBenchmarkTest.java
```

## 6. Installation and Setup

### Requirements

Install the following software:

- Java Development Kit (JDK) 21
- Apache Maven
- Git
- JavaFX dependencies (managed automatically by Maven)

### Clone the Repository

```bash
git clone https://github.com/Mohammadfasan/IC4302-Sorting-Algorithm-Performance.git
```

Navigate to the Maven project:

```bash
cd IC4302-Sorting-Algorithm-Performance/sorting-analyzer
```

### Build the Application

```bash
mvn clean compile
```

### Run the Application

```bash
mvn javafx:run
```

The SortLab desktop application will launch.

## 7. How to Use

**Step 1:** Launch SortLab.

**Step 2:** Open the Dataset Explorer from the sidebar.

**Step 3:** Click Browse CSV and select a dataset.

**Step 4:** Choose a numeric column, such as Marks or Age.

**Step 5:** Open Performance Results.

**Step 6:** Click Run All Algorithms.

**Step 7:** View execution times, the comparison chart, and the fastest algorithm.

**Step 8:** Visit Algorithm Explorer to learn about each sorting algorithm.

## 8. Performance Measurement Methodology

SortLab uses `System.nanoTime()` to measure algorithm execution time.

For each algorithm:

1. Create a copy of the original numeric dataset.
2. Execute five warm-up runs.
3. Execute fifteen measured runs.
4. Record the execution time of each measured run.
5. Sort the recorded execution times.
6. Select the median execution time.
7. Convert the result into milliseconds and seconds.
8. Compare the median times of all five algorithms.

Only the sorting operation is timed. Array copying and result verification are performed outside the measured section.

All algorithms receive the same original input values.

**Note:** Performance results depend on dataset size, input order, JVM behaviour, and computer hardware. Small datasets may produce very short and variable execution times.

## 9. Testing

The project uses JUnit 5 for automated testing.

Tests cover:

- Sorting correctness
- Duplicate values
- Negative numbers
- Empty arrays
- Already-sorted arrays
- Execution-time calculations
- Original dataset preservation
- Fastest algorithm identification
- Invalid benchmark input handling

### Run Automated Tests

```bash
mvn clean test
```

### Latest Verified Test Result

```text
Tests run: 30
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

Manual GUI testing was also performed using normal CSV datasets, larger datasets, and invalid CSV inputs.

## 10. Dataset Testing

The application has been manually tested with CSV datasets containing:

- 5 rows
- 1,000 rows
- 5,000 rows
- 10,000 rows

The application supports the selection and analysis of numeric CSV columns.

## 11. GitHub Collaboration

The project uses Git and GitHub feature branches for development.

| Branch | Development Area |
|---|---|
| `feature/csv-upload` | CSV uploading and dataset exploration |
| `feature/sorting-algorithms` | Sorting algorithms and algorithm tests |
| `feature/gui-performance` | Performance analysis, GUI integration and visualization |
| `main` | Reviewed and merged project code |

Feature branches were integrated through GitHub pull requests.

## 12. Team

Developed as a group assignment for IC4302 – Programming III at the University of Colombo, Faculty of Technology.

GitHub Contributors:

- [Mohammadfasan](https://github.com/Mohammadfasan)
- [asjadahd1234](https://github.com/asjadahd1234)

Individual development contributions should be explained according to the work actually completed, reviewed, and tested by each member.

## 13. Future Improvements

Possible future enhancements include:

- Export performance results to CSV or PDF.
- Save previous benchmark results.
- Compare multiple datasets in one chart.
- Add more sorting algorithms.
- Provide animated sorting visualizations.
- Support additional dataset formats.

## 14. Academic Purpose

This application was developed for educational purposes to demonstrate sorting algorithm implementation, execution-time measurement, JavaFX application development, automated testing, and GitHub collaboration.

---

**SortLab – Sorting Algorithm Performance Analyzer**

IC4302 Programming III | University of Colombo
