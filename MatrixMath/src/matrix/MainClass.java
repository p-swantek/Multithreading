package matrix;

/**
 * Main driver to perform matrix operations using the threaded and streams implementations of MatMath
 * 
 * @author Peter Swantek
 *
 */
public class MainClass {

    /*
     * run the test of ((A+B)*C)*D using a particular implementation of the MatMath interface
     */
    private static void runTest(MatMath impl, int[][] A, int[][] B, int[][] C, int[][] D, int[][] r, int[][] s,
            int[][] t, String whichImpl) {
        // Do the operations using the threaded implementation of MatMath
        System.out.println("Performing matrix operations using: " + whichImpl);
        r = new int[A.length][A[0].length];
        impl.add(A, B, r);
        System.out.println("Result of A + B:");
        impl.print(r);

        System.out.println();

        System.out.println("Result of (A+B) * C:");
        s = new int[r.length][C[0].length];
        impl.multiply(r, C, s);
        impl.print(s);

        System.out.println();

        System.out.println("Result of ((A+B) * C) * D:");
        t = new int[s.length][D[0].length];
        impl.multiply(s, D, t);
        impl.print(t);

        System.out.println();
    }

    public static void main(String[] args) {

        // initialize the different implementations of MatMath
        MatMath threadImpl = new MatMathImplThreads();
        MatMath streamsImpl = new MatMathImplStreams();
        MatMath poolImpl = new MatMathImplPools();
        MatMath streamPoolImpl = new MatMathImplPoolsStreams();

        // code to initialize A,B,C,D
        int[][] A = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

        int[][] B = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

        // 2x3
        int[][] C = { { 1, 2, 3 }, { 4, 5, 6 } };

        int[][] D = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

        int[][][] matrices = { A, B, C, D };
        // matrices to hold results of operations
        int[][] r = null;
        int[][] s = null;
        int[][] t = null;

        // print out A, B, C, D
        System.out.println("Matrices A, B, C, and D:");
        for (int[][] matrix : matrices) {
            threadImpl.print(matrix);
            System.out.println();
        }

        // run a test of each implementation
        runTest(threadImpl, A, B, C, D, r, s, t, "THREADS IMPLEMENTATION");
        runTest(streamsImpl, A, B, C, D, r, s, t, "STREAMS IMPLEMENTATION");
        runTest(poolImpl, A, B, C, D, r, s, t, "THREAD POOLS IMPLEMENTATION");
        runTest(streamPoolImpl, A, B, C, D, r, s, t, "STREAMS WITH THREAD POOLS IMPLEMENTATION");
    }
}
