package matrix;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * Provides an implementation of the MatMath interface that uses Threads to do
 * the matrix multiplication and addition
 * 
 * @author Peter Swantek
 *
 */
public class MatMathImplThreads implements MatMath {

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
        int numThreads = C.length * C[0].length; // create a thread for each
                                                 // cell in the result matrix
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(numThreads); // ensures that all
                                                             // threads finish

        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[i].length; j++) {
                Thread t = new Thread(new MultiplicationWorker(i, j, B.length, A, B, C, start, end)); // create
                                                                                                      // worker
                                                                                                      // thread
                                                                                                      // to
                                                                                                      // do
                                                                                                      // the
                                                                                                      // multiplication
                t.start();
            }
        }

        start.countDown(); // start all worker threads
        try {
            end.await(); // dont publish the result until all threads have
                         // finished working
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        int numThreads = A.length * A[0].length; // create a thread to take care
                                                 // of each cell in the array
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(numThreads); // ensures that all
                                                             // threads will
                                                             // finish

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                Thread t = new Thread(new AdditionWorker(i, j, A, B, C, start, end)); // create
                                                                                      // a
                                                                                      // worker
                                                                                      // thread
                                                                                      // to
                                                                                      // do
                                                                                      // the
                                                                                      // addition
                t.start();
            }
        }

        start.countDown(); // start all worker threads
        try {
            end.await(); // dont publish the result until all threads have
                         // finished working
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        private CountDownLatch start;
        private CountDownLatch end;

        public MultiplicationWorker(int i, int j, int size, int[][] first, int[][] second, int[][] result,
                CountDownLatch s, CountDownLatch e) {
            rowNum = i;
            colNum = j;
            length = size;
            A = first;
            B = second;
            C = result;
            start = s;
            end = e;
        }

        @Override
        public void run() {
            try {
                start.await(); // wait for the signal to start
                try {
                    for (int k = 0; k < length; k++) {
                        C[rowNum][colNum] += A[rowNum][k] * B[k][colNum]; // do
                                                                          // the
                                                                          // multiplication
                                                                          // and
                                                                          // put
                                                                          // the
                                                                          // result
                                                                          // in
                                                                          // result
                                                                          // matrix
                    }
                } finally {
                    end.countDown(); // decrement the latch to signal that this
                                     // thread is done
                }
            } catch (InterruptedException e) {
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
        private CountDownLatch start;
        private CountDownLatch end;

        public AdditionWorker(int i, int j, int[][] first, int[][] second, int[][] result, CountDownLatch s,
                CountDownLatch e) {
            rowNum = i;
            colNum = j;
            A = first;
            B = second;
            C = result;
            start = s;
            end = e;
        }

        @Override
        public void run() {
            try {
                start.await(); // wait for the signal to start
                try {
                    C[rowNum][colNum] = A[rowNum][colNum] + B[rowNum][colNum]; // do
                                                                               // the
                                                                               // addition
                                                                               // and
                                                                               // put
                                                                               // the
                                                                               // result
                                                                               // in
                                                                               // result
                                                                               // matrix
                } finally {
                    end.countDown(); // decrement the latch to signal that this
                                     // thread is done
                }
            } catch (InterruptedException e) {
            }
        }
    }
}
