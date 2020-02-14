## Assignment 9 README

## Files

**Project Files:** `Stack.java`, `Queue.java`, `SudokuSolver.java`

**Extension:** `MazeGenerator.java`, `Islands.java`, `LandmineMaze.java` 

I finished solving Sudoku with DFS and BFS. Then I impoved upon the maze problem form last year by creating a DFS maze generator. Lastly, I searched up two DFS and BFS interview questions on line and found the Island problem and the Landmine Maze problem.

## Sudoku Solver Problem

There are two private utility classes I used to solve this problem.

```java
private static class State {
		// Represent the Sudoku board
  	private int[][] board;
  	// Keeps track of tested numbers of this iteration of the board state
  	private boolean[] usedNums;
  	// Constructor
		State(int[][] board) {...}
}
```

State is the object used when adding to Stacks and Queues. It also stores a `boolean[]` because each board state remembers the numbers they have already tried so no repeats will be made.

```java
private static class Location {
		// x and y coordinates of location
    private int x;
    private int y;
		// Constructor
    Location(int x, int y) {...}
}
```

This is straight forward. Location is just an object that stores a x and a y coordinate so both `int` can be returned from one method.

#### Method

The solving method for DFS and BFS is similar. They share the same two conditions for stopping–`isEmpty()` and `isSolved()`. If at any time during the two while loops the Stack or the Queue `isEmpty()` it means that the Sudoku is unsolvable so the methods will `return false`. On the other hand, if they are full `isSolved()` will `return true` and the program will print out the solved board.

In the DFS, the program searches for the first empty space and vaild number to creates a new `State` to  `push()` onto the stack. This repeats until the program reaches one of its end conditions or if the program can no longer find anymore valid moves. When that happens, the program `pop()` and traverses down another path without repeating previous states.

In the BFS, the program does the same for creating a new `State` except it adds all possible `State` to the queue, diverging in different paths. Then every iteration of the board continues to split until the program hits one of the ending conditions.



## Extensions

**Maze Generator**

Extending on the maze problem I solved last year, I created a DFS maze generator. If you run it, the graphics will be printed in the console.

**Islands**

A DFS problem I found online [here](https://practice.geeksforgeeks.org/problems/find-the-number-of-islands/1). The goal of the problem is to find the number of groups of connected ones on the 2D array.

```Java
int grid[][] = {
	{ 1, 1, 0, 0, 0 },
	{ 0, 1, 0, 0, 1 },
	{ 1, 0, 0, 1, 1 },
	{ 0, 0, 0, 0, 0 },
	{ 1, 0, 1, 0, 1 }
};
```

For example, in this grid here, there are five islands (diagonal counts as connections).

**Landmine Maze**

A BFS problem I found online [here](https://www.techiedelight.com/find-shortest-distance-every-cell-landmine-maze/). This is slightly similar to the maze problem expect it the goal is to find the distance between each open location `'O'` and the landmine `'M'`. `'X'` represents closed pathes or walls.

```java
char[][] grid = {
	{'O', 'M', 'O', 'O', 'X'},
	{'O', 'X', 'X', 'O', 'M'},
  	{'O', 'O', 'O', 'O', 'O'},
	{'O', 'X', 'X', 'X', 'O'},
	{'O', 'O', 'M', 'O', 'O'},
	{'O', 'X', 'X', 'M', 'O'}
};
```
