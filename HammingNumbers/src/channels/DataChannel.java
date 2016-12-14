package channels;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of a data channel in the data flow network
 * 
 * @author Peter Swantek
 *
 */
public class DataChannel implements Channel {

    private final BlockingQueue<Integer> queue; // thread safe unbounded queue
                                                // to hold numbers

    public DataChannel() {
        queue = new LinkedBlockingQueue<>();
    }

    @Override
    public int read() throws InterruptedException {
        int result = queue.take(); // block until a number is available to read
                                   // from the queue
        return result;
    }

    @Override
    public void write(int number) {
        queue.add(number); // add a number to the queue, adding to the queue is
                           // asynchronous
    }

}
