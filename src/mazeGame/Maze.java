package mazeGame;

import java.util.Hashtable;

import edu.princeton.cs.algs4.BreadthFirstPaths;
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
	private boolean inPlay = true;
	private int wins = 0;
	private int losses = 0;
	private int ties = 0;

	private int[] computerCameFromWhenVisited;
	private boolean computerPathToGoalMade = false;
	Queue<Integer> computerPathToGoal;

	Maze(int width, Maze oldMaze, boolean keepPlayerPositionBetweenLevels) {
		if (keepPlayerPositionBetweenLevels) {
			makeMaze(width, oldMaze.getPlayer().getCurrentColumn(), oldMaze.getPlayer().getCurrentRow(),
					oldMaze.getComputer().getCurrentColumn(), oldMaze.getComputer().getCurrentRow());
		} else {
			makeMaze(width, 0, 0, 0, 0);
		}
		if (oldMaze == null) {
			setWins(0);
			setLosses(0);
			setTies(0);

		} else {
			setWins(oldMaze.getWins());
			setLosses(oldMaze.getLosses());
			setTies(oldMaze.getTies());
		}
	}

	private void makeMaze(int width, int playerCurrentColumn, int playerCurrentRow, int computerCurrentColumn,
			int computerCurrentRow) {

		if (playerCurrentColumn >= width || playerCurrentRow >= width || computerCurrentColumn >= width
				|| computerCurrentRow >= width)
			throw new IllegalArgumentException();

		this.width = width;
		size = width * width;

		mazeGraph = MazeGenerator.generateMaze(width);

		player = new Entity(playerCurrentColumn, playerCurrentRow);

		computer = new Entity(computerCurrentColumn, computerCurrentRow);

		createGoalVertex();

		known = new Hashtable<Integer, Boolean>(width);

		known.put(UsefulMethods.colAndRowToVertex(computerCurrentColumn, computerCurrentRow, width), true);
		known.put(UsefulMethods.colAndRowToVertex(playerCurrentColumn, playerCurrentRow, width), true);

		player.setVisited(getPlayerVertex());
		computer.setVisited(getComputerVertex());

		computerCameFromWhenVisited = new int[size];

		computerCameFromWhenVisited[computer.getVertex()] = computer.getVertex();

		updateKnown();
	}

	private void createGoalVertex() {

		do {
			boolean[] goalsBeenBefore = new boolean[size];
			goalVertex = (int) (Math.random() * size);
			byte timesLooped = 0;
			int newVertex = goalVertex;
			do {
				goalVertex = newVertex;
				goalsBeenBefore[goalVertex] = true;
				timesLooped = 0;
				for (int vertex : mazeGraph.adj(goalVertex)) {
					timesLooped++;

					if (!goalsBeenBefore[vertex]) {
						newVertex = vertex;
					}

				}

			} while (timesLooped > 1);
		} while ((goalVertex == getComputerVertex() || goalVertex == getPlayerVertex()) && width != 1);
	}

	public Direction computersNextMove() {
		if (isGoalKnown()) {
			if (!computerPathToGoalMade) {
				computerPathToGoal = new Queue<Integer>();
				BreadthFirstPaths computerPathToGoalFinder = new BreadthFirstPaths(mazeGraph, getComputerVertex());
				for (Integer nextVertexToGoal : computerPathToGoalFinder.pathTo(goalVertex)) {
					computerPathToGoal.enqueue(nextVertexToGoal);
				}
				computerPathToGoalMade = true;
				// first Node returned from goalfinder is the currentNode. Needed to be removed
				computerPathToGoal.dequeue();

			}
			computer.setVisited(computer.getVertex());
			return UsefulMethods.relativeLocationOfNeighborVertex(getComputerVertex(), computerPathToGoal.dequeue(),
					width);
		}

		Iterable<Integer> neighbors = mazeGraph.adj(getComputerVertex());
		for (Integer neighbor : neighbors) {
			if (!player.getVisited()[neighbor] && !computer.getVisited()[neighbor]) {
				computer.setVisited(neighbor);
				computerCameFromWhenVisited[neighbor] = computer.getVertex();
				return UsefulMethods.relativeLocationOfNeighborVertex(getComputerVertex(), neighbor, width);
			}
		}

		for (Integer neighbor : neighbors) {

			if (!computer.getVisited()[neighbor]) {
				computer.setVisited(neighbor);
				computerCameFromWhenVisited[neighbor] = computer.getVertex();
				return UsefulMethods.relativeLocationOfNeighborVertex(getComputerVertex(), neighbor, width);
			}
		}
		return UsefulMethods.relativeLocationOfNeighborVertex(getComputerVertex(),
				computerCameFromWhenVisited[computer.getVertex()], width);
	}

	private boolean isGoalKnown() {
		int[] goalAddress = UsefulMethods.vertexToArray(goalVertex, width);
		return isKnown(goalAddress[0], goalAddress[1]);
	}

	public void moveEntity(Direction moveDirection, Entity entity) {
		int currentEntityVertex = entity.getVertex();

		entity.setVisited(currentEntityVertex);

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

	public boolean isInPlay() {
		return inPlay;
	}

	public void setInPlay(boolean inPlay) {
		this.inPlay = inPlay;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getTies() {
		return ties;
	}

	public void setTies(int ties) {
		this.ties = ties;
	}

	class Entity {
		private int currentColumn;
		private int currentRow;
		private boolean[] visited;

		Entity(int currentColumn, int currentRow) {
			this.currentColumn = currentColumn;
			this.currentRow = currentRow;
			visited = new boolean[size];
		}

		public boolean[] getVisited() {
			return visited;
		}

		public void setVisited(int index) {
			visited[index] = true;
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
