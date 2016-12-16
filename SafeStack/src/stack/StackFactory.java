package stack;

/**
 * Provides an interface to build different stack implementations
 * 
 * @author Peter Swantek
 *
 * @param <E> the generic type of item that the built stack will contain
 */
public interface StackFactory<E> {

    /**
     * Creates a new stack
     * 
     * @return the stack implementation that was built
     */
    Stack<E> build();
}
