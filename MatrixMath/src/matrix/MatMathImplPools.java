package matrix;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Provides an implementation of the MatMath interface that uses fixed size
 * thread pools do the matrix multiplication and addition
 * 
 * @author Peter Swantek
 *
 */
public class MatMathImplPools implements MatMath {

    /**
     * Multiplies 2 matrices and stores the result in a third array
     * 
     * @param A
     *            the first matrix to multiply
     * @param B
     *            the second matrix to multiply
     * @param C
     *            matrix which will store the result of the multiplication
     */
    @Override
    public void multiply(int[][] A, int[][] B, int[][] C) {

        int NUM_THREADS = Runtime.getRuntime().availableProcessors(); // make as
                                                                      // many
                                                                      // threads
                                                                      // depending
                                                                      // on the
                                                                      // amount
                                                                      // of CPU
                                                                      // cores
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS); // create
                                                                          // fixed
                                                                          // thread
                                                                          // pool

        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[i].length; j++) {
                pool.submit(new MultiplicationWorker(i, j, B.length, A, B, C)); // submit
                                                                                // the
                                                                                // runnable
                                                                                // task
                                                                                // to
                                                                                // the
                                                                                // thread
                                                                                // pool
            }
        }

        // shut down, make sure all tasks finish before publishing the result
        pool.shutdown();
        try {
            pool.awaitTermination(2, TimeUnit.MINUTES); // wait 2 minutes to
                                                        // shut down
        } catch (InterruptedException e) {
            pool.shutdownNow(); // force shut down if we get interrupted
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Adds two matrices together and stores the result in a third matrix
     * 
     * @param A
     *            a matrix to be added
     * @param B
     *            a matrix to be added
     * @param C
     *            matrix that stores the result of adding 2 matrices together
     */
    @Override
    public void add(int[][] A, int[][] B, int[][] C) {

        int NUM_THREADS = Runtime.getRuntime().availableProcessors(); // make as
                                                                      // many
                                                                      // threads
                                                                      // depending
                                                                      // on the
                                                                      // amount
                                                                      // of CPU
                                                                      // cores
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS); // create
                                                                          // fixed
                                                                          // thread
                                                                          // pool

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                pool.submit(new AdditionWorker(i, j, A, B, C)); // submit the
                                                                // runnable task
                                                                // to the thread
                                                                // pool
            }
        }

        // shut down, make sure all tasks finish before publishing the result
        pool.shutdown();
        try {
            pool.awaitTermination(2, TimeUnit.MINUTES); // wait 2 minutes to
                                                        // shut down
        } catch (InterruptedException e) {
            pool.shutdownNow(); // force shut down if we get interrupted
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Print out a matrix
     * 
     * @param A
     *            the matrix to be printed
     */
    @Override
    public void print(int[][] A) {
        for (int[] subarray : A) {
            System.out.println(Arrays.toString(subarray));
        }
    }

    /*
     * worker for doing matrix multiplication
     */
    private class MultiplicationWorker implements Runnable {

        private int rowNum;
        private int colNum;
        private int length;
        private int[][] A;
        private int[][] B;
        private int[][] C;

        public MultiplicationWorker(int i, int j, int size, int[][] first, int[][] second, int[][] result) {
            rowNum = i;
            colNum = j;
            length = size;
            A = first;
            B = second;
            C = result;
        }

        @Override
        public void run() {
            for (int k = 0; k < length; k++) {
                C[rowNum][colNum] += A[rowNum][k] * B[k][colNum]; // do the
                                                                  // multiplication
                                                                  // and put the
                                                                  // result in
                                                                  // result
                                                                  // matrix
            }
        }
    }

    /*
     * worker for doing matrix addition
     */
    private class AdditionWorker implements Runnable {

        private int rowNum;
        private int colNum;
        private int[][] A;
        private int[][] B;
        private int[][] C;

        public AdditionWorker(int i, int j, int[][] first, int[][] second, int[][] result) {
            rowNum = i;
            colNum = j;
            A = first;
            B = second;
            C = result;
        }

        @Override
        public void run() {
            C[rowNum][colNum] = A[rowNum][colNum] + B[rowNum][colNum]; // do the
                                                                       // addition
                                                                       // and
                                                                       // put
                                                                       // the
                                                                       // result
                                                                       // in
                                                                       // result
                                                                       // matrix
        }
    }
}
