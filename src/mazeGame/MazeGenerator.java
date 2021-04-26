package mazeGame;

import dataStructure.RandomizedQueue;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import usefulMethods.UsefulMethods;

public class MazeGenerator {

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
