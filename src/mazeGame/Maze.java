package mazeGame;

import edu.princeton.cs.algs4.Graph;

public class Maze {
	Graph mazeHolder;

	private int playerCurrentColumn;
	private int playerCurrentRow;
	private int computerCurrentColumn;
	private int computerCurrentRow;

	private boolean[][] visited;

	Maze(int size) {
		mazeHolder = MazeGenerator.newMaze(size);
		playerCurrentColumn = (int) (Math.sqrt(mazeHolder.V()) - 2);
		playerCurrentRow = (int) (Math.sqrt(mazeHolder.V()) - 1);

		computerCurrentColumn = (int) (Math.sqrt(mazeHolder.V()) - 1);
		computerCurrentRow = (int) (Math.sqrt(mazeHolder.V()) - 2);

		visited = new boolean[(int) Math.sqrt(mazeHolder.V())][(int) Math.sqrt(mazeHolder.V())];

		visited[visited.length - 1][visited.length - 1] = true;

		visited[visited.length - 2][visited.length - 1] = true;
		visited[visited.length - 1][visited.length - 2] = true;

		visited[playerCurrentColumn - 1][playerCurrentRow] = true;
		visited[computerCurrentColumn - 1][computerCurrentRow - 1] = true;

	}

	public boolean isVisited(int col, int row) {
		if ((playerCurrentColumn == col && playerCurrentRow == row)
				|| (computerCurrentColumn == col && computerCurrentRow == row))
			return true;
		else
			return visited[col][row];

	}

	public int getPlayerCurrentColumn() {

		return playerCurrentColumn;
	}

	public int getPlayerCurrentRow() {

		return playerCurrentRow;
	}

	public int getComputerCurrentColumn() {
		return computerCurrentColumn;
	}

	public int getComputerCurrentRow() {
		return computerCurrentRow;
	}

}
