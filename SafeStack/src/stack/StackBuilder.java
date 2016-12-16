package stack;

/**
 * Main factory used by clients to create either unsafe or thread safe stacks
 * 
 * @author Peter Swantek
 *
 * @param <E> the generic type of item that will be contained in the stack
 */
public class StackBuilder<E> {

    private final StackFactory<E> unsafeFactory;
    private final StackFactory<E> safeFactory;

    public StackBuilder() {
        unsafeFactory = new UnsafeStackFactory<>();
        safeFactory = new SafeStackFactory<>();
    }

    public Stack<E> getUnsafeStack() {
        return unsafeFactory.build();
    }

    public Stack<E> getSafeStack() {
        return safeFactory.build();
    }

}
