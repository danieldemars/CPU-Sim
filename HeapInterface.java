import java.util.Comparator;


/**
 * A generic heap.
 *
 * @author Kevin Lillis
 * @param <E> Type of elements stored in this heap
 */
public interface HeapInterface<E> {
    
    /**
     * Initial size of the Heap.
     */
    public static final int INITIAL_SIZE = 5;
    
    /**
     * Adds the specified element to this heap.
     *
     * @param element the element to add to this heap.
     */
    void add(E element);

    /**
     * Removes and returns the element with the highest priority.
     *
     * @return element with the highest priority.
     * @throws EmptyHeapException when trying to remove from an empty heap.
     */
    E remove() throws EmptyHeapException;

    /**
     * Returns the number of elements stored in this heap.
     *
     * @return number of elements in this heap.
     */
    int size();

    /**
     * Returns true if there are no elements in this heap, false otherwise.
     *
     * @return true if there are no elements in this heap, false otherwise.
     */
    boolean isEmpty();
    
    /**
     * Returns the comparator used by this heap.
     * @return the comparator used by this heap.
     */
    Comparator comparator();
}