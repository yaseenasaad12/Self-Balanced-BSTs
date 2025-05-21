import BSTs.AVL;
import BSTs.RedBlackTree;
import BSTs.Trees;
import java.io.IOException;

public class DictionaryImplementation {
    private final Trees<String> dictionary;

    public DictionaryImplementation(String typeD) {
        switch (typeD) {
            case "AVL" : {dictionary = new AVL<>();
                break;
            }
            case "Red Black Tree" : {dictionary = new RedBlackTree<>();
                break;
            }
            default : throw new IllegalArgumentException("Invalid dictionary type.");
        }  
    }
    public synchronized void insert(String toInsert) {
        long start = System.nanoTime();
         
        boolean res = dictionary.insert(toInsert);
        if(res) {
            System.out.println("(" + toInsert + ")" + "\u001B[32m Successfully INSERTED ✅\u001B[0m");
        } else {
            System.out.println("\u001B[33m Already Exist\u001B[0m ");
        }
        //dictionary.printdictinory();
        long end = System.nanoTime();
        System.out.println("Insert Time: " + (end - start) / 1_000_000.0 + " ms");
    }

    public synchronized void delete(String toDelete) {
        long start = System.nanoTime();
        if (dictionary.delete(toDelete)) {
            System.out.println("(" + toDelete + ")" + "\u001B[32m Successfully DELETED ✅\u001B[0m");
        } else {
            System.out.println("(" + toDelete + ")" + "\u001B[31m Not found ❌\u001B[0m");
        }
        long end = System.nanoTime();
        System.out.println("Delete Time: " + (end - start) / 1_000_000.0 + " ms");
    }
    
    public boolean search(String toSearch) {
        long start = System.nanoTime();
        boolean result;
        if (dictionary.search(toSearch)) {
            System.out.println("\u001B[32m Found in dictionary ✅\u001B[0m");
            result = true;
        } else {
            System.out.println("\u001B[31m Not found in dictionary ❌\u001B[0m");
            result = false;
        }
        long end = System.nanoTime();
        System.out.println("Search Time: " + (end - start) / 1_000_000.0 + " ms");
        return result;
    }
    public int size() {
        return dictionary.size();
    }
    public int height() {
        return dictionary.height();
    }
    public void batchInsert(String fileToInsert) {
        long start = System.nanoTime();
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(fileToInsert);
            java.util.List<String> lines = java.nio.file.Files.readAllLines(path);
            
            int insertedCount = 0;
            int failedCount = 0;
            
            for (String line : lines) {
                boolean result = dictionary.insert(line.trim());
                if (result) {
                    insertedCount++;
                } else {
                    failedCount++;
                    System.out.println(line.trim());
                }
            }
            System.out.println("Inserted successfully: " + insertedCount + " strings.");
            if (failedCount != 0) {
                System.out.println("Failed to insert: " + failedCount + " strings.");
            }
            System.out.println("Current size: " + dictionary.size());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        long end = System.nanoTime();
        System.out.println("Batch Insert Time: " + (end - start) / 1_000_000.0 + " ms");
    }
    
    public void batchDelete(String fileToDelete) {
        long start = System.nanoTime();
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(fileToDelete);
            java.util.List<String> lines = java.nio.file.Files.readAllLines(path);
            
            int deletedCount = 0;
            int failedCount = 0;
            
            for (String line : lines) {
                boolean result = dictionary.delete(line.trim());
                if (result) {
                    deletedCount++;
                } else {
                    failedCount++;
                }
            }
            
            System.out.println("Deleted successfully: " + deletedCount + " strings.");
            if (failedCount != 0) {
                System.out.println("Failed to delete: " + failedCount + " strings.");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        long end = System.nanoTime();
        System.out.println("Batch Delete Time: " + (end - start) / 1_000_000.0 + " ms");
    }
    

    
}
