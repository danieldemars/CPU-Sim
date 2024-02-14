
import java.util.Arrays;
import java.util.Comparator;

/*
Daniel DeMars
CSCI-310 Data Structures
Homework 16 – Generic Heap
*/

public class Heap<E> implements HeapInterface<E>{
    private E[] array; 
    protected Comparator<E> comparator; 
    private int size; 
    
    public Heap(Comparator<E> comparator){
        this.comparator = comparator; 
        this.size = 0;
        array = (E[]) new Object[INITIAL_SIZE];
    }
    
    @Override
    public void add(E element) {
        if (size == array.length - 2){
            doubleArraySize(); 
        }
        
        size++;
        int current = size; 
        array[current] = element; 
        
        int parent = current / 2; 
        boolean done = false; 
        
        while (current != 1 && !done){
            if (comparator.compare(array[current], array[parent]) < 0){
                swap(current, parent);
                current = parent;
                parent = current / 2; 
            } else {
                done = true;
            }
        }
    }
    
    @Override
    public E remove() throws EmptyHeapException {
        if (this.isEmpty()){
            throw new EmptyHeapException("ERROR: cannot remove from an empty heap."); 
        }
        
        E returnValue = array[1];
        array[1] = array[size];
        size--;
        int current = 1;
        boolean done = false;
        int leftChild; 
        int rightChild; 
        int priC; 
        
        while (numberOfChildren(current) > 0 && !done){
            if (numberOfChildren(current) == 1){
                leftChild = current * 2;
                
                if (comparator.compare(array[leftChild], array[current]) < 0){
                    swap(leftChild, current); 
                    current = leftChild; 
                } else {
                    done = true;
                }
            } else {
                leftChild = current * 2;
                rightChild = current * 2 + 1; 
                
                if (comparator.compare(array[leftChild], array[rightChild]) < 0){
                    priC = leftChild; // may need to reverse this idk yet 
                } else {
                    priC = rightChild; 
                }
                
                if (comparator.compare(array[priC], array[current]) < 0){
                    swap(priC, current);
                    current = priC; 
                } else {
                    done = true;
                }
            }
        }
        return returnValue; 
    }
    
    @Override
    public int size() {
        return this.size; 
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0; 
    }

    @Override
    public Comparator comparator() {
        return this.comparator; 
    }
    
    private void swap(int x, int y){
        E temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    private void doubleArraySize() {
        array = Arrays.copyOf(array, array.length * 2); 
    }
    
    private int numberOfChildren(int index){
        int children = 0; 
        
        if (index * 2 <= size){
            children++;
        }
        
        if (index * 2 + 1 <= size){
            children++;
        }
        
        return children; 
    }
}
