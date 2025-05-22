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
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        assertEquals(0, rbt.height());
        rbt.insert(10);
        assertEquals(1, rbt.height());
        rbt.insert(20);
        rbt.insert(5);
        assertTrue(rbt.height() > 0);
    }
}