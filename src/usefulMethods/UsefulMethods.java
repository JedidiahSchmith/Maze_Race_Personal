package usefulMethods;

import edu.princeton.cs.algs4.Queue;

public class UsefulMethods {

	public static Iterable<Integer> adjVertices(int vertex, int width) {
		Queue<Integer> adjVertices = new Queue<Integer>();

		int[] address = vertexToArray(vertex, width);
		int column = address[0];
		int row = address[1];

		if (column > 0)
			adjVertices.enqueue(vertex - 1);
		if (row > 0)
			adjVertices.enqueue(vertex - width);

		if (column < width - 1)
			adjVertices.enqueue(vertex + 1);

		if (row < width - 1)
			adjVertices.enqueue(vertex + width);

		return adjVertices;
	}

	public static int[] vertexToArray(int vertex, int width) {
		int[] arrayToReturn = new int[2];

		// column
		arrayToReturn[0] = vertex % width;

		// row
		arrayToReturn[1] = vertex / width;

		return arrayToReturn;
	}

	public static int colAndRowToVertex(int col, int row, int width) {
		if (row >= width || col >= width)
			throw new IllegalArgumentException();

		return col + (row * width);
	}

}
