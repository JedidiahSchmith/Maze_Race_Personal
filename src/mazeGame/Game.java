package mazeGame;

public class Game {
	public static void main(String[] nil) {
		boolean playing = true;
		boolean replay = true;
		int width = 2;
		Maze maze = new Maze(width);
		int playerCol;
		int playerRow;
		int computerCol;
		int computerRow;

		boolean keepPlayerPositionBetweenLevels = true;

		int endGameLevel = 27;
		endGameLevel = -1;

		while (playing) {
			try {
				// Momentarily pauses thread to save CPU power from unnecessary key event
				// checks.
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (replay) {
				if (keepPlayerPositionBetweenLevels) {
					playerCol = maze.getPlayer().getCurrentColumn();
					playerRow = maze.getPlayer().getCurrentRow();
					computerCol = maze.getComputer().getCurrentColumn();
					computerRow = maze.getComputer().getCurrentRow();

					maze = new Maze(width++, playerCol, playerRow, computerCol, computerRow);
				} else {
					maze = new Maze(width++);
				}
				DrawMaze.runGame(maze);
				replay = false;
			}
			replay = !maze.isInPlay();
			if (width == endGameLevel - 1) {
				playing = false;
			}
		}
	}

}