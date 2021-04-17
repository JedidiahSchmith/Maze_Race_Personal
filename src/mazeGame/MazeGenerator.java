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

	public static Graph noWallsMaze(int width) {
		int wallbetween1 = 25;
		int wallbetween2 = 26;

		Graph graphToReturn = new Graph(width * width);
		for (int vertex = 0; vertex < graphToReturn.V(); vertex++)
			for (int adjVertex : UsefulMethods.adjVertices(vertex, width)) {

				if (!((vertex == wallbetween1 && adjVertex == wallbetween2)
						|| (vertex == wallbetween2 && adjVertex == wallbetween1)))
					graphToReturn.addEdge(vertex, adjVertex);
			}
		return graphToReturn;
	}

}
