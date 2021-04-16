package mazeGame;

import java.awt.Color;
import java.awt.event.KeyEvent;

import edu.princeton.cs.algs4.StdDraw;
import usefulMethods.UsefulMethods;

public class DrawMaze {

	private static final Color BACKGROUND_COLOR = StdDraw.WHITE;
	private static final Color PLAYER_COLOR = StdDraw.GREEN;
	private static final Color COMPUTER_COLOR = StdDraw.RED;
	private static final Color GOAL_COLOR = StdDraw.YELLOW;

	public static void runGame(Maze maze) {
		Direction direction = null;
		boolean aKeyIsPressedDown = false;
		boolean directionSet = false;

		int rowLength = (int) Math.sqrt(maze.getMazeHolder().V());

		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0.5, 0.97, "Maze Race");

		StdDraw.setXscale(-0.05 * rowLength, 1.05 * rowLength);
		StdDraw.setYscale(-0.05 * rowLength, 1.05 * rowLength);
		StdDraw.filledSquare(rowLength / 2.0, rowLength / 2.0, rowLength / 2.0);
		draw(maze);

		while (maze.getPlayerVertex() != maze.getGoalVertex() || maze.getComputerVertex() != maze.getGoalVertex()) {

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
		double radiusOfSquare = 0.5;

		int computerCurrentVertex = maze.getComputerVertex();
		int playerCurrentVertex = maze.getPlayerVertex();

		double gridSquareSize = 0.45;

		double playerSquareSize = 0.30;

		StdDraw.setPenColor(BACKGROUND_COLOR);
		int[] currentAddress = new int[2];
		int[] adjecentAddress = new int[2];
		double[][] rectangleArray = new double[4][4];

		// colors the goal
		currentAddress = UsefulMethods.vertexToArray(computerCurrentVertex, mazeWidth);
		if (maze.isKnown(currentAddress[0], currentAddress[1])) {
			StdDraw.setPenColor(BACKGROUND_COLOR);
			if (computerCurrentVertex == maze.getGoalVertex())
				StdDraw.setPenColor(GOAL_COLOR);

			StdDraw.filledSquare(currentAddress[0] + radiusOfSquare, mazeWidth - currentAddress[1] - radiusOfSquare,
					gridSquareSize);
		}
		currentAddress = UsefulMethods.vertexToArray(playerCurrentVertex, mazeWidth);
		if (maze.isKnown(currentAddress[0], currentAddress[1])) {
			StdDraw.setPenColor(BACKGROUND_COLOR);

			if (computerCurrentVertex == maze.getGoalVertex())
				StdDraw.setPenColor(GOAL_COLOR);

			StdDraw.filledSquare(currentAddress[0] + radiusOfSquare, mazeWidth - currentAddress[1] - radiusOfSquare,
					gridSquareSize);
		}

		// checks if the vertices around the computer are known
		for (int vertix : maze.getMazeHolder().adj(computerCurrentVertex)) {
			adjecentAddress = UsefulMethods.vertexToArray(vertix, mazeWidth);
			if (maze.isKnown(adjecentAddress[0], adjecentAddress[1])) {
				if (UsefulMethods.colAndRowToVertex(adjecentAddress[0], adjecentAddress[1], mazeWidth) == maze
						.getGoalVertex()) {
					StdDraw.setPenColor(GOAL_COLOR);
					StdDraw.filledSquare(adjecentAddress[0] + radiusOfSquare,
							mazeWidth - adjecentAddress[1] - radiusOfSquare, gridSquareSize);

					currentAddress = UsefulMethods.vertexToArray(computerCurrentVertex, mazeWidth);
					StdDraw.setPenColor(BACKGROUND_COLOR);
					StdDraw.filledSquare(currentAddress[0] + radiusOfSquare,
							mazeWidth - currentAddress[1] - radiusOfSquare, gridSquareSize);
				} else {
					StdDraw.setPenColor(BACKGROUND_COLOR);
					// StdDraw.filledRectangle(computerCurrentVertex,
					// playerCurrentVertex,gridSquareSize, gridSquareSize);
				}
			}
		}

		// checks if the vertices around the player are known and draws them in
		currentAddress = UsefulMethods.vertexToArray(playerCurrentVertex, mazeWidth);
		// forLoop:
		for (int vertix : maze.getMazeHolder().adj(playerCurrentVertex)) {
			adjecentAddress = UsefulMethods.vertexToArray(vertix, mazeWidth);

			if (maze.isKnown(adjecentAddress[0], adjecentAddress[1])) {

				if (UsefulMethods.colAndRowToVertex(adjecentAddress[0], adjecentAddress[1], mazeWidth) == maze
						.getGoalVertex()) {
					StdDraw.setPenColor(GOAL_COLOR);
				} else {
					StdDraw.setPenColor(BACKGROUND_COLOR);
				}

				// fills in boarder between adjacent squares
				{
					// following Comments apply to first iteration only

					// corner 1 x value
					rectangleArray[0][0] = currentAddress[0];
					// corner 1 y value
					rectangleArray[1][0] = mazeWidth - currentAddress[1];

					// corner 2 x value
					rectangleArray[0][1] = adjecentAddress[0];
					// corner 2 y value
					rectangleArray[1][1] = mazeWidth - adjecentAddress[1];

					// corner 3 x value
					rectangleArray[0][2] = currentAddress[0] + gridSquareSize;
					// corner 3 y value
					rectangleArray[1][3] = mazeWidth - currentAddress[1];

					// corner 4 x value
					rectangleArray[0][3] = adjecentAddress[0] + gridSquareSize;
					// corner 4 y value
					rectangleArray[1][2] = mazeWidth - adjecentAddress[1];

					StdDraw.filledPolygon(rectangleArray[0], rectangleArray[1]);
				}

				// draws adjacent squares
				// StdDraw.filledSquare(adjecentAddress[0] + gapSize, mazeWidth -
				// adjecentAddress[1] - gapSize, gridSquareSize);

				// break forLoop;
			}
		}
		int playerColumn = maze.getPlayerCurrentColumn();
		int playerRow = maze.getPlayerCurrentRow();

		int computerColumn = maze.getComputerCurrentColumn();
		int computerRow = maze.getComputerCurrentRow();
		// Draws player
		StdDraw.setPenColor(PLAYER_COLOR);
		StdDraw.filledSquare(playerColumn + radiusOfSquare, mazeWidth - playerRow - radiusOfSquare, playerSquareSize);

		// Draws computer If on same square, computer and player will share it
		StdDraw.setPenColor(COMPUTER_COLOR);
		if (computerColumn == playerColumn && computerRow == playerRow) {
			StdDraw.filledRectangle(computerColumn + radiusOfSquare + playerSquareSize / 2,
					mazeWidth - computerRow - radiusOfSquare, playerSquareSize / 2, playerSquareSize);
		} else {
			StdDraw.filledSquare(computerColumn + radiusOfSquare, mazeWidth - computerRow - radiusOfSquare,
					playerSquareSize);
		}

	}

}
