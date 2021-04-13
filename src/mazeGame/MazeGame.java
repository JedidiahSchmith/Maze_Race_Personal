package mazeGame;

public class MazeGame {
	public static void main(String[] nil) {
		int width = 3;
		int size = width * width;

		Maze maze = new Maze(size);

		System.out.println(maze.mazeHolder.toString());
	}
}
