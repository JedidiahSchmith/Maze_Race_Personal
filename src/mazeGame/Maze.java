package mazeGame;

import java.util.Hashtable;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;
import usefulMethods.UsefulMethods;

public class Maze {
	private Graph mazeGraph;
	private int width;
	private int size;

	private Entity player;
	private Entity computer;
	private int goalVertex;
	private Hashtable<Integer, Boolean> known;

	private boolean[] computerVisited;
	private int[] computerCameFromWhenVisited;

	Maze(int width) {

		makeMaze(width, 0, 0, 0, 0);
	}

	Maze(int width, int playerCurrentColumn, int playerCurrentRow, int computerCurrentColumn, int computerCurrentRow) {
		makeMaze(width, playerCurrentColumn, playerCurrentRow, computerCurrentColumn, computerCurrentRow);
	}

	private void makeMaze(int width, int playerCurrentColumn, int playerCurrentRow, int computerCurrentColumn,
			int computerCurrentRow) {

		if (playerCurrentColumn >= width || playerCurrentRow >= width || computerCurrentColumn >= width
				|| computerCurrentRow >= width)
			throw new IllegalArgumentException();

		this.width = width;
		size = width * width;

		computerVisited = new boolean[size];
		computerCameFromWhenVisited = new int[size];

		mazeGraph = MazeGenerator.generateMaze(width);

		player = new Entity(playerCurrentColumn, playerCurrentRow);

		computer = new Entity(computerCurrentColumn, computerCurrentRow);

		do {
			goalVertex = (int) (Math.random() * size);
		} while ((goalVertex == getComputerVertex() || goalVertex == getPlayerVertex()) && width != 1);

		known = new Hashtable<Integer, Boolean>(width);

		known.put(UsefulMethods.colAndRowToVertex(computerCurrentColumn, computerCurrentRow, width), true);
		known.put(UsefulMethods.colAndRowToVertex(playerCurrentColumn, playerCurrentRow, width), true);

		computerVisited[computer.getVertex()] = true;
		computerCameFromWhenVisited[computer.getVertex()] = computer.getVertex();

		updateKnown();
	}

	public Direction computersNextMove() {
		Iterable<Integer> neighbors = mazeGraph.adj(getComputerVertex());

		for (Integer neighbor : neighbors) {
			if (!computerVisited[neighbor]) {
				computerVisited[neighbor] = true;
				computerCameFromWhenVisited[neighbor] = computer.getVertex();
				return UsefulMethods.relativeLocationOfNeighborVertex(getComputerVertex(), neighbor, width);
			}
		}
		return UsefulMethods.relativeLocationOfNeighborVertex(getComputerVertex(),
				computerCameFromWhenVisited[computer.getVertex()], width);
	}

	public void moveEntity(Direction moveDirection, Entity entity) {
		int currentEntityVertex = entity.getVertex();

		Direction relativeDirection;
		loop: for (int newVertex : mazeGraph.adj(currentEntityVertex)) {
			relativeDirection = UsefulMethods.relativeLocationOfNeighborVertex(currentEntityVertex, newVertex, width);
			if (relativeDirection.equals(moveDirection)) {
				switch (moveDirection) {
				case DOWN:
					entity.decrementRow();
					break loop;
				case LEFT:
					entity.decrementColumn();
					break loop;
				case UP:
					entity.incrementRow();
					break loop;
				case RIGHT:
					entity.incrementColumn();
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

	public boolean isKnown(int col, int row) {

		return isKnown(UsefulMethods.colAndRowToVertex(col, row, width));

	}

	public boolean isKnown(int vertex) {
		try {
			return known.get(vertex);
		} catch (java.lang.NullPointerException e) {
			return false;
		}
	}

	public Entity getPlayer() {
		return player;
	}

	public Entity getComputer() {
		return computer;
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
		return computer.getVertex();
	}

	class Entity {
		private int currentColumn;
		private int currentRow;

		Entity(int currentColumn, int currentRow) {
			this.currentColumn = currentColumn;
			this.currentRow = currentRow;
		}

		public int getVertex() {
			return UsefulMethods.colAndRowToVertex(currentColumn, currentRow, width);
		}

		public int getCurrentColumn() {
			return currentColumn;
		}

		public int getCurrentRow() {
			return currentRow;
		}

		public void incrementColumn() {
			currentColumn++;
		}

		public void decrementColumn() {
			currentColumn--;
		}

		public void incrementRow() {
			currentRow++;
		}

		public void decrementRow() {
			currentRow--;
		}
	}
}
