package mazeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import edu.princeton.cs.algs4.StdDraw;
import usefulMethods.UsefulMethods;

public class DrawMaze {

	private static final Color BACKGROUND_COLOR = StdDraw.WHITE;
	private static final Color PLAYER_COLOR = StdDraw.GREEN;
	private static final Color COMPUTER_COLOR = StdDraw.RED;
	private static final Color GOAL_COLOR = StdDraw.YELLOW;

	public static void runGame(Maze maze) {
		StdDraw.enableDoubleBuffering();
		Direction direction = null;
		boolean aKeyIsPressedDown = false;
		boolean directionSet = false;

		int rowLength = (int) Math.sqrt(maze.getMazeHolder().V());
		StdDraw.setXscale(-0.05 * rowLength, 1.05 * rowLength);
		StdDraw.setYscale(-0.05 * rowLength, 1.05 * rowLength);
		StdDraw.setFont();
		StdDraw.setPenColor();
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(rowLength * 0.5, rowLength * 1.02, "Maze Race");
		StdDraw.text(rowLength * 0.8, rowLength * 1.02, "Level " + (maze.getWidth() - 1));

		StdDraw.filledSquare(rowLength / 2.0, rowLength / 2.0, rowLength / 2.0);
		draw(maze);
		while (maze.getPlayerVertex() != maze.getGoalVertex() && maze.getComputerVertex() != maze.getGoalVertex()) {

			try {
				// Momentarily pauses thread to save CPU power from unnecessary key event
				// checks.
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

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
					// moves computer in random direction every time the player moves
					maze.moveEntity(maze.computersNextMove(), maze.getComputer());
					maze.moveEntity(direction, maze.getPlayer());
					draw(maze);
				}
			}
		}

		WinStatus winState;

		if (maze.getPlayerVertex() == maze.getGoalVertex() && maze.getComputerVertex() == maze.getGoalVertex()) {
			winState = WinStatus.TIE;
			StdDraw.setPenColor(GOAL_COLOR);
		} else if (maze.getPlayerVertex() == maze.getGoalVertex()) {
			winState = WinStatus.WIN;
			StdDraw.setPenColor(PLAYER_COLOR);
		} else {
			winState = WinStatus.LOSE;
			StdDraw.setPenColor(COMPUTER_COLOR);
		}

		double mazeWidth = maze.getWidth();
		StdDraw.setFont();
		StdDraw.setFont(new Font("san sarif", Font.BOLD, 50));
		switch (winState) {
		case WIN:
			StdDraw.text(mazeWidth / 2, mazeWidth / 2, "You win!");
			break;
		case LOSE:
			StdDraw.text(mazeWidth / 2, mazeWidth / 2, "You Lose!");
			break;
		case TIE:
			StdDraw.text(mazeWidth / 2, mazeWidth / 2, "It's a Tie!");
			break;

		}
		StdDraw.setFont(new Font("san sarif", Font.PLAIN, 20));
		StdDraw.setPenColor(Color.MAGENTA);
		StdDraw.text(mazeWidth * 0.5, mazeWidth * 0.3, "Press 'Enter' to go to next level.");
		StdDraw.show();

		while (maze.isInPlay()) {
			try {
				// Momentarily pauses thread to save CPU power from unnecessary key event
				// checks.
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
				maze.setInPlay(false);
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

		// colors the goal
		currentAddress = UsefulMethods.vertexToArray(computerCurrentVertex, mazeWidth);
		if (maze.isKnown(currentAddress[0], currentAddress[1])) {
			StdDraw.setPenColor(BACKGROUND_COLOR);
			if (computerCurrentVertex == maze.getGoalVertex())
				StdDraw.setPenColor(GOAL_COLOR);

			StdDraw.filledSquare(currentAddress[0] + radiusOfSquare, currentAddress[1] + radiusOfSquare,
					gridSquareSize);
		}
		currentAddress = UsefulMethods.vertexToArray(playerCurrentVertex, mazeWidth);
		if (maze.isKnown(currentAddress[0], currentAddress[1])) {
			StdDraw.setPenColor(BACKGROUND_COLOR);
			if (playerCurrentVertex == maze.getGoalVertex())
				StdDraw.setPenColor(GOAL_COLOR);

			StdDraw.filledSquare(currentAddress[0] + radiusOfSquare, currentAddress[1] + radiusOfSquare,
					gridSquareSize);
		}

		// checks if the vertices around the computer are known
		fillInAroundAgent(maze, computerCurrentVertex, mazeWidth, adjecentAddress, radiusOfSquare, gridSquareSize);

		// checks if the vertices around the player are known and draws them in

		fillInAroundAgent(maze, playerCurrentVertex, mazeWidth, adjecentAddress, radiusOfSquare, gridSquareSize);

		int playerColumn = maze.getPlayer().getCurrentColumn();
		int playerRow = maze.getPlayer().getCurrentRow();
		int computerColumn = maze.getComputer().getCurrentColumn();
		int computerRow = maze.getComputer().getCurrentRow();
		// Draws player
		StdDraw.setPenColor(PLAYER_COLOR);
		StdDraw.filledSquare(playerColumn + radiusOfSquare, playerRow + radiusOfSquare, playerSquareSize);

		// Draws computer If on same square, computer and player will share it
		StdDraw.setPenColor(COMPUTER_COLOR);
		if (computerColumn == playerColumn && computerRow == playerRow) {
			StdDraw.filledRectangle(computerColumn + radiusOfSquare + playerSquareSize / 2,
					computerRow + radiusOfSquare, playerSquareSize / 2, playerSquareSize);
		} else {
			StdDraw.filledSquare(computerColumn + radiusOfSquare, computerRow + radiusOfSquare, playerSquareSize);
		}
		StdDraw.show();
	}

	private static void fillInAroundAgent(Maze maze, int CurrentVertex, int width, int[] adjecentAddress,
			double radiusOfSquare, double gridSquareSize) {
		double neighborDepth = 0.275;

		for (int adjVertix : maze.getMazeHolder().adj(CurrentVertex)) {
			adjecentAddress = UsefulMethods.vertexToArray(adjVertix, width);
			if (maze.isKnown(adjecentAddress[0], adjecentAddress[1])) {

				if (UsefulMethods.colAndRowToVertex(adjecentAddress[0], adjecentAddress[1], width) == maze
						.getGoalVertex()) {
					StdDraw.setPenColor(GOAL_COLOR);
				} else {
					StdDraw.setPenColor(BACKGROUND_COLOR);
				}

				// removes boarder

				switch (UsefulMethods.relativeLocationOfNeighborVertex(CurrentVertex, adjVertix, width)) {
				case UP:
					StdDraw.filledRectangle(adjecentAddress[0] + radiusOfSquare,
							adjecentAddress[1] + radiusOfSquare - neighborDepth, gridSquareSize, neighborDepth);
					break;
				case LEFT:
					StdDraw.filledRectangle(adjecentAddress[0] + radiusOfSquare + neighborDepth,
							adjecentAddress[1] + radiusOfSquare, neighborDepth, gridSquareSize);

					break;
				case DOWN:
					StdDraw.filledRectangle(adjecentAddress[0] + radiusOfSquare,
							adjecentAddress[1] + radiusOfSquare + neighborDepth, gridSquareSize, neighborDepth);
					break;
				case RIGHT:
					StdDraw.filledRectangle(adjecentAddress[0] + radiusOfSquare - neighborDepth,
							adjecentAddress[1] + radiusOfSquare, neighborDepth, gridSquareSize);
					break;
				}

				if (maze.getComputer().getVisited()[adjVertix] || maze.getPlayer().getVisited()[adjVertix]) {
					StdDraw.filledSquare(adjecentAddress[0] + radiusOfSquare, adjecentAddress[1] + radiusOfSquare,
							gridSquareSize);

				}

			}

		}
	}

	private enum WinStatus {
		WIN, LOSE, TIE;
	}
}
