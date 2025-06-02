package BSTs;

public interface Trees<T extends Comparable<T>>  {
    boolean insert(T key);
    boolean delete(T key);
    boolean search(T key);
    void printdictinory();
    int size();
    int height();
  


}
