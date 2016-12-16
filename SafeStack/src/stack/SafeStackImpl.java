package stack;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation for a thread-safe stack. This stack keeps track of
 * its version, updating on any pushes or pops. Any iterator over this stack
 * will throw a ConcurrentModificationException if the stack is modified at all
 * during iteration
 * 
 * @author Peter Swantek
 *
 */
class SafeStackImpl<E> implements Stack<E> {

    private static final int INIT_VERSION = 0;

    private final Stack<E> unsafeStack; // use an UnsafeStack as an aggregate
    private int version; //the current version of this stack, updates whenever stack is modified via push/pop
    private final StackBuilder<E> stackBuilder = new StackBuilder<>();

    /**
     * Constructs a new SafeStackImpl
     * 
     */
    public SafeStackImpl() {
        unsafeStack = stackBuilder.getUnsafeStack();
        version = INIT_VERSION;
    }

    /**
     * Push the given element onto the stack, updates the stack's version
     * 
     * @param element the item that is pushed onto the stack
     */
    public synchronized void push(E element) {
        unsafeStack.push(element);
        version++;
    }

    /**
     * Pops and returns the top item on the stack, updates the stack's version
     * 
     * @return the object that was popped off of the top of the stack
     */
    public synchronized E pop() {
        E res = unsafeStack.pop();
        version++;
        return res;
    }

    /**
     * Gets the number of items currently in this stack
     * 
     * @return the number of elements currently contained in the stack
     */
    public synchronized int size() {
        return unsafeStack.size();
    }

    /**
     * Provides an iterator over the stacks elements
     * 
     * @return an Iterator over the elements of this stack
     */
    public synchronized Iterator<E> iterator() {

        return new SafeStackIterator<>(version, unsafeStack, this);
    }

    protected int getCurrVersion() {
        return version;
    }

}

/**
 * Represents an implementation of an iterator which maintains its current
 * version. This is a fail-fast iterator, it will fail if the underlying stack
 * is modified during iteration
 * 
 * @author Peter Swantek
 *
 */
class SafeStackIterator<E> implements Iterator<E> {

    private int itrVersion; //the iterator's current version
    private Iterator<E> itr; //delegate iterator actions off to the unsafe safe stack's iterator
    private SafeStackImpl<E> instance; //the instance of the safe stack over which this iterator travels

    /**
     * Constructs a new SafeStackIterator
     * 
     */
    public SafeStackIterator(int version, Stack<E> contents, SafeStackImpl<E> currInstance) {
        itrVersion = version;
        itr = contents.iterator();
        instance = currInstance;
    }

    /**
     * Return true or false if there are any more elements to be iterated over
     * 
     */
    @Override
    public boolean hasNext() {
        synchronized (instance) {
            return itr.hasNext();
        }

    }

    /**
     * Returns the next item. First checks to see if this iterator's current
     * version matches the current version of the SafeStackImpl, throws a
     * ConcurrentModificationException if the versions do not match
     * 
     */
    @Override
    public E next() {
        synchronized (instance) {
            if (itrVersion != instance.getCurrVersion()) { //fail-fast, throw exception if versions don't match
                throw new ConcurrentModificationException();
            }

            else if (!itr.hasNext()) {
                throw new NoSuchElementException();
            }

            return itr.next();

        }
    }
}
