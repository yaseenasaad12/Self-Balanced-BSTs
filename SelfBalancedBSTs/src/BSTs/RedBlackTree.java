package BSTs;

import javafx.scene.paint.Color;

public class RedBlackTree<T extends Comparable<T>> implements Trees<T> {
    Node root;
    int size;
    Node TNULL; 
    
    private class Node {
        T key;
        Node parent;
        Node left, right;
        String colour;

        public Node(T key) {
            this.key = key;
            this.colour = "RED";
            this.left = this.right = null;
            this.parent = null;
        }
    }

    public RedBlackTree() {
        this.TNULL = new Node(null);
        this.TNULL.colour = "BLACK";
        this.TNULL.left = null;
        this.TNULL.right = null;
        this.root = TNULL;
        this.size = 0;
    }

    public void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    public void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    @Override
    public boolean insert(T key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        if (search(key)) {
            return false;
        }
        Node z = new Node(key);
        z.left = this.TNULL;
        z.right = this.TNULL;
        Node y = null;
        Node x = this.root;

        while (x != this.TNULL) {
            y = x;
            if (z.key.compareTo(x.key) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.parent = y;
        if (y == null) {
            this.root = z;
        } else if (z.key.compareTo(y.key) < 0) {
            y.left = z;
        } else {
            y.right = z;
        }
        
        insertFixup(z);
        this.size++;
        return true;
    }

    public void insertFixup(Node z) {
        while (z.parent != null && z.parent.colour.equals("RED")) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right;
                if (y.colour.equals("RED")) {
                    z.parent.colour = "BLACK";
                    y.colour = "BLACK";
                    z.parent.parent.colour = "RED";
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.colour = "BLACK";
                    z.parent.parent.colour = "RED";
                    rightRotate(z.parent.parent);
                }
            } else {
                Node y = z.parent.parent.left;
                if (y.colour.equals("RED")) {
                    z.parent.colour = "BLACK";
                    y.colour = "BLACK";
                    z.parent.parent.colour = "RED";
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.colour = "BLACK";
                    z.parent.parent.colour = "RED";
                    leftRotate(z.parent.parent);
                }
            }
            if(z == this.root) {
                break;
            }
        }
        this.root.colour = "BLACK";
    }

    @Override
    public boolean search(T key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return search(key, this.root);
    }

    public boolean search(T key, Node node) {
        if (node == this.TNULL || key.equals(node.key)) {
            return node != this.TNULL;
        }
        if (key.compareTo(node.key) < 0) {
            return search(key, node.left);
        } else {
            return search(key, node.right);
        }
    } 

    public void transplant(Node u, Node v) {
        if (u.parent == null) {
            this.root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;

    }   

    public Node getNode(T key) {
        Node node = this.root;
        while (node != this.TNULL) {
            if (key.equals(node.key)) {
                return node;
            } else if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    public Node minimum(Node node) {
        while (node.left != this.TNULL) {
            node = node.left;
        }
        return node;
    }

    @Override
    public boolean delete(T key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        if (!search(key)) {
            return false;
        }
        Node z = getNode(key);
        Node y = z;
        String yOriginalColour = y.colour;
        Node x;

        if (z.left == this.TNULL) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == this.TNULL) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColour = y.colour;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.colour = z.colour;
        }
        if (yOriginalColour.equals("BLACK")) {
            deleteFixup(x);
        }
        this.size--;
        return true;
    }

    public void deleteFixup(Node x) {
        while (x != this.root &&  x.colour.equals("BLACK")) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.colour.equals("RED")) {
                    w.colour = "BLACK";
                    x.parent.colour = "RED";
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.colour.equals("BLACK") && w.right.colour.equals("BLACK")) {
                    w.colour = "RED";
                    x = x.parent;
                } else {
                    if (w.right.colour.equals("BLACK")) {
                        w.left.colour = "BLACK";
                        w.colour = "RED";
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.colour = x.parent.colour;
                    x.parent.colour = "BLACK";
                    w.right.colour = "BLACK";
                    leftRotate(x.parent);
                    x = this.root;
                }
            } else {
                Node w = x.parent.left;
                if (w.colour.equals("RED")) {
                    w.colour = "BLACK";
                    x.parent.colour = "RED";
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.right.colour.equals("BLACK") &&  w.left.colour.equals("BLACK")) {
                    w.colour = "RED";
                    x = x.parent;
                } else {
                    if (w.left.colour.equals("BLACK")) {
                        w.right.colour = "BLACK";
                        w.colour = "RED";
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.colour = x.parent.colour;
                    x.parent.colour = "BLACK";
                    w.left.colour = "BLACK";
                    rightRotate(x.parent);
                    x = this.root;
                }
            }
        }

        x.colour = "BLACK";

    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int height() {
        return height(this.root);
    }

    public int height(Node node) {
        if (node == this.TNULL) {
            return 0;
        }
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    @Override
    public void printdictinory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printdictinory'");
    }
    
}

