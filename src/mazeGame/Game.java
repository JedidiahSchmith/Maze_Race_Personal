package mazeGame;

public class Game {
	public static void main(String[] nil) {
		int width = 50;
		int size = width * width;

		Maze maze = new Maze(width);

		DrawMaze.runGame(maze);

	}
}
