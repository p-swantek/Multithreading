package ajeffrey.teaching.util.list;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementation of a MutableList that uses an optimistic strategy to maintain
 * thread safety
 * 
 * @author Peter Swantek
 * @see MutableListFactory
 * @see MutableList
 *
 */
public interface OptimisticMutableList extends MutableList {

    public static final MutableListFactory factory = new OptimisticMutableListFactoryImpl(); //factory to build OptimisticMutableLists
}

/*
 * implementation of the MutableListFactory that builds OptimisticMutableLists
 */
class OptimisticMutableListFactoryImpl implements MutableListFactory {

    @Override
    public MutableList build() {
        return new OptimisticMutableListImpl(); //Have the OptimisticMutableList factory build an instance of the OptimisticMutableListImpl
    }
}

/*
 * Implementation of an OptimisticMutableList, uses an atomic reference to an
 * ImmutableList along with compareAndSet to ensure thread safety on list
 * operations without using any locking
 */
class OptimisticMutableListImpl implements OptimisticMutableList {

    private final AtomicReference<ImmutableList> immutableRef; //reference to the ImmutableList

    public OptimisticMutableListImpl() {
        immutableRef = new AtomicReference<>(ImmutableList.empty); //construct an atomic reference to an empty ImmutableList
    }

    @Override
    public void add(Object element) {
        ImmutableList oldList;
        ImmutableList newList;
        do {
            oldList = immutableRef.get(); //get the old value
            newList = oldList.cons(element); //create a new list that contains the item that was added
        } while (!immutableRef.compareAndSet(oldList, newList)); //keep looping until compareAndSet succeeds
    }

    @Override
    public void remove(Object element) {
        ImmutableList oldList;
        ImmutableList newList;
        do {
            oldList = immutableRef.get(); //get the old value
            newList = oldList.remove(element); //create a new list that has the given element removed	
        } while (!immutableRef.compareAndSet(oldList, newList)); //keep looping until compareAndSet succeeds
    }

    @Override
    public Iterator iterator() {
        return immutableRef.get().iterator();
    }

    @Override
    public int size() {
        return immutableRef.get().size();
    }

}
