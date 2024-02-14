/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */

/**
 *
 * @author dande
 */
public class EmptyHeapException extends RuntimeException{

    /**
     * Creates a new instance of <code>EmptyHeapException</code> without detail
     * message.
     */
    public EmptyHeapException() {
        super(); 
    }

    /**
     * Constructs an instance of <code>EmptyHeapException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EmptyHeapException(String msg) {
        super(msg);
    }
}

