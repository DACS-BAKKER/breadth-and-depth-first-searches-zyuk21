/*
 File: MazeGenerator.java
 Name: Alex Yuk
 Date: 11/02/2019
 */

import java.util.Random;
import java.util.Scanner;

public class MazeGenerator {

	// Location classed used to store x and y
	private class Location {
		private int x;
		private int y;

		public Location(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[]args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("What size would you like the maze to be\n? ");
		MazeGenerator mg = new MazeGenerator(sc.nextInt());
		mg.generateDFS();
		System.out.println("Please scroll up to see the generation process");
	}

	private int size;
	private Random rd = new Random();
	private Stack<Location> st = new Stack<Location>();
	private Character[][] maze;
	private Location start;
	private Location end;

	// Constructor
	public MazeGenerator(int size) {
		// To make sure that walls are only one block thick
		if (size % 2 == 0)
			size++;

		// Creates new 2D array of size x size filled by walls
		maze = new Character[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				maze[i][j] = '#';

		this.size = size;

		// Start always at index 1, 1
		maze[1][1] = 'S';
		start = new Location(1, 1);
	}

	// Generates maze by DFS
	public Character[][] generateDFS() {
		st.push(start);

		// Runs until stack is empty
		// Runs until all walls are 1 thickness
		while (!st.isEmpty()) {
			printMaze();
			Location next = findNext(st.peek().x, st.peek().y);
			if (next != null) {
				draw(st.peek(), next);
				st.push(next);
			} else
				st.pop();
		}

		// Goal is always at index size - 2, size - 2
		maze[size - 2][size - 2] = 'G';
		end = new Location(size - 2, size - 2);
		printMaze();
		return maze;
	}

	// Updates Console Graphics
	private void draw(Location a, Location next) {
		// One right to add original
		if (next.x - a.x < 0)
			maze[next.x + 1][next.y] = '.';
		// One left to add original
		else if (next.x - a.x > 0)
			maze[next.x - 1][next.y] = '.';
		// One down to add original
		else if (next.y - a.y < 0)
			maze[next.x][next.y + 1] = '.';
		// One up to add original
		else
			maze[next.x][next.y - 1] = '.';

		// Add next
		maze[next.x][next.y] = '.';
	}

	// Find next location to break walls
	private Location findNext(int x, int y) {
		int temp = rd.nextInt(4);

		// Everything here move here by 2 instead of 1 because there has to be space leftover for the walls
		if (temp == 0) {
			if (isValid(x + 2, y))
				return new Location(x + 2, y);
		} else if (temp == 1) {
			if (isValid(x - 2, y))
				return new Location(x - 2, y);
		} else if (temp == 2) {
			if (isValid(x, y + 2))
				return new Location(x, y + 2);
		} else if (temp == 3)
			if (isValid(x, y - 2))
				return new Location(x, y - 2);


		for (int i = -2; i <= 2; i += 2)
			for (int j = -2; j <= 2; j += 2)
				if (Math.abs(i) != Math.abs(j) && isValid(x + i, y + j))
					return new Location(x + i, y + j);

		return null;
	}

	// Check is location is valid
	private boolean isValid(int x, int y) {
		if (x <= 0 || y <= 0 || x >= size - 1 || y >= size - 1 || maze[x][y] != '#')
			return false;
		return true;
	}

	// Prints maze
	private void printMaze() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(maze[j][i] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}

}
