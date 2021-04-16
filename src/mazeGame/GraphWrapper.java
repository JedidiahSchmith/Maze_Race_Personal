package mazeGame;

import edu.princeton.cs.algs4.Graph;

public class GraphWrapper extends Graph {

	public GraphWrapper(int i) {
		super(i);
	}

	public Iterable<Integer> adj(int playerVertex) {
		return super.adj(playerVertex);
	}

}
