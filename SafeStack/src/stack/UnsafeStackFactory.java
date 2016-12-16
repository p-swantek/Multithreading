package stack;

/**
 * Factory that builds a stack that is not thread safe
 * 
 * @author Peter Swantek
 *
 * @param <E> the generic type of items in the unsafe stack
 */
class UnsafeStackFactory<E> implements StackFactory<E> {

    @Override
    public Stack<E> build() {
        return new UnsafeStackImpl<>();
    }

}
