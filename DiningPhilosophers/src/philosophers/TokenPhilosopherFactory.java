package philosophers;

import java.util.concurrent.Semaphore;

/**
 * A factory class for building token philosophers.
 * 
 * @author Peter Swantek
 */

public interface TokenPhilosopherFactory {

    /**
     * Build a philosopher that uses a token to gain access to the table to start eating.
     * 
     * @param lhFork the left-hand fork
     * @param rhFork the right-hand fork
     * @param name the name of the philosopher
     * @param token the token that is used to gain access to the table
     * @return a new philosopher
     */
    public Philosopher build(Comparable lhFork, Comparable rhFork, String name, Semaphore token);

}
