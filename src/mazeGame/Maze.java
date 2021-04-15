package mazeGame;

import java.util.Hashtable;

import edu.princeton.cs.algs4.Graph;

public class Maze {
	private Graph mazeGraph;

	private int playerCurrentColumn;
	private int playerCurrentRow;
	private int computerCurrentColumn;
	private int computerCurrentRow;

	private Hashtable<Integer, Boolean> known;

	Maze(int size) {
		makeMaze(size, 0, 0, 0, 0);
	}

	Maze(int size, int playerCurrentColumn, int playerCurrentRow, int computerCurrentColumn, int computerCurrentRow) {
		makeMaze(size, playerCurrentColumn, playerCurrentRow, computerCurrentColumn, computerCurrentRow);
	}

	private void makeMaze(int size, int playerCurrentColumn, int playerCurrentRow, int computerCurrentColumn,
			int computerCurrentRow) {

		mazeGraph = MazeGenerator.newMaze(size);
		{
			int width = (int) (Math.sqrt(mazeGraph.V()) - 1);
			if (playerCurrentColumn > width || playerCurrentRow > width || computerCurrentColumn > width
					|| computerCurrentRow > width)
				throw new IllegalArgumentException();
		}

		this.playerCurrentColumn = playerCurrentColumn;
		this.playerCurrentRow = playerCurrentRow;

		this.computerCurrentColumn = computerCurrentColumn;//
		this.computerCurrentRow = computerCurrentRow;

		known = new Hashtable<Integer, Boolean>(size);

		known.put((computerCurrentColumn * computerCurrentRow + computerCurrentColumn), true);
		known.put((playerCurrentColumn * playerCurrentRow + playerCurrentColumn), true);

	}

	public boolean isKnown(int col, int row) {
		return known.contains(col * row + col);
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

	public void moveComputer(Direction direction) {

		switch (direction) {
		case UP:
			computerCurrentRow--;
			break;
		case RIGHT:
			computerCurrentColumn++;
			break;
		case DOWN:
			computerCurrentRow++;
			break;
		case LEFT:
			computerCurrentColumn--;
			break;
		default:
			throw new IllegalArgumentException();
		}

		updateKnown();
	}

	public void movePlayer(Direction direction) {

		switch (direction) {
		case UP:
			playerCurrentRow--;
			break;
		case RIGHT:
			playerCurrentColumn++;
			break;
		case DOWN:
			playerCurrentRow++;
			break;
		case LEFT:
			playerCurrentColumn--;
			break;
		default:
			throw new IllegalArgumentException();
		}

		updateKnown();
	}

	private void updateKnown() {
		for (int vertex : mazeGraph.adj(computerCurrentColumn * computerCurrentRow + computerCurrentColumn)) {
			known.put(vertex, true);
		}
		for (int vertex : mazeGraph.adj(playerCurrentColumn * playerCurrentRow + playerCurrentColumn))
			known.put(vertex, true);

	}

	public Graph getMazeHolder() {
		return mazeGraph;
	}

}
