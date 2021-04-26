package mazeGame;

public class Game {
	public static void main(String[] nil) {
		boolean playing = true;
		boolean replay = true;
		int width = 2;
		Maze maze = new Maze(width, null, false);

		boolean keepPlayerPositionBetweenLevels = false;

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
				maze = new Maze(width++, maze, keepPlayerPositionBetweenLevels);
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