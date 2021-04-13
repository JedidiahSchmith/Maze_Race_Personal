package mazeGame;

import edu.princeton.cs.algs4.Graph;

public class Maze {
	Graph mazeHolder;

	Maze(int size) {
		mazeHolder = MazeGenerator.newMaze(size);

	}

	public boolean isVisited(int col, int row) {
		if (Math.sqrt(mazeHolder.V()) - 1 == row)
			return true;

		return false;
	}

	public boolean isOccupied(int column, int row) {
		if (column == row && column == Math.sqrt(mazeHolder.V()) - 1)
			return true;
		else
			return false;
	}

	public int getCurrentColumn() {

		return (int) (Math.sqrt(mazeHolder.V()) - 1);
	}

	public int getCurrentRow() {

		return (int) (Math.sqrt(mazeHolder.V()) - 1);
	}

}
