package stack;

/**
 * Interface of basic stack operations
 * 
 * @author Peter Swantek
 *
 * @param <E> the generic type of elements contained in the stack
 */
public interface Stack<E> extends Iterable<E> {

    /**
     * Push an element onto the stack
     * 
     * @param element the element to push
     */
    void push(E element);

    /**
     * Pop an item off of the stack
     * 
     * @return the item that was popped off
     */
    E pop();

    /**
     * Returns the current size of the stack
     * 
     * @return the number of items currently contained in the stack
     */
    int size();

}
