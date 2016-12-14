package workers;

import java.util.ArrayList;
import java.util.List;

import channels.Channel;

/**
 * Worker that reads the output from the merging worker and then copies this
 * result to 4 output channels. The number received from the merger is sent to
 * the 3 multiplication workers as well as sent to the thread responsible for
 * printing. This worker will have its input channel seeded with an initial
 * value upon creation
 * 
 * @author Peter Swantek
 *
 */
public class OutCopier implements Runnable {

    private static final int NUM_OUTPUT_CHANNELS = 4; // number of channels to
                                                      // output copied data to

    private final Channel input; // input channel from the 3 merger
    private final Channel output2; // output channel to write data to mult_2
    private final Channel output3; // output channel to write data to mult_3
    private final Channel output5; // output channel to write data to mult_5
    private final Channel outputPrint; // output channel to write data to the
                                       // printer
    private final List<Channel> outputChannels = new ArrayList<>(NUM_OUTPUT_CHANNELS); // list
                                                                                       // to
                                                                                       // hold
                                                                                       // output
                                                                                       // channels

    public OutCopier(Channel in, Channel out2, Channel out3, Channel out5, Channel outP, int seed) {
        input = in;
        output2 = out2;
        output3 = out3;
        output5 = out5;
        outputPrint = outP;
        outputChannels.add(output2);
        outputChannels.add(output3);
        outputChannels.add(output5);
        outputChannels.add(outputPrint);
        input.write(seed); // take the initial seed value and write it to the
                           // input channel of this copier to start the process

    }

    /**
     * tries to read data from the input channel, waiting if necessary until
     * data becomes available. data will be the result from the merger. this
     * data will then be written to all of the output channels
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // get the data and write to all of the outputs
                int item = input.read();
                for (Channel c : outputChannels) {
                    c.write(item);
                }
            } catch (InterruptedException consumed) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
