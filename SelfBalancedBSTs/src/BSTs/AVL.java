package BSTs;

public class AVL<T extends Comparable<T>> implements Trees<T> {
    private Node root;
    private int size;

    private class Node {
        T key;
        Node left, right;
        int height;

        public Node(T key) {
            this.key = key;
            this.height = 0; 
            this.left = this.right = null;
        }
    }

    public AVL() {
        this.root = null;
        this.size = 0;
    }

    // Get height of a node (handles null case)
    private int height(Node node) {
        if (node == null) return -1;
        return node.height;
    }

    // Get balance factor of a node
    private int getBalance(Node node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    // Update height of a node based on its children (the longest path from the root to a leaf node)
    private void updateHeight(Node node) {
        if (node != null) {
            node.height = Math.max(height(node.left), height(node.right)) + 1;
        }
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        updateHeight(y);
        updateHeight(x);

        // Return new root
        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        updateHeight(x);
        updateHeight(y);

        // Return new root
        return y;
    }

    @Override
    public boolean insert(T key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        // Check if key already exists
        if (search(key)) {
            return false;
        }
        
        root = insertNode(root, key);
        size++;
        return true;
    }

    private Node insertNode(Node node, T key) {
        // Standard BST insertion
        if (node == null) {
            return new Node(key);
        }

        int compareResult = key.compareTo(node.key);
        
        if (compareResult < 0) {
            node.left = insertNode(node.left, key);
        } else if (compareResult > 0) {
            node.right = insertNode(node.right, key);
        } else {
            return node;
        }

        updateHeight(node);

        int balance = getBalance(node);

        // Left heavy case
        if (balance > 1) {
            if (key.compareTo(node.left.key) < 0) {
                // Left-Left Case
                return rightRotate(node);
            } else {
                // Left-Right Case
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }

        // Right heavy case
        if (balance < -1) {
            if (key.compareTo(node.right.key) > 0) {
                // Right-Right Case
                return leftRotate(node);
            } else {
                // Right-Left Case
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        // Return the unchanged node if no rotation needed
        return node;
    }

    // Search for a key in the tree
    @Override
    public boolean search(T key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return searchNode(root, key);
    }

    // Helper method for search
    private boolean searchNode(Node node, T key) {
        if (node == null) {
            return false; // Key not found
        }

        int compareResult = key.compareTo(node.key);
        
        if (compareResult == 0) {
            return true; // Key found
        } else if (compareResult < 0) {
            return searchNode(node.left, key); // Search in left subtree
        } else {
            return searchNode(node.right, key); // Search in right subtree
        }
    }

    // Delete a key from the tree
    @Override
    public boolean delete(T key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        // Check if key exists
        if (!search(key)) {
            return false;
        }
        
        root = deleteNode(root, key);
        size--;
        return true;
    }

    // Helper method for deletion
    private Node deleteNode(Node node, T key) {
        if (node == null) {
            return null;
        }

        int compareResult = key.compareTo(node.key);
        
        if (compareResult < 0) {
            node.left = deleteNode(node.left, key);
        } else if (compareResult > 0) {
            node.right = deleteNode(node.right, key);
        } else {
            // Node with the key to be deleted found

            // Node with one child or no child
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            // Node with two children: Get the inorder successor (smallest in right subtree)
            node.key = findMin(node.right).key;

            // Delete the inorder successor
            node.right = deleteNode(node.right, node.key);
        }

        // If the tree had only one node, return null
        if (node == null) {
            return null;
        }

        // Update height of current node
        updateHeight(node);

        // Get the balance factor to check if this node became unbalanced
        int balance = getBalance(node);

        // Left heavy case
        if (balance > 1) {
            if (getBalance(node.left) >= 0) {
                // Left-Left Case
                return rightRotate(node);
            } else {
                // Left-Right Case
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }

        // Right heavy case
        if (balance < -1) {
            if (getBalance(node.right) <= 0) {
                // Right-Right Case
                return leftRotate(node);
            } else {
                // Right-Left Case
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        return node;
    }

    // Find the node with minimum value in the tree
    private Node findMin(Node node) {
        if (node == null) {
            return null;
        }
        
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Return the size of the tree
    @Override
    public int size() {
        return size;
    }

    // Return the height of the tree
    @Override
    public int height() {
        return height(root);
    }

    @Override
    public void printdictinory() { 
        System.out.println("Dictionary contents (AVL Tree - in-order traversal):");
        if (root == null) {
            System.out.println("Dictionary is empty");
            return;
        }
        
        System.out.println("Total words: " + size);
        System.out.println("--------------------------------------");
        inorderPrint(root);
        System.out.println("--------------------------------------");
    }

    public void inorder() {
        inorderTraversal(root);
        System.out.println();
    }
    
    private void inorderTraversal(Node node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.print(node.key + " ");
            inorderTraversal(node.right);
        }
    }
    private void inorderPrint(Node node) {
        if (node != null) {
            inorderPrint(node.left);
            System.out.println(node.key);
            inorderPrint(node.right);
        }
    }
    
/* public void inorderTraversal(java.util.function.Consumer<T> consumer) {
    inorderTraversal(root, consumer);
}

private void inorderTraversal(Node node, java.util.function.Consumer<T> consumer) {
    if (node == null) return;
    inorderTraversal(node.left, consumer);
    consumer.accept(node.key);
    inorderTraversal(node.right, consumer);
} */


private void printTreeHelper(Node node, String indent, boolean last) {
        if (node != null) {
            System.out.println(indent + (last ? "└── " : "├── ") + node.key + " (h=" + node.height + ")");
            if (node.left != null || node.right != null) {
                if (node.right != null) {
                    printTreeHelper(node.right, indent + (last ? "    " : "│   "), node.left == null);
                }
                if (node.left != null) {
                    printTreeHelper(node.left, indent + (last ? "    " : "│   "), true);
                }
            }
        }
    }
    @Override
       public void printTreeStructure() {
        System.out.println("Tree structure:");
        printTreeHelper(root, "", true);
    }

    public static void main(String[] args) {
    AVL<Integer> avl = new AVL<>();
    
    avl.insert(41);
    avl.insert(38);
    avl.insert(31);
    avl.insert(12);
    avl.insert(19);
    avl.insert(8);
    
    System.out.println("Tree height: " + avl.height()); // Should print 1
    avl.printTreeStructure(); // Visual representation
    avl.inorder(); // Should print: 31 38 41
}
  


} 