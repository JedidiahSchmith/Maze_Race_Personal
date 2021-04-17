package mazeGame;

import java.util.Hashtable;

import edu.princeton.cs.algs4.Graph;
import usefulMethods.UsefulMethods;

public class Maze {
	private Graph mazeGraph;
	private int width;
	private int size;

	private Entity player = new Entity();
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

		player.currentColumn = playerCurrentColumn;
		player.currentRow = playerCurrentRow;

		this.computerCurrentColumn = computerCurrentColumn;//
		this.computerCurrentRow = computerCurrentRow;

		do {
			goalVertex = (int) (Math.random() * size);
		} while (goalVertex == getComputerVertex() || goalVertex == getPlayerVertex());

		known = new Hashtable<Integer, Boolean>(width);

		known.put(UsefulMethods.colAndRowToVertex(computerCurrentColumn, computerCurrentRow, width), true);
		known.put(UsefulMethods.colAndRowToVertex(playerCurrentColumn, playerCurrentRow, width), true);

		updateKnown();
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

		return player.currentColumn;
	}

	public int getPlayerCurrentRow() {

		return player.currentRow;
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

	public void movePlayer(Direction moveDirection) {

		int currentPlayerVertex = player.getVertex();

		Direction relativeDirection;
		loop: for (int newVertex : mazeGraph.adj(currentPlayerVertex)) {
			relativeDirection = UsefulMethods.relativeLocationOfNeighborVertex(currentPlayerVertex, newVertex, width);
			if (relativeDirection.equals(moveDirection)) {
				System.out.println(relativeDirection);
				switch (moveDirection) {
				case UP:
					player.currentRow--;
					break loop;
				case RIGHT:
					player.currentColumn--;
					break loop;
				case DOWN:
					player.currentRow++;
					break loop;
				case LEFT:
					player.currentColumn++;
					break loop;
				default:
					throw new IllegalArgumentException();
				}
			}
		}

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

	public Graph getMazeHolder() {
		return mazeGraph;
	}

	public int getWidth() {
		return width;
	}

	public int getSize() {
		return size;
	}

	public int getPlayerVertex() {
		return player.getVertex();
	}

	public int getComputerVertex() {
		return UsefulMethods.colAndRowToVertex(computerCurrentColumn, computerCurrentRow, width);
	}

	private class Entity {
		private int currentColumn;
		private int currentRow;

		public int getVertex() {
			return UsefulMethods.colAndRowToVertex(currentColumn, currentRow, width);
		}

		public int getCurrentColumn() {
			return currentColumn;
		}

		public int getCurrentRow() {
			return currentRow;
		}

	}

}
