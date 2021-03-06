package drivers.ordered;

import ajeffrey.teaching.debug.Debug;
import philosophers.DeadlockingPhilosopher;
import philosophers.OrderedPhilosopher;
import philosophers.Philosopher;
import philosophers.PhilosopherFactory;

/**
 * Test driver that uses philosophers that use resource ordering to avoid
 * deadlock
 * 
 * @author Alan Jeffrey and Peter Swantek
 * @version 1.0.2
 * @see Philosopher, OrderedPhilosopher, PhilosopherFactory
 */
public class OrderedPhilosopherDriver {

    public static void main(String[] args) throws Exception {

        // Uncomment the line below to switch on step debugging
        // Debug.out.addFactory (StepDebugStream.factory);

        // Send debugging to stderr
        Debug.out.addPrintStream(System.err);

        // Create the forks
        final Comparable fork1 = new Integer(1);// "Fork 1";
        final Comparable fork2 = new Integer(2);// "Fork 2";
        final Comparable fork3 = new Integer(3);// "Fork 3";
        final Comparable fork4 = new Integer(4);// "Fork 4";

        // factory to create philosophers that utilize resource ordering
        final PhilosopherFactory factory = OrderedPhilosopher.factory;

        // Create the philosophers
        final Philosopher fred = factory.build(fork1, fork2, "Fred");
        final Philosopher wilma = factory.build(fork2, fork3, "Wilma");
        final Philosopher barney = factory.build(fork3, fork4, "Barney");
        final Philosopher betty = factory.build(fork4, fork1, "Betty");

        // Start the philosophers
        fred.start();
        wilma.start();
        barney.start();
        betty.start();
    }

}
