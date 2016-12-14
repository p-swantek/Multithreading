package main;

import src.channels.Channel;
import src.channels.ChannelFactory;
import src.workers.WorkerFactory;

/**
 * Driver for Hamming number generation. Sets up various worker threads that are
 * part of the data flow network and establishes channels between the various
 * workers. Program will then read the numbers computed by the workers and print
 * them to the screen. After the desired amount of numbers are printed, the
 * workers will all be shut down so that the program can terminate normally
 * 
 * @author Peter Swantek
 *
 */
public class HammingPrinter {

    public static final int AMOUNT_TO_GENERATE = 60; // the amount of numbers to print
    public static final int SEED = 1; // starting seed number to kick everything off
    public static final int NUM_WORKERS = 5; // amount of worker threads to use

    /*
     * read data from a channel or wait if none is available. once data is
     * obtained, print it to the standard output. stop printing numbers from the
     * channel once the amount of numbers signified by AMOUNT_TO_GENERATE has
     * been printed
     */
    private static void printFromChannel(Channel c) throws InterruptedException {
        int count = 0;
        while (count < AMOUNT_TO_GENERATE) {
            int number = c.read();
            System.out.println(number);
            count++;
        }
    }

    public static void main(String[] args) {

        // create a fixed size thread pool to handle the worker threads
        ExecutorService executor = Executors.newFixedThreadPool(NUM_WORKERS);

        // build all the channels
        Channel input2 = ChannelFactory.buildChannel(); // input channel to mult_2
        Channel input3 = ChannelFactory.buildChannel(); // input channel to mult_3
        Channel input5 = ChannelFactory.buildChannel(); // input channel to mult_5
        Channel output2 = ChannelFactory.buildChannel(); // output channel from mult_2
        Channel output3 = ChannelFactory.buildChannel(); // output channel from mult_3
        Channel output5 = ChannelFactory.buildChannel(); // output channel from mult_5
        Channel mergeOutput = ChannelFactory.buildChannel(); // output channel from the merger
        Channel printInput = ChannelFactory.buildChannel(); // input channel to the printing thread

        // create the various workers
        Runnable[] workerThreads = { WorkerFactory.buildMultiplier(2, input2, output2), WorkerFactory.buildMultiplier(3, input3, output3), WorkerFactory.buildMultiplier(5, input5, output5),
                WorkerFactory.buildMerger(output2, output3, output5, mergeOutput), WorkerFactory.buildCopier(mergeOutput, input2, input3, input5, printInput, SEED) };

        // start up all the worker threads
        for (Runnable worker : workerThreads) {
            executor.submit(worker);
        }

        // main thread reads from an input channel to print the numbers
        try {
            printFromChannel(printInput);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // once the desired number of numbers are printed, shut down all worker
        // threads
        executor.shutdownNow();
    }

}
