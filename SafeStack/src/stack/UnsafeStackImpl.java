package stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the stack interface, this is not thread safe!
 * 
 * @author Alan Jeffrey, Peter Swantek
 * @param <E>
 */
class UnsafeStackImpl<E> implements Stack<E> {

    private static final int INIT_ARRAY_CAPACITY = 1;
    private static final int INIT_SIZE = 0;

    private E[] contents;
    private int size;

    @SuppressWarnings("unchecked")
    public UnsafeStackImpl() {
        contents = (E[]) new Object[INIT_ARRAY_CAPACITY];
        size = INIT_SIZE;
    }

    @Override
    public void push(E element) {
        while (size == contents.length) {
            grow();
        }
        contents[size++] = element;
    }

    @Override
    public E pop() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        final E result = contents[--size];
        contents[size] = null;
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new UnsafeStackIterator<>(contents, size);
    }

    private void grow() {
        @SuppressWarnings("unchecked")
        E[] newContents = (E[]) new Object[contents.length * 2];
        System.arraycopy(contents, 0, newContents, 0, size);
        contents = newContents;
    }

}

class UnsafeStackIterator<E> implements Iterator<E> {

    private final E[] contents;
    private final int size;
    private int current = 0;

    UnsafeStackIterator(final E[] contents, final int size) {
        this.contents = contents;
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        return current < size;
    }

    @Override
    public E next() {
        return contents[current++];
    }

}
