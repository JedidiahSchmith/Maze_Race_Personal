package usefulMethods;

import edu.princeton.cs.algs4.Queue;
import mazeGame.Direction;

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

	public static Direction relativeLocationOfNeighborVertex(int currentVertex, int otherVertex, int width) {

		int[] currentAddress = vertexToArray(currentVertex, width);
		int[] otherAddress = vertexToArray(otherVertex, width);

		int horizontalDistance = currentAddress[0] - otherAddress[0];
		int verticalDistance = currentAddress[1] - otherAddress[1];

		if (horizontalDistance == 0) {
			if (verticalDistance == -1) {
				return Direction.DOWN;
			} else if (verticalDistance == 1) {
				return Direction.UP;
			}
		} else if (verticalDistance == 0) {
			if (horizontalDistance == -1) {
				return Direction.LEFT;
			} else if (horizontalDistance == 1) {
				return Direction.RIGHT;
			}
		}

		return null;
	}

}
