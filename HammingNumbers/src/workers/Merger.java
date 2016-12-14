package workers;

import src.channels.Channel;

/**
 * Worker that takes the outputs from the 3 multiplier workers and merges them.
 * The minimum number from the 3 outputs will be written to the worker in charge
 * of copying then.
 * 
 * @author Peter Swantek
 *
 */
public class Merger implements Runnable {

    private static final int NUM_INPUTS = 3; // amount of input channals

    private final List<Channel> inputChannels = new ArrayList<>(NUM_INPUTS);
    private final Channel output; // the output channel to write the results

    public Merger(Channel in2, Channel in3, Channel in5, Channel out) {

        inputChannels.add(in2);
        inputChannels.add(in3);
        inputChannels.add(in5);
        output = out;
    }

    /**
     * gets numbers from the input channels from mult_2, mult_3, and mult_5
     * workers then takes these numbers and writes the minimum number to the
     * output channel.
     */
    @Override
    public void run() {

        int[] buffer = { -1, -1, -1 }; // buffer to hold the numbers to merge

        while (!Thread.currentThread().isInterrupted()) {
            try {
                int min = Integer.MAX_VALUE;

                // read data from each channel and store it in a slot in the
                // buffer
                // also record the minimum number that is contained within the
                // buffer
                for (int i = 0; i < buffer.length; i++) {
                    if (buffer[i] == -1) { // if buffer slot has a -1, it
                                           // designates that it is empty and
                                           // needs to be filled by reading from
                                           // the appropriate channel
                        buffer[i] = inputChannels.get(i).read();
                    }
                    if (buffer[i] < min) {
                        min = buffer[i]; // update the current minimum item if necessary
                    }
                }

                output.write(min); // write the minimum number to the output channel

                // delete any other numbers currently in the buffer that are
                // equal to the number just written to the output
                for (int i = 0; i < buffer.length; i++) {
                    if (buffer[i] == min) {
                        buffer[i] = -1; // sets empty slots back to -1
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
