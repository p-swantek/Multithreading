package drivers.token;

import java.util.concurrent.Semaphore;

import ajeffrey.teaching.debug.Debug;
import philosophers.Philosopher;
import philosophers.TokenPhilosopher;
import philosophers.TokenPhilosopherFactory;

/**
 * Test driver that uses philosophers that use resource ordering to avoid deadlock
 * 
 * @author Peter Swantek
 * @see Philosopher, TokenPhilosopher, TokenPhilosopherFactory
 */
public class TokenPhilosopherDriver {

    public static void main(String[] args) {

        // Uncomment the line below to switch on step debugging
        // Debug.out.addFactory (StepDebugStream.factory);

        // Send debugging to stderr
        Debug.out.addPrintStream(System.err);

        // Create the forks
        final Comparable fork1 = new Integer(1);// "Fork 1";
        final Comparable fork2 = new Integer(2);// "Fork 2";
        final Comparable fork3 = new Integer(3);// "Fork 3";
        final Comparable fork4 = new Integer(4);// "Fork 4";

        // the amount of philosophers
        // if there are N philosophers, only give out N-1 permits so not all sit
        // at the table
        int numPhilosophers = 4;
        Semaphore s = new Semaphore(numPhilosophers - 1);

        // factory to create philosophers that utilize tokens to gain access to
        // the table
        final TokenPhilosopherFactory factory = TokenPhilosopher.factory;

        // Create the philosophers
        final Philosopher fred = factory.build(fork1, fork2, "Fred", s);
        final Philosopher wilma = factory.build(fork2, fork3, "Wilma", s);
        final Philosopher barney = factory.build(fork3, fork4, "Barney", s);
        final Philosopher betty = factory.build(fork4, fork1, "Betty", s);

        // Start the philosophers
        fred.start();
        wilma.start();
        barney.start();
        betty.start();

    }

}
