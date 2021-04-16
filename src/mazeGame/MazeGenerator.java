package mazeGame;

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

	public static GraphWrapper noWallsMaze(int width) {
		GraphWrapper graphToReturn = new GraphWrapper(width * width);
		for (int vertex = 0; vertex < graphToReturn.V(); vertex++)
			for (int adjVertices : UsefulMethods.adjVertices(vertex, width)) {
				graphToReturn.addEdge(vertex, adjVertices);
			}
		return graphToReturn;
	}

}
