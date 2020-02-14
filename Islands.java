/*
 File: Islands.java
 Name: Alex Yuk
 Date: 11/02/2020
 Source: https://practice.geeksforgeeks.org/problems/find-the-number-of-islands/1
 */

import java.lang.*;

public class Islands {

    private static int ROW;
    private static int COL;

    public static void main(String[] args) {
        System.out.println("This program will count the number of islands in this 2D array");
        System.out.println("Connected 1s make one island, connections include diagonal");

        int grid[][] = {
                {1, 1, 0, 0, 0},
                {0, 1, 0, 0, 1},
                {1, 0, 0, 1, 1},
                {0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1}
        };

        ROW = grid.length;
        COL = grid[0].length;

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("");
        }

        System.out.println("Number of Islands: " + countIslands(grid));
    }

    // Returns the number of islands in a 2D array
    private static int countIslands(int grid[][]) {
        // Used to keep track of locations visited
        boolean tested[][] = new boolean[ROW][COL];

        // Initialize counter
        int counter = 0;
        // Traverses through all elements in the grid
        for (int i = 0; i < ROW; ++i) {
            for (int j = 0; j < COL; ++j) {
                // Start count if location is 1 and that it has not been tested before
                if (grid[i][j] == 1 && !tested[i][j]) {
                    search(grid, i, j, tested);
                    counter++;
                }
            }
        }
        return counter;
    }

    // Uses DFS to search for all other connected islands
    // Marked the tested islands as tested so they will not be visited again
    private static void search(int grid[][], int row, int col, boolean tested[][]) {
        // Mark this cell as tested
        tested[row][col] = true;

        // Tests the 8 locations around the current one to see if connected to any
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Doesn't have to count itself
                if (i == 0 && j == 0)
                    continue;
                // Continue testing all connections
                if (isSafe(grid, row + i, col + j, tested))
                    // Recursively calls itself until there are no more connections
                    // This is the DFS aspect of the algorithm
                    search(grid, row + i, col + j, tested);
            }
        }
    }

    // Returns true if location can be tested
    private static boolean isSafe(int grid[][], int row, int col, boolean visited[][]) {
        // If row and col are in range and if location has already been visited
        return (row >= 0) && (row < ROW) && (col >= 0) && (col < COL) && (grid[row][col] == 1 && !visited[row][col]);
    }
}