package mazeGame;

import edu.princeton.cs.algs4.Graph;

public class Maze {
	Graph mazeHolder;

	Maze(int size) {

		mazeHolder = MazeGenerator.newMaze(size);
	}

}
