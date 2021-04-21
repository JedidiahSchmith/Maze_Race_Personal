package mazeGame;

import dataStructure.RandomizedQueue;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
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

	public static Graph generateMaze(int width) {
		Graph graphToReturn = new Graph(width * width);

		RandomizedQueue<Edge> validEdges = new RandomizedQueue<Edge>();
		WeightedQuickUnionUF linkedVertciesChecker = new WeightedQuickUnionUF(width * width);

		for (int vertex = 0; vertex < graphToReturn.V(); vertex++)
			for (int adjVertex : UsefulMethods.adjVertices(vertex, width)) {
				validEdges.enqueue(new Edge(vertex, adjVertex, 0));
			}

		Edge edgeToTest;
		int eitherVertex;
		int otherVertex;
		while (!validEdges.isEmpty()) {
			edgeToTest = validEdges.dequeue();
			eitherVertex = edgeToTest.either();
			otherVertex = edgeToTest.other(eitherVertex);
			if (linkedVertciesChecker.find(eitherVertex) != linkedVertciesChecker.find(otherVertex)) {
				graphToReturn.addEdge(eitherVertex, otherVertex);
				linkedVertciesChecker.union(eitherVertex, otherVertex);
			}
		}

		return graphToReturn;
	}

}
