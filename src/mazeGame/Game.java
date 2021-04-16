package mazeGame;

public class Game {
	public static void main(String[] nil) {
		int width = 50;

		Maze maze = new Maze(width);

		DrawMaze.runGame(maze);

	}
}
