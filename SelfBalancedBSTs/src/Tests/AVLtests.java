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
    public void testHeightAndBalance() {
        avl.insert(30);
        avl.insert(20);
        avl.insert(10); // Causes right rotation
        assertTrue(avl.height() <= 2);
        avl.insert(25);
        avl.insert(5);
        avl.insert(35);
        avl.insert(40); // Causes left rotation
        assertTrue(avl.height() <= 3);
    }

    @Test
    public void testInorderTraversal() {
        avl.insert(3);
        avl.insert(1);
        avl.insert(2);
        avl.insert(5);
        avl.insert(4);

        // Capture in-order output
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        avl.inorder();
        System.setOut(System.out);

        String output = out.toString().trim().replaceAll("\\s+", " ");
        assertEquals("1 2 3 4 5", output);
    }
}