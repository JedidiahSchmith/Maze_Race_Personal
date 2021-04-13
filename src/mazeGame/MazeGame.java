package mazeGame;

public class MazeGame {
	public static void main(String[] nil) {
		int size = 5;
		Maze maze = new Maze(size);

		System.out.println(maze.mazeHolder.toString());
	}
}
