package Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import BSTs.AVL;


public class AVLtests {
    private AVL<Integer> avl;

    @Before
    public void setUp() {
        avl = new AVL<>();
    }

    @Test
    public void testInsertAndSearch() {
        assertTrue(avl.insert(10));
        assertTrue(avl.insert(20));
        assertTrue(avl.insert(5));
        assertTrue(avl.search(10));
        assertTrue(avl.search(20));
        assertTrue(avl.search(5));
        assertFalse(avl.search(99));
        assertFalse(avl.insert(10)); // Duplicate insert
    }

    @Test
    public void testDelete() {
        avl.insert(15);
        avl.insert(10);
        avl.insert(20);
        assertTrue(avl.delete(10));
        assertFalse(avl.search(10));
        assertFalse(avl.delete(10)); // Already deleted
        assertTrue(avl.delete(15));
        assertTrue(avl.delete(20));
        assertEquals(0, avl.size());
    }

    @Test
    public void testSize() {
        assertEquals(0, avl.size());
        avl.insert(1);
        avl.insert(2);
        avl.insert(3);
        assertEquals(3, avl.size());
        avl.delete(2);
        assertEquals(2, avl.size());
    }

    @Test
    public void testHeight() {

            final int N = 1000;
            int[] data = new int[N];
            java.util.Random rand = new java.util.Random(123);
            for (int i = 0; i < N; i++) {
                data[i] = rand.nextInt();
            }
            BSTs.AVL<Integer> avl = new BSTs.AVL<>();
        
            for (int value : data) {
                avl.insert(value);
            }
            int avlHeight = avl.height();
        
            // Theoretical max height for AVL: ~1.44*log2(N+2)-0.328
            double avlMax = 1.44 * (Math.log(N + 2) / Math.log(2)) - 0.328;
        
            assertTrue("AVL height too large: " + avlHeight, avlHeight <= avlMax + 1);
        }

    @Test
    public void testInsertionAndDeletionCorrectness() {
        final int N = 2000;
        java.util.Random rand = new java.util.Random(456);
        java.util.Set<Integer> set = new java.util.TreeSet<>();
        AVL<Integer> avl = new AVL<>();

        for (int i = 0; i < N; i++) {
            int num = rand.nextInt(1000);
            set.add(num);
            avl.insert(num);
        }

        java.util.List<Integer> inorder = new java.util.ArrayList<>();
        avl.inorderTraversal(x -> inorder.add(x));
        java.util.List<Integer> sorted = new java.util.ArrayList<>(set);
        assertEquals(sorted, inorder);

        int count = 0;
        for (Integer num : sorted) {
            if (count++ % 2 == 0) {
                avl.delete(num);
                set.remove(num);
            }
        }
        inorder.clear();
        avl.inorderTraversal(x -> inorder.add(x));
        sorted = new java.util.ArrayList<>(set);
        assertEquals(sorted, inorder);
    }

    @Test
    public void testInsertDuplicates() {
        AVL<Integer> avl = new AVL<>();
        assertTrue(avl.insert(10));
        assertFalse(avl.insert(10)); // Duplicate insert should return false
        assertEquals(1, avl.size());
    }

    @Test
    public void testDeleteNonExistent() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(5);
        avl.insert(10);
        assertFalse(avl.delete(20)); // Deleting non-existent element
        assertEquals(2, avl.size());
    }

    @Test
    public void testInorderAfterRandomOperations() {
        AVL<Integer> avl = new AVL<>();
        java.util.Set<Integer> set = new java.util.TreeSet<>();
        java.util.Random rand = new java.util.Random(789);

        // Insert random numbers
        for (int i = 0; i < 100; i++) {
            int num = rand.nextInt(200);
            avl.insert(num);
            set.add(num);
        }

        // Delete some numbers
        int count = 0;
        for (Integer num : new java.util.ArrayList<>(set)) {
            if (count++ % 3 == 0) {
                avl.delete(num);
                set.remove(num);
            }
        }

        // Compare in-order traversal with sorted set
        java.util.List<Integer> inorder = new java.util.ArrayList<>();
        avl.inorderTraversal(x -> inorder.add(x));
        java.util.List<Integer> sorted = new java.util.ArrayList<>(set);
        assertEquals(sorted, inorder);
    }

    @Test
    public void testHeightAfterSequentialInsertions() {
        AVL<Integer> avl = new AVL<>();
        int N = 100;
        for (int i = 1; i <= N; i++) {
            avl.insert(i);
        }
        // AVL tree should remain balanced, height should be O(log N)
        int height = avl.height();
        double maxAllowed = 1.44 * (Math.log(N + 2) / Math.log(2)) - 0.328 + 1;
        assertTrue("AVL height too large after sequential insertions: " + height, height <= maxAllowed);
    }
}