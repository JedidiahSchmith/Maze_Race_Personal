package mazeGame;

import edu.princeton.cs.algs4.Graph;
import usefulMethods.UsefulMethods;

public class MazeGenerator {

	/*
	 * Goal is randomly placed somewhere in board
	 * 
	 * both players start at the same spot
	 * 
	 * Allows a dynamic size
	 * 
	 */

	public static Graph newMaze(int width) {
		Graph graphToReturn = new Graph(width * width);
		for (int vertex = 0; vertex < graphToReturn.V(); vertex++)
			for (int adjVertices : UsefulMethods.adjVertices(vertex, width)) {
				graphToReturn.addEdge(vertex, adjVertices);
			}
		// System.out.println(graphToReturn);
		return graphToReturn;
	}

}
