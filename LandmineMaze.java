/*
 File: LandmineMaze.java
 Name: Alex Yuk
 Date: 11/02/2020
 Source: https://www.techiedelight.com/find-shortest-distance-every-cell-landmine-maze/
 */

import java.util.Arrays;

public class LandmineMaze {

    // Location class for LandmineMaze
    private static class Location {
        int x;
        int y;
        int distance;

        Location(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }

    private static int ROW;
    private static int COL;

    // Shortest distance from every location to mines
    public static void main(String[] args) {
        System.out.println("This program will mark the shortest distances from locations to mines");
        System.out.println("-1 marks all of the Xs");

        char[][] grid = {
                {'O', 'M', 'O', 'O', 'X'},
                {'O', 'X', 'X', 'O', 'M'},
                {'O', 'O', 'O', 'O', 'O'},
                {'O', 'X', 'X', 'X', 'O'},
                {'O', 'O', 'M', 'O', 'O'},
                {'O', 'X', 'X', 'M', 'O'}
        };

        ROW = grid.length;
        COL = grid[0].length;

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                System.out.print(grid[i][j] + "\t");
            }
            System.out.println("");
        }

        // Used to store the distances
        int[][] distances = new int[ROW][COL];

        calculateDistance(grid, distances);

        System.out.println("Results: ");
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                System.out.print(distances[i][j] + "\t");
            }
            System.out.println("");
        }
    }

    // Replace all O's in the matrix with their shortest distance
    // from the nearest mine
    private static void calculateDistance(char[][] grid, int[][] distances) {
        Queue<Location> queue = new Queue<>();

        // Add all mine locations to queue
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (grid[i][j] == 'M') {
                    queue.enqueue(new Location(i, j, 0));
                    // Mine's distance from itself is 1, stated in the problem
                    distances[i][j] = 0;
                }
                // If it is not mine, set to -1 for identification later
                else
                    distances[i][j] = -1;
            }
        }

        // Search for each item in queue
        while (!queue.isEmpty()) {
            // Temps to keep track of current mine
            int x = queue.peek().x;
            int y = queue.peek().y;
            int distance = queue.peek().distance;

            queue.dequeue();

            // Checks 4 locations around the current one
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (Math.abs(i + j) == 1) {
                        // Enqueue when meeting isSafe requirements
                        if (isSafe(grid, x + i, y + j, distances)) {
                            distances[x + i][y + j] = distance + 1;
                            queue.enqueue(new Location(x + i, y + j, distance + 1));
                        }
                    }
                }
            }
        }
    }

    // Return true if row and col in range
    // if cells is open
    // if have not been calculated
    // We leave blocked cells as -1 as stated in the question
    private static boolean isSafe(char[][] grid, int row, int col, int[][] distances) {
        return row >= 0 && row < ROW && col >= 0 && col < COL && grid[row][col] == 'O' && distances[row][col] == -1;
    }


}