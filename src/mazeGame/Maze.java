package mazeGame;

import java.util.Hashtable;

import usefulMethods.UsefulMethods;

public class Maze {
	private GraphWrapper mazeGraph;
	private int width;
	private int size;

	private int playerCurrentColumn;
	private int playerCurrentRow;
	private int computerCurrentColumn;
	private int computerCurrentRow;
	private int goalVertex;
	private Hashtable<Integer, Boolean> known;

	Maze(int width) {

		makeMaze(width, 0, 0, 0, 0);
	}

	Maze(int width, int playerCurrentColumn, int playerCurrentRow, int computerCurrentColumn, int computerCurrentRow) {
		makeMaze(width, playerCurrentColumn, playerCurrentRow, computerCurrentColumn, computerCurrentRow);
	}

	private void makeMaze(int width, int playerCurrentColumn, int playerCurrentRow, int computerCurrentColumn,
			int computerCurrentRow) {

		this.width = width;
		size = width * width;

		mazeGraph = MazeGenerator.noWallsMaze(width);

		if (playerCurrentColumn >= width || playerCurrentRow >= width || computerCurrentColumn >= width
				|| computerCurrentRow >= width)
			throw new IllegalArgumentException();

		this.playerCurrentColumn = playerCurrentColumn;
		this.playerCurrentRow = playerCurrentRow;

		this.computerCurrentColumn = computerCurrentColumn;//
		this.computerCurrentRow = computerCurrentRow;

		do {
			goalVertex = (int) (Math.random() * 25);
		} while (goalVertex == getComputerVertex() || goalVertex == getPlayerVertex());

		known = new Hashtable<Integer, Boolean>(width);

		known.put(UsefulMethods.colAndRowToVertex(computerCurrentColumn, computerCurrentRow, width), true);
		known.put(UsefulMethods.colAndRowToVertex(playerCurrentColumn, playerCurrentRow, width), true);

	}

	public boolean isKnown(int col, int row) {
		try {
			return known.get(UsefulMethods.colAndRowToVertex(col, row, width));
		} catch (java.lang.NullPointerException e) {
			return false;
		}
	}

	public boolean isKnown(int vertex) {
		try {
			return known.get(vertex);
		} catch (java.lang.NullPointerException e) {
			return false;
		}
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
			if (--playerCurrentRow < 0) {
				playerCurrentRow++;
				return;
			}
			break;
		case RIGHT:
			if (++playerCurrentColumn == width) {
				playerCurrentColumn--;
				return;
			}
			break;
		case DOWN:
			if (++playerCurrentRow == width) {
				playerCurrentRow--;
				return;
			}
			break;
		case LEFT:
			if (--playerCurrentColumn < 0) {
				playerCurrentColumn++;
				return;
			}
			break;
		default:
			throw new IllegalArgumentException();
		}
		moveComputer(direction);
		updateKnown();
	}

	private void updateKnown() {
		int computerVertex = getComputerVertex();
		int playerVertex = getPlayerVertex();

		if (!isKnown(playerVertex))
			known.put(playerVertex, true);

		if (!isKnown(computerVertex))
			known.put(computerVertex, true);

		for (int vertex : mazeGraph.adj(getComputerVertex())) {
			if (!isKnown(vertex))
				known.put(vertex, true);
		}
		for (int vertex : mazeGraph.adj(getPlayerVertex())) {
			if (!isKnown(vertex))
				known.put(vertex, true);
		}
	}

	public int getGoalVertex() {
		return goalVertex;
	}

	public GraphWrapper getMazeHolder() {
		return mazeGraph;
	}

	public int getWidth() {
		return width;
	}

	public int getSize() {
		return size;
	}

	public int getPlayerVertex() {
		return UsefulMethods.colAndRowToVertex(playerCurrentColumn, playerCurrentRow, width);
	}

	public int getComputerVertex() {
		return UsefulMethods.colAndRowToVertex(computerCurrentColumn, computerCurrentRow, width);
	}

}
