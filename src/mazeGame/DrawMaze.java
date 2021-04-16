package mazeGame;

import java.awt.event.KeyEvent;

import edu.princeton.cs.algs4.StdDraw;
import usefulMethods.UsefulMethods;

public class DrawMaze {

	public static void runGame(Maze maze) {
		Direction direction = null;
		boolean gameRunning = true;
		boolean aKeyIsPressedDown = false;
		boolean directionSet = false;

		int rowLength = (int) Math.sqrt(maze.getMazeHolder().V());
		double gapSize = 0.5;

		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0.5, 0.97, "Maze Race");

		StdDraw.setXscale(-0.05 * rowLength, 1.05 * rowLength);
		StdDraw.setYscale(-0.05 * rowLength, 1.05 * rowLength);
		StdDraw.filledSquare(rowLength / 2.0, rowLength / 2.0, rowLength / 2.0);
		draw(maze);

		while (gameRunning) {

			if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
				direction = Direction.LEFT;
				aKeyIsPressedDown = directionSet = true;
			} else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
				direction = Direction.RIGHT;
				aKeyIsPressedDown = directionSet = true;
			} else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
				direction = Direction.DOWN;
				aKeyIsPressedDown = directionSet = true;
			} else if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
				direction = Direction.UP;
				aKeyIsPressedDown = directionSet = true;
			} else {
				aKeyIsPressedDown = false;
			}

			if (!aKeyIsPressedDown) {
				if (directionSet) {
					directionSet = false;
					maze.movePlayer(direction);
					draw(maze);
				}
			}
		}

	}

	public static void draw(Maze maze) {
		int mazeWidth = maze.getWidth();
		double gapSize = 0.5;

		int computerCurrentVertex = UsefulMethods.colAndRowToVertex(maze.getComputerCurrentColumn(),
				maze.getComputerCurrentRow(), mazeWidth);
		int playerCurrentVertex = UsefulMethods.colAndRowToVertex(maze.getPlayerCurrentColumn(),
				maze.getPlayerCurrentRow(), mazeWidth);

		StdDraw.setPenColor(StdDraw.WHITE);
		int[] address = new int[2];

		address = UsefulMethods.vertexToArray(computerCurrentVertex, mazeWidth);

		if (maze.isKnown(address[0], address[1])) {
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledSquare(address[0] + gapSize, mazeWidth - address[1] - gapSize, 0.45);
		}

		address = UsefulMethods.vertexToArray(playerCurrentVertex, mazeWidth);

		if (maze.isKnown(address[0], address[1])) {
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledSquare(address[0] + gapSize, mazeWidth - address[1] - gapSize, 0.45);
		}

		// checks if the vertices around the computer are known
		for (int vertix : maze.getMazeHolder().adj(computerCurrentVertex)) {
			address = UsefulMethods.vertexToArray(vertix, mazeWidth);
			if (maze.isKnown(address[0], address[1])) {
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.filledSquare(address[0] + gapSize, mazeWidth - address[1] - gapSize, 0.45);
			}
		}

		// checks if the vertices around the player are known
		for (int vertix : maze.getMazeHolder().adj(playerCurrentVertex)) {
			address = UsefulMethods.vertexToArray(vertix, mazeWidth);

			if (maze.isKnown(address[0], address[1])) {
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.filledSquare(address[0] + gapSize, mazeWidth - address[1] - gapSize, 0.45);
			}
		}
		int playerColumn = maze.getPlayerCurrentColumn();
		int playerRow = maze.getPlayerCurrentRow();

		int computerColumn = maze.getComputerCurrentColumn();
		int computerRow = maze.getComputerCurrentRow();
		// Draws player
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.filledSquare(playerColumn + gapSize, mazeWidth - playerRow - gapSize, 0.30);

		// Draws computer If on same square, computer and player will share it
		StdDraw.setPenColor(StdDraw.RED);
		if (computerColumn == playerColumn && computerRow == playerRow) {
			StdDraw.filledRectangle(computerColumn + gapSize + 0.15, mazeWidth - computerRow - gapSize, 0.15, 0.30);
		} else {
			StdDraw.filledSquare(computerColumn + gapSize, mazeWidth - computerRow - gapSize, 0.30);
		}

	}

}
