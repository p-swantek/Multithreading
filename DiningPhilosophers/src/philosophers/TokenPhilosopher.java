package philosophers;

import java.util.concurrent.Semaphore;
import ajeffrey.teaching.debug.Debug;

/**
 * Implementation of a Philosopher that will need to obtain a token in order to
 * access the table to get the forks and start eating. Deadlock is avoided
 * because not all Philosophers will be able to access the table at once due to
 * the number of available tokens
 * 
 * @author Peter Swantek
 *
 */
public interface TokenPhilosopher extends Philosopher {

    /**
     * A factory for building token philosophers.
     */
    public static final TokenPhilosopherFactory factory = new TokenPhilosopherFactoryImpl();
}

class TokenPhilosopherFactoryImpl implements TokenPhilosopherFactory {

    @Override
    public Philosopher build(Comparable lhFork, Comparable rhFork, String name, Semaphore token) {
        return new TokenPhilosopherImpl(lhFork, rhFork, name, token);
    }

}

class TokenPhilosopherImpl implements TokenPhilosopher, Runnable {

    final private Comparable lhFork;
    final private Comparable rhFork;
    final private String name;
    final private Thread thread;
    final private Semaphore token; // reference to a shared Semaphore,
                                   // represents the token allowing access to
                                   // the table

    /**
     * Constructs a TokenPhilosopher
     * 
     * @param lhFork
     *            the left hand fork of this TokenPhilosopher
     * @param rhFork
     *            the right hand fork of this TokenPhilosopher
     * @param name
     *            the name of this TokenPhilosopher
     * @param token
     *            then for access to the table
     */
    public TokenPhilosopherImpl(final Comparable lhFork, final Comparable rhFork, final String name,
            final Semaphore token) {
        this.lhFork = lhFork;
        this.rhFork = rhFork;
        this.name = name;
        this.thread = new Thread(this);
        this.token = token;
    }

    /**
     * Have this philosopher try to access the table and start eating/thinking
     */
    @Override
    public void start() {
        thread.start();
    }

    /**
     * This philosopher will try to aquire a token to access the table, waiting
     * until it does. When it gets access to the table, it will pick up the
     * forks, eat/think, then stop. If interrupted or done eating, this
     * philosopher will relinquish the token and leave the table
     */
    @Override
    public void run() {
        Debug.out.breakPoint(name + " is starting");
        try {
            while (true) {
                // Added some new debug outputs around when the philosopher
                // calls aquire() and release() methods
                Debug.out.println(name + " is trying to aquire a permit");
                token.acquire(); // try to aquire a spot at the table, will wait
                                 // if no token is currently available
                Debug.out.println(name + " has aquired a permit");
                Debug.out.println(name + " is thinking");
                delay();
                Debug.out.println(name + " tries to pick up " + lhFork);
                Debug.out.println(name + " picked up " + lhFork);
                delay();
                Debug.out.println(name + " tries to pick up " + rhFork);
                Debug.out.println(name + " picked up " + rhFork);
                Debug.out.println(name + " starts eating");
                delay();
                Debug.out.println(name + " finishes eating");
                Debug.out.println(name + " trying to release its permit");
                token.release(); // after done, give up the token and leave the
                                 // table
                Debug.out.println(name + " has released its permit");
            }
        } catch (final InterruptedException ex) {
            Debug.out.println(name + " is interrupted");
        } finally {
            Debug.out.println(name + " trying to release its permit");
            token.release(); // give up the token if happened to be interrupted
                             // so that you don't hold onto resources
            Debug.out.println(name + " has released its permit");
        }
    }

    @SuppressWarnings("static-access")
    protected void delay() throws InterruptedException {
        Thread.currentThread().sleep((long) (1000 * Math.random()));
    }

}
