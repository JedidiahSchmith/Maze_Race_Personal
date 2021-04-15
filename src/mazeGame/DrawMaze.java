package mazeGame;

import edu.princeton.cs.algs4.StdDraw;

public class DrawMaze {

	public static void draw(Maze maze) {
		int rowLength = (int) Math.sqrt(maze.getMazeHolder().V());
		double gapSize = 0.5;
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setXscale(-0.05 * rowLength, 1.05 * rowLength);
		StdDraw.setYscale(-0.05 * rowLength, 1.05 * rowLength);
		StdDraw.filledSquare(rowLength / 2.0, rowLength / 2.0, rowLength / 2.0);
		StdDraw.text(gapSize, rowLength + 0.10, "Maze Race");

		StdDraw.setPenColor(StdDraw.WHITE);
		for (int row = 0; row < rowLength; row++) {
			for (int col = 0; col < rowLength; col++) {

				if (maze.isKnown(col, row)) {
					StdDraw.setPenColor(StdDraw.WHITE);
				} else {
					StdDraw.setPenColor(StdDraw.BLACK);
				}
				StdDraw.filledSquare(col + gapSize, rowLength - row - gapSize, 0.45);

			}

		}

		int playerColumn = maze.getPlayerCurrentColumn();
		int playerRow = maze.getPlayerCurrentRow();

		int computerColumn = maze.getComputerCurrentColumn();
		int computerRow = maze.getComputerCurrentRow();
		// Draws player
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.filledSquare(playerColumn + gapSize, rowLength - playerRow - gapSize, 0.30);

		// Draws computer If on same square, computer and player will share it
		StdDraw.setPenColor(StdDraw.RED);
		if (computerColumn == playerColumn && computerRow == playerRow) {
			StdDraw.filledRectangle(computerColumn + gapSize + 0.15, rowLength - computerRow - gapSize, 0.15, 0.30);
		} else {
			StdDraw.filledSquare(computerColumn + gapSize, rowLength - computerRow - gapSize, 0.30);
		}

	}

}
