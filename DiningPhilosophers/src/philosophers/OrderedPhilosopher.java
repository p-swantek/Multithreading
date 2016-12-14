package philosophers;

import ajeffrey.teaching.debug.Debug;

/**
 * A philosopher from the dining philosophers problem. A philosopher thinks,
 * then chooses which fork to pick up based on which fork has a smaller number
 * so that the forks are picked up in order. A philosopher will always try to
 * pick up the fork with the lower number first. This version of the business
 * logic has been edited by Peter Swantek. All of my edits are highlighted with
 * EDITED by Peter Swantek.
 * 
 * @author Alan Jeffrey and Peter Swantek
 * @version 1.0.2
 */

// EDITED by Peter Swantek:
// changed the interface name to OrderPhilosopher
public interface OrderedPhilosopher extends Philosopher {

    /**
     * A factory for building ordered philosophers.
     */
    // EDITED by Peter Swantek:
    // changed the factory to be the one that will produce the
    // OrderedPhilosopher
    public static final PhilosopherFactory factory = new OrderedPhilosopherFactoryImpl();

}

// EDITED by Peter Swantek:
// changed the class name to OrderedPhilosopherFactoryImpl
class OrderedPhilosopherFactoryImpl implements PhilosopherFactory {

    // EDITED by Peter Swantek:
    // changed the build method so that it builds an instance of an
    // OrderedPhilosopher
    @Override
    public Philosopher build(final Comparable lhFork, final Comparable rhFork, final String name) {
        return new OrderedPhilosopherImpl(lhFork, rhFork, name);
    }

}

// EDITED by Peter Swantek:
// changed the class name to OrderedPhilosopher
class OrderedPhilosopherImpl implements Runnable, OrderedPhilosopher {

    // EDITED by Peter Swantek:
    // changed the locks to be Comparables rather than Objects in order to use
    // the compareTo() method to assign order to the forks
    final private Comparable lhFork;
    final private Comparable rhFork;

    final private String name;
    final private Thread thread;

    // EDITED by Peter Swantek:
    // changed the constructor to take Comparables to represent the forks as
    // opposed to Objects
    public OrderedPhilosopherImpl(final Comparable lhFork, final Comparable rhFork, final String name) {

        // EDITED by Peter Swantek:
        // added code to compare the 2 forks.
        // if the lhFork that is passed into the constructor is greater than the
        // rhFork based on the compareTo() result,
        // swap the values so the lhFork instance variable always holds a
        // reference to the lower number and the rhFork always holds the larger
        // number
        if (lhFork.compareTo(rhFork) > 0) {
            this.lhFork = rhFork;
            this.rhFork = lhFork;
        }
        // otherwise, lhFork was less than the rhFork
        else {
            this.lhFork = lhFork;
            this.rhFork = rhFork;
        }

        this.name = name;
        this.thread = new Thread(this);
    }

    @Override
    public void start() {
        thread.start();
    }

    @Override
    public void run() {
        Debug.out.breakPoint(name + " is starting");
        try {
            while (true) {
                Debug.out.println(name + " is thinking");
                delay();
                Debug.out.println(name + " tries to pick up " + lhFork);
                synchronized (lhFork) {
                    Debug.out.println(name + " picked up " + lhFork);
                    delay();
                    Debug.out.println(name + " tries to pick up " + rhFork);
                    synchronized (rhFork) {
                        Debug.out.println(name + " picked up " + rhFork);
                        Debug.out.println(name + " starts eating");
                        delay();
                        Debug.out.println(name + " finishes eating");
                    }
                }
            }
        } catch (final InterruptedException ex) {
            Debug.out.println(name + " is interrupted");
        }
    }

    @SuppressWarnings("static-access")
    private void delay() throws InterruptedException {
        Thread.currentThread().sleep((long) (1000 * Math.random()));
    }

}
