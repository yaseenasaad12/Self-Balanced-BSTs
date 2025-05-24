package Tests;

import BSTs.RedBlackTree;
import BSTs.AVL;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

public class ComparisonsTests {

    @Test
    public void testTimeAndHeightComparison_Small() {
        compareTreesWithSize(100);
    }

    @Test
    public void testTimeAndHeightComparison_Medium() {
        compareTreesWithSize(1000);
    }

    @Test
    public void testTimeAndHeightComparison_Large() {
        compareTreesWithSize(10000);
    }

     @Test
    public void testTimeAndHeightComparison_superLarge() {
        compareTreesWithSize(100000);
    }
     @Test
    public void testTimeAndHeightComparison_megaLarge() {
        compareTreesWithSize(1000000);
    }

    private void compareTreesWithSize(int N) {
        int[] data = new int[N];
        Random rand = new Random(42 + N); // Different seed for each size
        for (int i = 0; i < N; i++) {
            data[i] = rand.nextInt();
        }

        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        AVL<Integer> avl = new AVL<>();

        // Time RedBlackTree insertion
        long startRBT = System.nanoTime();
        for (int value : data) {
            rbt.insert(value);
        }
        long endRBT = System.nanoTime();
        long rbtTime = endRBT - startRBT;

        // Time AVLTree insertion
        long startAVL = System.nanoTime();
        for (int value : data) {
            avl.insert(value);
        }
        long endAVL = System.nanoTime();
        long avlTime = endAVL - startAVL;

        System.out.println("N = " + N);
        System.out.println("RedBlackTree insertion time (ms): " + (rbtTime / 1_000_000));
        System.out.println("AVLTree insertion time (ms): " + (avlTime / 1_000_000));
        System.out.println("RedBlackTree height: " + rbt.height());
        System.out.println("AVLTree height: " + avl.height());

        assertEquals(rbt.size(), avl.size());
        assertTrue(rbt.height() > 0);
        assertTrue(avl.height() > 0);
    }
}