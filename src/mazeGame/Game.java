package mazeGame;

public class Game {
	public static void main(String[] nil) {
		int width = 10;
		int size = width * width;

		Maze maze = new Maze(size);

		DrawMaze window = new DrawMaze(maze);
		System.out.println(maze.mazeHolder.toString());
	}
}
