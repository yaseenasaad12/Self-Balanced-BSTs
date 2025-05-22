package Tests;

import BSTs.RedBlackTree;
import BSTs.AVL;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

public class ComparisonsTests {



    @Test
    public void testTimeAndHeightComparison() {
        final int N = 10000;
        int[] data = new int[N];
        Random rand = new Random(42);
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

        System.out.println("RedBlackTree insertion time (ms): " + (rbtTime / 1_000_000));
        System.out.println("AVLTree insertion time (ms): " + (avlTime / 1_000_000));
        System.out.println("RedBlackTree height: " + rbt.height());
        System.out.println("AVLTree height: " + avl.height());

        // Optionally, assert that both trees have the same size
        assertEquals(rbt.size(), avl.size());

        // You can also assert that heights are reasonable (not required)
        assertTrue(rbt.height() > 0);
        assertTrue(avl.height() > 0);
    }
}