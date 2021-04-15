package mazeGame;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;

public class MazeGenerator {

	/*
	 * Goal is randomly placed somewhere in board
	 * 
	 * both players start at the same spot
	 * 
	 * Allows a dynamic size
	 * 
	 */

	public static Graph newMaze(int size) {
		Graph graphToReturn = new Graph(size);
		for (int vertex = 0; vertex < graphToReturn.V(); vertex++)
			for (int adjVertices : adjVertices(vertex, size))
				graphToReturn.addEdge(vertex, adjVertices);
		System.out.println(graphToReturn);
		return graphToReturn;
	}

	private static Iterable<Integer> adjVertices(int vertex, int size) {
		Queue<Integer> adjVertices = new Queue<Integer>();
		int width = (int) Math.sqrt(size);
		int column = vertex % width;
		int row = vertex / width;

		if (column != 0)
			adjVertices.enqueue(vertex - 1);
		if (column != width - 1)
			adjVertices.enqueue(vertex + 1);
		if (row != 0)
			adjVertices.enqueue(vertex - width - 1);
		if (row != width - 1)
			adjVertices.enqueue(vertex + width - 1);

		return adjVertices;
	}
}
