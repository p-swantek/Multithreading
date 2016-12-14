package matrix;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Provides an implementation of the MatMath interface that uses Streams to 
 * perform matrix multiplication and addition
 * 
 * @author Peter Swantek
 *
 */
public class MatMathImplStreams implements MatMath{
	
	/**
	 * Multiplies 2 matrices and stores the result in a third array
	 * 
	 * @param A the first matrix to multiply
	 * @param B the second matrix to multiply
	 * @param C matrix which will store the result of the multiplication
	 */
	@Override
	public void multiply(int[][] A, int[][] B, int[][] C) {
		IntStream.range(0, A.length)
			.parallel().forEach(i -> IntStream.range(0, B[0].length)
				.parallel().forEach(j -> IntStream.range(0, B.length)
						.parallel().forEach(p -> C[i][j] = IntStream.range(0, B.length)
							.parallel().map(k -> A[i][k] * B[k][j])
								.reduce(0, Integer::sum))));
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
		IntStream.range(0, A.length)
			.parallel().forEach(i -> IntStream.range(0, A[i].length)
					.parallel().forEach(j -> C[i][j] = A[i][j] + B[i][j]));
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
