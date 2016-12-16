package stack;

/**
 * Factory that builds a thread safe stack
 * 
 * @author Peter Swantek
 *
 * @param <E> the generic type of items in the safe stack
 */
class SafeStackFactory<E> implements StackFactory<E> {

    @Override
    public Stack<E> build() {
        return new SafeStackImpl<>();
    }

}
