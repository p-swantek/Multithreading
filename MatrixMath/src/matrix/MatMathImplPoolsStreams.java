package matrix;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Provides an implementation of the MatMath interface that uses Streams along 
 * with thread pools to perform matrix multiplication and addition
 * 
 * @author Peter Swantek
 *
 */
public class MatMathImplPoolsStreams implements MatMath{
	
	/**
	 * Multiplies 2 matrices and stores the result in a third array
	 * 
	 * @param A the first matrix to multiply
	 * @param B the second matrix to multiply
	 * @param C matrix which will store the result of the multiplication
	 */
	@Override
	public void multiply(int[][] A, int[][] B, int[][] C) {
		
		int NUM_THREADS = Runtime.getRuntime().availableProcessors(); //make as many threads depending on the amount of CPU cores
		ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS); //create fixed thread pool
		
		IntStream.range(0, A.length)
			.parallel().forEach(i -> IntStream.range(0, B[0].length)
					.parallel().forEach(j -> IntStream.range(0, B.length)
							.parallel().forEach(p -> 
								pool.submit(() -> C[i][j] = IntStream.range(0, B.length).parallel().map(k -> A[i][k] * B[k][j]).reduce(0, Integer::sum))))); //submit a runnable for the pool to execute

		
		//shut down, make sure all tasks finish before publishing the result
		pool.shutdown();
		try {
			pool.awaitTermination(2, TimeUnit.MINUTES); //wait 2 minutes to shut down
		} catch (InterruptedException e) {
			pool.shutdownNow(); //force shut down if we get interrupted
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Adds two matrices together and stores the result in a third matrix
	 * 
	 * @param A a matrix to be added
	 * @param B a matrix to be added
	 * @param C matrix that stores the result of adding 2 matrices together
	 */
	@Override
	public void add(int[][] A, int[][] B, int[][] C) {
		
		int NUM_THREADS = Runtime.getRuntime().availableProcessors(); //make as many threads depending on the amount of CPU cores
		ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS); //create fixed thread pool
		
		IntStream.range(0, A.length)
			.parallel().forEach(i -> IntStream.range(0, A[i].length)
					.parallel().forEach(j -> 
								pool.submit(() -> C[i][j] = A[i][j] + B[i][j]))); //submit a runnable to the pool to execute
		
		//shut down, make sure all tasks finish before publishing the result
		pool.shutdown();
		try {
			pool.awaitTermination(2, TimeUnit.MINUTES); //wait 2 minutes to shut down
		} catch (InterruptedException e) {
			pool.shutdownNow(); //force shut down if we get interrupted
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Print out a matrix
	 * 
	 * @param A the matrix to be printed
	 */
	@Override
	public void print(int[][] A) {
		for (int[] subarray : A){
			System.out.println(Arrays.toString(subarray));
		}
	}

}
