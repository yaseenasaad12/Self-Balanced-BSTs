package Tests;
import BSTs.RedBlackTree;
import org.junit.Test;
import static org.junit.Assert.*;

public class RBTtests {

    @Test
    public void testInsertAndSearch() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        assertTrue(rbt.insert(10));
        assertTrue(rbt.insert(20));
        assertTrue(rbt.insert(5));
        assertFalse(rbt.insert(10)); // duplicate
        assertTrue(rbt.search(10));
        assertTrue(rbt.search(20));
        assertTrue(rbt.search(5));
        assertFalse(rbt.search(15));
    }

    @Test
    public void testDelete() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(10);
        rbt.insert(20);
        rbt.insert(5);
        assertTrue(rbt.delete(10));
        assertFalse(rbt.search(10));
        assertFalse(rbt.delete(10)); // already deleted
        assertTrue(rbt.delete(5));
        assertTrue(rbt.delete(20));
        assertEquals(0, rbt.size());
    }

    @Test
    public void testSize() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        assertEquals(0, rbt.size());
        rbt.insert(1);
        rbt.insert(2);
        assertEquals(2, rbt.size());
        rbt.delete(1);
        assertEquals(1, rbt.size());
    }

    @Test
    public void testHeight() {

            final int N = 1000;
            int[] data = new int[N];
            java.util.Random rand = new java.util.Random(123);
            for (int i = 0; i < N; i++) {
                data[i] = rand.nextInt();
            }
        
            RedBlackTree<Integer> rbt = new RedBlackTree<>();
            for (int value : data) {
                rbt.insert(value);
            }
        
            int rbtHeight = rbt.height();

            // Theoretical max height for RBT: 2*log2(N+1)
            double rbtMax = 2 * (Math.log(N + 1) / Math.log(2));

            assertTrue("RBT height too large: " + rbtHeight, rbtHeight <= rbtMax + 1);
        }
    
    @Test
    public void testInsertionAndDeletionCorrectness() {
        final int N = 2000;
        java.util.Random rand = new java.util.Random(456);
        java.util.Set<Integer> set = new java.util.TreeSet<>();
        RedBlackTree<Integer> rbt = new RedBlackTree<>();

        for (int i = 0; i < N; i++) {
            int num = rand.nextInt(1000);
            set.add(num);
            rbt.insert(num);
        }

        java.util.List<Integer> inorder = new java.util.ArrayList<>();
        rbt.inorderTraversal(x -> inorder.add(x));
        java.util.List<Integer> sorted = new java.util.ArrayList<>(set);
        assertEquals(sorted, inorder);

        int count = 0;
        for (Integer num : sorted) {
            if (count++ % 2 == 0) {
                rbt.delete(num);
                set.remove(num);
            }
        }
        inorder.clear();
        rbt.inorderTraversal(x -> inorder.add(x));
        sorted = new java.util.ArrayList<>(set);
        assertEquals(sorted, inorder);
    }
    
}