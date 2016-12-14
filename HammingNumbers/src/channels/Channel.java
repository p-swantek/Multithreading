package channels;

/**
 * Represents behaviors of channels within the data flow network for Hamming
 * number generation
 * 
 * @author Peter Swantek
 *
 */
public interface Channel {

    /**
     * Get an integer from the channel
     * 
     * @return an integer from the channel
     * @throws InterruptedException
     */
    int read() throws InterruptedException;

    /**
     * Write a number to the channel
     * 
     * @param number the number to write
     */
    void write(int number);

}
