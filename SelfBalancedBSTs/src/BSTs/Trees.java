package BSTs;

public interface Trees<T extends Comparable<T>>  {
    boolean insert(T key);
    boolean delete(T key);
    boolean search(T key);
    int size();
    int height();
    void printdictinory();
    void printTreeStructure() ;

}
