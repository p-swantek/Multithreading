package workers;

import channels.Channel;

/**
 * Worker that takes in a number from an input channel and then multiplies that number by either 2, 3, or 5. This resulting product is then written to
 * this workers output channel
 * 
 * @author Peter Swantek
 *
 */
public class Multiplier implements Runnable {

    private final int multiplier; // thread will use either 2, 3, or 5 for
                                  // multiplying
    private final Channel input; // input channel to read numbers from
    private final Channel output; // the output to write the number after
                                  // performing multiplication

    public Multiplier(int mult, Channel in, Channel out) {
        multiplier = mult;
        input = in;
        output = out;
    }

    /**
     * tries to read data from its input channel, waiting if necessary until data becomes available. once a number is obtained, it is multiplied by
     * the specific multiplier. the product of this calculation is then written to the output channel
     */
    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                // get the number, multiply it, write it to the output channel
                int number = input.read();
                output.write(number * multiplier);
            } catch (InterruptedException consumed) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
