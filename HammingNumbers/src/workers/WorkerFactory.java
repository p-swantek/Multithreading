package workers;

import channels.Channel;

/**
 * Contains factory methods to create different Hamming Worker threads
 * 
 * @author Peter Swantek
 *
 */
public class WorkerFactory {

    private WorkerFactory() {
    }

    /**
     * Builds a multiplier worker
     * 
     * @param multiple the multiplier for this worker to use
     * @param input the input channel of this worker
     * @param output the output channel of this worker
     * @return a new multiplier worker thread
     */
    public static Runnable buildMultiplier(int multiple, Channel input, Channel output) {
        return new Multiplier(multiple, input, output);
    }

    /**
     * Builds a merger worker
     * 
     * @param input2 input channel from a multiplication worker
     * @param input3 input channel from a multiplication worker
     * @param input5 input channel from a multiplication worker
     * @param output output channel to write the merged result
     * @return a new merger worker thread
     */
    public static Runnable buildMerger(Channel input2, Channel input3, Channel input5, Channel output) {
        return new Merger(input2, input3, input5, output);
    }

    /**
     * Builds a copier worker
     * 
     * @param in input channel from the merger
     * @param out2 output channel to a multiplication worker
     * @param out3 output channel to a multiplication worker
     * @param out5 output channel to a multiplication worker
     * @param outP output channel to a printer thread
     * @param seed the initial value to use to initialize this copier
     * @return a new copier worker thread
     */
    public static Runnable buildCopier(Channel in, Channel out2, Channel out3, Channel out5, Channel outP, int seed) {
        return new OutCopier(in, out2, out3, out5, outP, seed);
    }
}
