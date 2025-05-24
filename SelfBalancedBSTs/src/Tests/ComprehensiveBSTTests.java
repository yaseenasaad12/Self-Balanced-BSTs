package Tests;

import BSTs.RedBlackTree;
import BSTs.AVL;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class ComprehensiveBSTTests {
    
    private static FileWriter csvWriter;
    private static final String CSV_FILE = "bst_performance_results.csv";
    
    @BeforeClass
    public static void setUp() throws IOException {
        csvWriter = new FileWriter(CSV_FILE);
        csvWriter.append("Test_Type,Data_Size,Data_Pattern,RBT_Insert_Time(ms),AVL_Insert_Time(ms),")
                 .append("RBT_Search_Time(ms),AVL_Search_Time(ms),RBT_Delete_Time(ms),AVL_Delete_Time(ms),")
                 .append("RBT_Height,AVL_Height,RBT_Memory_Efficiency,AVL_Memory_Efficiency\n");
    }
    
    @AfterClass
    public static void tearDown() throws IOException {
        if (csvWriter != null) {
            csvWriter.close();
        }
        System.out.println("\n=== PERFORMANCE ANALYSIS COMPLETE ===");
        System.out.println("Results saved to: " + CSV_FILE);
        System.out.println("Use this data for your report analysis.");
    }

    // Comprehensive size testing
    @Test
    public void testPerformance_SmallDataset() {
        runComprehensiveTest(100, "Small Dataset");
    }

    @Test
    public void testPerformance_MediumDataset() {
        runComprehensiveTest(1000, "Medium Dataset");
    }

    @Test
    public void testPerformance_LargeDataset() {
        runComprehensiveTest(10000, "Large Dataset");
    }

    @Test
    public void testPerformance_VeryLargeDataset() {
        runComprehensiveTest(50000, "Very Large Dataset");
    }

    @Test
    public void testPerformance_ExtraLargeDataset() {
        runComprehensiveTest(100000, "Extra Large Dataset");
    }

    // Different data patterns
    @Test
    public void testWorstCase_SortedData() {
        testWithDataPattern_Sorted(10000);
    }

    @Test
    public void testWorstCase_ReverseSortedData() {
        testWithDataPattern_ReverseSorted(10000);
    }

    @Test
    public void testBestCase_RandomData() {
        testWithDataPattern_Random(10000);
    }

    @Test
    public void testSpecialCase_DuplicateHeavyData() {
        testWithDataPattern_DuplicateHeavy(10000);
    }

    // Scalability analysis
    @Test
    public void testScalabilityAnalysis() {
        System.out.println("\n=== SCALABILITY ANALYSIS ===");
        int[] sizes = {500, 1000, 2000, 5000, 10000, 20000, 50000};
        
        for (int size : sizes) {
            System.out.println("\n--- Testing Size: " + size + " ---");
            runComprehensiveTest(size, "Scalability_Test");
        }
    }

    // Operation-specific performance tests
    @Test
    public void testInsertionPerformance() {
        System.out.println("\n=== INSERTION PERFORMANCE ANALYSIS ===");
        testSpecificOperation("INSERT", 25000);
    }

    @Test
    public void testSearchPerformance() {
        System.out.println("\n=== SEARCH PERFORMANCE ANALYSIS ===");
        testSpecificOperation("SEARCH", 25000);
    }

    @Test
    public void testDeletionPerformance() {
        System.out.println("\n=== DELETION PERFORMANCE ANALYSIS ===");
        testSpecificOperation("DELETE", 25000);
    }

    private void runComprehensiveTest(int size, String testType) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPREHENSIVE TEST: " + testType + " (N = " + size + ")");
        System.out.println("=".repeat(60));

        // Test with random data
        int[] randomData = generateRandomData(size);
        PerformanceResult randomResult = performFullTest(randomData, "Random");
        
        System.out.println("\n--- RANDOM DATA RESULTS ---");
        printDetailedResults(randomResult, size);
        writeToCSV(testType, size, "Random", randomResult);

        // Verify tree properties
        verifyTreeProperties(randomResult.rbt, randomResult.avl, size);
    }

    private void testWithDataPattern_Sorted(int size) {
        System.out.println("\n=== SORTED DATA PATTERN TEST ===");
        int[] sortedData = generateSortedData(size);
        PerformanceResult result = performFullTest(sortedData, "Sorted");
        
        printDetailedResults(result, size);
        writeToCSV("Pattern_Test", size, "Sorted", result);
        
        System.out.println("\nIMPACT ANALYSIS:");
        System.out.println("- Sorted data represents worst-case for unbalanced BSTs");
        System.out.println("- Both RBT and AVL should handle this efficiently");
    }

    private void testWithDataPattern_ReverseSorted(int size) {
        System.out.println("\n=== REVERSE SORTED DATA PATTERN TEST ===");
        int[] reverseData = generateReverseSortedData(size);
        PerformanceResult result = performFullTest(reverseData, "Reverse_Sorted");
        
        printDetailedResults(result, size);
        writeToCSV("Pattern_Test", size, "Reverse_Sorted", result);
    }

    private void testWithDataPattern_Random(int size) {
        System.out.println("\n=== RANDOM DATA PATTERN TEST ===");
        int[] randomData = generateRandomData(size);
        PerformanceResult result = performFullTest(randomData, "Random");
        
        printDetailedResults(result, size);
        writeToCSV("Pattern_Test", size, "Random", result);
    }

    private void testWithDataPattern_DuplicateHeavy(int size) {
        System.out.println("\n=== DUPLICATE HEAVY DATA PATTERN TEST ===");
        int[] duplicateData = generateDuplicateHeavyData(size);
        PerformanceResult result = performFullTest(duplicateData, "Duplicate_Heavy");
        
        printDetailedResults(result, size);
        writeToCSV("Pattern_Test", size, "Duplicate_Heavy", result);
        
        System.out.println("\nDUPLICATE HANDLING ANALYSIS:");
        System.out.println("- Tests how trees handle many duplicate values");
        System.out.println("- Important for real-world scenarios");
    }

    private void testSpecificOperation(String operation, int size) {
        int[] data = generateRandomData(size);
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        AVL<Integer> avl = new AVL<>();

        switch (operation) {
            case "INSERT":
                testInsertionOnly(rbt, avl, data);
                break;
            case "SEARCH":
                // Pre-populate trees
                for (int value : data) {
                    rbt.insert(value);
                    avl.insert(value);
                }
                testSearchOnly(rbt, avl, data);
                break;
            case "DELETE":
                // Pre-populate trees
                for (int value : data) {
                    rbt.insert(value);
                    avl.insert(value);
                }
                testDeletionOnly(rbt, avl, data);
                break;
        }
    }

    private PerformanceResult performFullTest(int[] data, String pattern) {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        AVL<Integer> avl = new AVL<>();

        // Measure insertion time
        long rbtInsertTime = measureInsertionTime(rbt, data);
        long avlInsertTime = measureInsertionTime(avl, data);

        // Measure search time
        long rbtSearchTime = measureSearchTime(rbt, data);
        long avlSearchTime = measureSearchTime(avl, data);

        // Measure deletion time (on copies)
        RedBlackTree<Integer> rbtCopy = copyTree(rbt, data);
        AVL<Integer> avlCopy = copyTree(avl, data);
        long rbtDeleteTime = measureDeletionTime(rbtCopy, data);
        long avlDeleteTime = measureDeletionTime(avlCopy, data);

        return new PerformanceResult(
            rbtInsertTime, avlInsertTime,
            rbtSearchTime, avlSearchTime,
            rbtDeleteTime, avlDeleteTime,
            rbt.height(), avl.height(),
            rbt, avl
        );
    }

    private long measureInsertionTime(RedBlackTree<Integer> rbt, int[] data) {
        long start = System.nanoTime();
        for (int value : data) {
            rbt.insert(value);
        }
        return System.nanoTime() - start;
    }

    private long measureInsertionTime(AVL<Integer> avl, int[] data) {
        long start = System.nanoTime();
        for (int value : data) {
            avl.insert(value);
        }
        return System.nanoTime() - start;
    }

    private long measureSearchTime(RedBlackTree<Integer> rbt, int[] data) {
        long start = System.nanoTime();
        for (int value : data) {
            rbt.search(value);
        }
        return System.nanoTime() - start;
    }

    private long measureSearchTime(AVL<Integer> avl, int[] data) {
        long start = System.nanoTime();
        for (int value : data) {
            avl.search(value);
        }
        return System.nanoTime() - start;
    }

    private long measureDeletionTime(RedBlackTree<Integer> rbt, int[] data) {
        // Shuffle for realistic deletion pattern
        List<Integer> deleteOrder = new ArrayList<>();
        for (int value : data) deleteOrder.add(value);
        Collections.shuffle(deleteOrder);

        long start = System.nanoTime();
        for (int value : deleteOrder) {
            rbt.delete(value);
        }
        return System.nanoTime() - start;
    }

    private long measureDeletionTime(AVL<Integer> avl, int[] data) {
        List<Integer> deleteOrder = new ArrayList<>();
        for (int value : data) deleteOrder.add(value);
        Collections.shuffle(deleteOrder);

        long start = System.nanoTime();
        for (int value : deleteOrder) {
            avl.delete(value);
        }
        return System.nanoTime() - start;
    }

    // Data generation methods
    private int[] generateRandomData(int size) {
        int[] data = new int[size];
        Random rand = new Random(42);
        for (int i = 0; i < size; i++) {
            data[i] = rand.nextInt(Integer.MAX_VALUE);
        }
        return data;
    }

    private int[] generateSortedData(int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = i;
        }
        return data;
    }

    private int[] generateReverseSortedData(int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = size - i;
        }
        return data;
    }

    private int[] generateDuplicateHeavyData(int size) {
        int[] data = new int[size];
        Random rand = new Random(42);
        for (int i = 0; i < size; i++) {
            data[i] = rand.nextInt(size / 10); // Many duplicates
        }
        return data;
    }

    private void printDetailedResults(PerformanceResult result, int size) {
        System.out.println("\n--- DETAILED PERFORMANCE RESULTS ---");
        System.out.printf("Data Size: %,d elements\n", size);
        
        System.out.println("\nINSERTION PERFORMANCE:");
        System.out.printf("  Red-Black Tree: %,d ms\n", result.rbtInsertTime / 1_000_000);
        System.out.printf("  AVL Tree:       %,d ms\n", result.avlInsertTime / 1_000_000);
        System.out.printf("  Speed Ratio:    %.2fx %s\n", 
            calculateSpeedRatio(result.rbtInsertTime, result.avlInsertTime),
            result.rbtInsertTime < result.avlInsertTime ? "(RBT faster)" : "(AVL faster)");

        System.out.println("\nSEARCH PERFORMANCE:");
        System.out.printf("  Red-Black Tree: %,d ms\n", result.rbtSearchTime / 1_000_000);
        System.out.printf("  AVL Tree:       %,d ms\n", result.avlSearchTime / 1_000_000);
        System.out.printf("  Speed Ratio:    %.2fx %s\n", 
            calculateSpeedRatio(result.rbtSearchTime, result.avlSearchTime),
            result.rbtSearchTime < result.avlSearchTime ? "(RBT faster)" : "(AVL faster)");

        System.out.println("\nDELETION PERFORMANCE:");
        System.out.printf("  Red-Black Tree: %,d ms\n", result.rbtDeleteTime / 1_000_000);
        System.out.printf("  AVL Tree:       %,d ms\n", result.avlDeleteTime / 1_000_000);
        System.out.printf("  Speed Ratio:    %.2fx %s\n", 
            calculateSpeedRatio(result.rbtDeleteTime, result.avlDeleteTime),
            result.rbtDeleteTime < result.avlDeleteTime ? "(RBT faster)" : "(AVL faster)");

        System.out.println("\nTREE STRUCTURE ANALYSIS:");
        System.out.printf("  Red-Black Tree Height: %d\n", result.rbtHeight);
        System.out.printf("  AVL Tree Height:       %d\n", result.avlHeight);
        System.out.printf("  Theoretical Min Height: %d\n", (int)Math.ceil(Math.log(size + 1) / Math.log(2)));
        System.out.printf("  Height Efficiency RBT: %.2f%%\n", 
            ((double)(int)Math.ceil(Math.log(size + 1) / Math.log(2)) / result.rbtHeight) * 100);
        System.out.printf("  Height Efficiency AVL: %.2f%%\n", 
            ((double)(int)Math.ceil(Math.log(size + 1) / Math.log(2)) / result.avlHeight) * 100);

        printPerformanceAnalysis(result, size);
    }

    private void printPerformanceAnalysis(PerformanceResult result, int size) {
        System.out.println("\n--- PERFORMANCE ANALYSIS ---");
        
        long totalRBT = result.rbtInsertTime + result.rbtSearchTime + result.rbtDeleteTime;
        long totalAVL = result.avlInsertTime + result.avlSearchTime + result.avlDeleteTime;
        
        System.out.printf("Total Operations Time:\n");
        System.out.printf("  Red-Black Tree: %,d ms\n", totalRBT / 1_000_000);
        System.out.printf("  AVL Tree:       %,d ms\n", totalAVL / 1_000_000);
        
        if (totalRBT < totalAVL) {
            System.out.printf("  Overall Winner: Red-Black Tree (%.2fx faster)\n", (double)totalAVL / totalRBT);
        } else {
            System.out.printf("  Overall Winner: AVL Tree (%.2fx faster)\n", (double)totalRBT / totalAVL);
        }

        // Performance per operation
        System.out.println("\nPer-Operation Performance (microseconds):");
        System.out.printf("  RBT Insert/Search/Delete: %.2f / %.2f / %.2f\n",
            (double)result.rbtInsertTime / size / 1000, 
            (double)result.rbtSearchTime / size / 1000,
            (double)result.rbtDeleteTime / size / 1000);
        System.out.printf("  AVL Insert/Search/Delete: %.2f / %.2f / %.2f\n",
            (double)result.avlInsertTime / size / 1000,
            (double)result.avlSearchTime / size / 1000,
            (double)result.avlDeleteTime / size / 1000);
    }

    private double calculateSpeedRatio(long time1, long time2) {
        return Math.max(time1, time2) / (double)Math.min(time1, time2);
    }

    private void verifyTreeProperties(RedBlackTree<Integer> rbt, AVL<Integer> avl, int expectedSize) {
        System.out.println("\n--- CORRECTNESS VERIFICATION ---");
        assertEquals("Tree sizes should match", rbt.size(), avl.size());
        assertTrue("RBT height should be positive", rbt.height() > 0);
        assertTrue("AVL height should be positive", avl.height() > 0);
        assertTrue("AVL should be more balanced", avl.height() <= rbt.height());
        System.out.println("✓ All correctness tests passed");
    }

    private void writeToCSV(String testType, int size, String pattern, PerformanceResult result) {
        try {
            csvWriter.append(String.format("%s,%d,%s,%d,%d,%d,%d,%d,%d,%d,%d,%.2f,%.2f\n",
                testType, size, pattern,
                result.rbtInsertTime / 1_000_000, result.avlInsertTime / 1_000_000,
                result.rbtSearchTime / 1_000_000, result.avlSearchTime / 1_000_000,
                result.rbtDeleteTime / 1_000_000, result.avlDeleteTime / 1_000_000,
                result.rbtHeight, result.avlHeight,
                ((double)(int)Math.ceil(Math.log(size + 1) / Math.log(2)) / result.rbtHeight) * 100,
                ((double)(int)Math.ceil(Math.log(size + 1) / Math.log(2)) / result.avlHeight) * 100
            ));
            csvWriter.flush();
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    // Helper methods for specific operation testing
    private void testInsertionOnly(RedBlackTree<Integer> rbt, AVL<Integer> avl, int[] data) {
        System.out.println("Testing insertion performance with " + data.length + " elements...");
        
        long rbtTime = measureInsertionTime(new RedBlackTree<>(), data);
        long avlTime = measureInsertionTime(new AVL<>(), data);
        
        System.out.printf("RBT Insertion: %,d ms\n", rbtTime / 1_000_000);
        System.out.printf("AVL Insertion: %,d ms\n", avlTime / 1_000_000);
        System.out.printf("Performance Ratio: %.2fx\n", calculateSpeedRatio(rbtTime, avlTime));
    }

    private void testSearchOnly(RedBlackTree<Integer> rbt, AVL<Integer> avl, int[] data) {
        System.out.println("Testing search performance with " + data.length + " elements...");
        
        long rbtTime = measureSearchTime(rbt, data);
        long avlTime = measureSearchTime(avl, data);
        
        System.out.printf("RBT Search: %,d ms\n", rbtTime / 1_000_000);
        System.out.printf("AVL Search: %,d ms\n", avlTime / 1_000_000);
        System.out.printf("Performance Ratio: %.2fx\n", calculateSpeedRatio(rbtTime, avlTime));
    }

    private void testDeletionOnly(RedBlackTree<Integer> rbt, AVL<Integer> avl, int[] data) {
        System.out.println("Testing deletion performance with " + data.length + " elements...");
        
        RedBlackTree<Integer> rbtCopy = copyTree(rbt, data);
        AVL<Integer> avlCopy = copyTree(avl, data);
        
        long rbtTime = measureDeletionTime(rbtCopy, data);
        long avlTime = measureDeletionTime(avlCopy, data);
        
        System.out.printf("RBT Deletion: %,d ms\n", rbtTime / 1_000_000);
        System.out.printf("AVL Deletion: %,d ms\n", avlTime / 1_000_000);
        System.out.printf("Performance Ratio: %.2fx\n", calculateSpeedRatio(rbtTime, avlTime));
    }

    private RedBlackTree<Integer> copyTree(RedBlackTree<Integer> original, int[] data) {
        RedBlackTree<Integer> copy = new RedBlackTree<>();
        for (int value : data) {
            copy.insert(value);
        }
        return copy;
    }

    private AVL<Integer> copyTree(AVL<Integer> original, int[] data) {
        AVL<Integer> copy = new AVL<>();
        for (int value : data) {
            copy.insert(value);
        }
        return copy;
    }

    // Inner class to hold performance results
    private static class PerformanceResult {
        final long rbtInsertTime, avlInsertTime;
        final long rbtSearchTime, avlSearchTime;
        final long rbtDeleteTime, avlDeleteTime;
        final int rbtHeight, avlHeight;
        final RedBlackTree<Integer> rbt;
        final AVL<Integer> avl;

        PerformanceResult(long rbtInsertTime, long avlInsertTime,
                         long rbtSearchTime, long avlSearchTime,
                         long rbtDeleteTime, long avlDeleteTime,
                         int rbtHeight, int avlHeight,
                         RedBlackTree<Integer> rbt, AVL<Integer> avl) {
            this.rbtInsertTime = rbtInsertTime;
            this.avlInsertTime = avlInsertTime;
            this.rbtSearchTime = rbtSearchTime;
            this.avlSearchTime = avlSearchTime;
            this.rbtDeleteTime = rbtDeleteTime;
            this.avlDeleteTime = avlDeleteTime;
            this.rbtHeight = rbtHeight;
            this.avlHeight = avlHeight;
            this.rbt = rbt;
            this.avl = avl;
        }
    }
}