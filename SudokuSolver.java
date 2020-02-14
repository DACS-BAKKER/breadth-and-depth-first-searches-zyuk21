/*
 File: SudokuSolver.java
 Name: Alex Yuk
 Date: 13/02/2020
 */

import java.util.Scanner;

public class SudokuSolver {
    // Object used to store each State of the board
    private static class State {
        private int[][] board;
        // To not have repeats
        private boolean[] usedNums;

        State(int[][] board) {
            this.board = board;
            this.usedNums = new boolean[board.length];
        }
    }

    // Object used to store x and y of a location
    public static class Location {
        private int x;
        private int y;

        Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private final static int BOARD_START_INDEX = 0;
    private final static int BOARD_SIZE = 9;
    private final static int EMPTY_VALUE = 0;
    private final static int MIN_VALUE = 1;
    private final static int MAX_VALUE = 9;
    private final static int SUBSECTION_SIZE = 3;

    private static Stack<State> stack = new Stack<>();
    private static Queue<State> queue = new Queue<>();


    // Used to count pushes and enqueues
    private static int counter = 0;

    public static void main(String[] args) {
        int[][] board = UI();


        System.out.println("Original Board: ");
        printBoard(board);
        System.out.println();

        System.out.println("Stack Solve: ");
        if (DFSSolve(new State(board))) {
            System.out.println("Stack Size: " + stack.size());
            System.out.println("Num of Push: " + counter);
            printBoard(stack.peek().board);
        } else
            System.out.println("Unsolvable");

        System.out.println();

//        for (State state : stack) {
//            printBoard(state.board);
//            System.out.println("");
//        }

        System.out.println("Queue Solve: ");
        if (BFSSolve(new State(board))) {
            System.out.println("Queue Size: " + queue.size());
            System.out.println("Num of Enqueue: " + counter);
            printBoard(queue.peek().board);
        } else
            System.out.println("Unsolvable");

        System.out.println();
    }

    // User Interface to select board
    private static int[][] UI() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Which board do you want to solve?");
        System.out.println("1. Easy");
        System.out.println("2. Hard");
        System.out.println("3. Three Empty");
        System.out.println("4. All Empty");
        System.out.print("? ");
        int input = sc.nextInt();

        switch (input) {
            case 1:
                int[][] easy = {
                        {0, 6, 8, 2, 3, 0, 0, 4, 0},
                        {0, 4, 0, 0, 5, 0, 6, 3, 0},
                        {3, 9, 5, 0, 4, 0, 0, 0, 8},
                        {5, 0, 0, 1, 0, 7, 0, 2, 3},
                        {1, 7, 0, 0, 0, 3, 0, 8, 0},
                        {9, 0, 3, 0, 0, 0, 0, 5, 1},
                        {8, 0, 9, 0, 0, 2, 3, 0, 0},
                        {0, 0, 2, 9, 7, 0, 8, 0, 0},
                        {0, 0, 0, 0, 0, 6, 5, 0, 0},
                };
                return easy;
            case 2:
                int[][] hard = {
                        {8, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 3, 6, 0, 0, 0, 0, 0},
                        {0, 7, 0, 0, 9, 0, 2, 0, 0},
                        {0, 5, 0, 0, 0, 7, 0, 0, 0},
                        {0, 0, 0, 0, 4, 5, 7, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0, 3, 0},
                        {0, 0, 1, 0, 0, 0, 0, 6, 8},
                        {0, 0, 8, 5, 0, 0, 0, 1, 0},
                        {0, 9, 0, 0, 0, 0, 4, 0, 0}
                };
                return hard;
            case 3:
                int[][] threeEmpty = {
                        {7, 6, 8, 2, 3, 9, 1, 4, 5},
                        {2, 4, 1, 7, 5, 8, 6, 3, 9},
                        {3, 9, 5, 6, 4, 1, 2, 7, 8},
                        {5, 8, 6, 1, 9, 7, 4, 2, 3},
                        {1, 7, 4, 5, 2, 3, 9, 8, 6},
                        {9, 2, 3, 8, 6, 4, 7, 5, 1},
                        {8, 5, 9, 4, 1, 2, 3, 6, 7},
                        {6, 3, 2, 9, 7, 5, 8, 0, 4},
                        {4, 1, 7, 3, 8, 6, 0, 0, 2},
                };
                return threeEmpty;

            default:
                int[][] allEmpty = {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                };
                return allEmpty;

        }
    }


    // Solves Sudoku with DFS
    private static boolean DFSSolve(State board) {
        counter = 1;
        // Adds initial board onto stack
        stack.push(board);

        while (true) {
            int[][] nextState = findNextMove();

            if (nextState == null) {
                stack.pop();
                // If stack is empty, Sudoku is unsolvable
                if (stack.isEmpty())
                    return false;
                continue;
            }

            counter++;
            stack.push(new State(nextState));

//            printBoard(stack.peek().board);
//            System.out.println("");

            // if isSolved is true, return true
            if (isSolved(stack.peek().board))
                return true;
        }
    }

    // Utility function for the DFS solve of Sudoku Solver
    // Returns a 2D array of the next board iteration
    private static int[][] findNextMove() {
        // Traverses through the entire board
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            for (int col = BOARD_START_INDEX; col < BOARD_SIZE; col++) {
                // If current block is empty
                if (stack.peek().board[row][col] == EMPTY_VALUE) {
                    // Tries to assignall num from MIN_VALUE to MAX_VALUE to that block
                    for (int num = MIN_VALUE; num <= MAX_VALUE; num++) {
//                        System.out.println(Arrays.toString(stack.peek().usedNums));
                        // If num has been used, skip
                        if (stack.peek().usedNums[num - 1])
                            continue;
                        // Set usage as true
                        stack.peek().usedNums[num - 1] = true;

                        // Pushes only if num is safe at row col
                        if (isSafe(stack.peek().board, row, col, num)) {
                            int[][] temp = stack.peek().board;
                            int[][] newBoard = new int[BOARD_SIZE][BOARD_SIZE];

                            // Copy previous board into new board
                            for (int i = 0; i < BOARD_SIZE; i++)
                                for (int j = 0; j < BOARD_SIZE; j++)
                                    newBoard[i][j] = temp[i][j];

                            newBoard[row][col] = num;
                            return newBoard;
                        }
                    }
                    return null;
                }
            }
        }
        return null;
    }

    // Solves Sudoku with BFS
    private static boolean BFSSolve(State board) {
        counter = 1;
        // Adds initial board state in to queue
        queue.enqueue(board);

        while (true) {
            // If Queue is empty, the Sudoku is unsolvable
            if (queue.isEmpty())
                return false;
            // Returns true if matches isSolved parameters
            if (isSolved(queue.peek().board))
                return true;

            State currentBoard = queue.dequeue();

            // Gets next empty location on the baord
            Location temp = nextEmpty(currentBoard);
            int row = temp.x;
            int col = temp.y;

            // Traverses through all the possible values for Sudoku, 1 - 9
            for (int num = MIN_VALUE; num <= MAX_VALUE; num++) {
                // Only enqueues if the combination isSafe
                if (isSafe(currentBoard.board, row, col, num)) {

                    int[][] newBoard = new int[BOARD_SIZE][BOARD_SIZE];
                    // Copy previous board into new board
                    for (int i = 0; i < BOARD_SIZE; i++)
                        for (int j = 0; j < BOARD_SIZE; j++)
                            newBoard[i][j] = currentBoard.board[i][j];

                    // Adds new number to new board
                    newBoard[row][col] = num;

//                    printBoard(newBoard);
//                    System.out.println("");

                    counter++;
                    queue.enqueue(new State(newBoard));
                }
            }

        }
    }

    // Utility function for the BFS solve of Sudoku Solver
    // Returns the next empty location on the board
    private static Location nextEmpty(State currentBoard) {
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++)
            for (int col = BOARD_START_INDEX; col < BOARD_SIZE; col++)
                if (currentBoard.board[row][col] == EMPTY_VALUE)
                    return new Location(row, col);
        return null;
    }

    // Returns true if board is full, meaning solved
    private static boolean isSolved(int[][] board) {
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++)
            for (int col = BOARD_START_INDEX; col < BOARD_SIZE; col++)
                if (board[row][col] == EMPTY_VALUE)
                    return false;
        return true;
    }

    // Return true if num has been used in row row
    private static boolean usedInRow(int[][] board, int row, int num) {
        for (int col = BOARD_START_INDEX; col < BOARD_SIZE; col++)
            if (board[row][col] == num)
                return true;
        return false;
    }

    // Return true if num has been used in column col
    private static boolean usedInCol(int[][] board, int col, int num) {
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++)
            if (board[row][col] == num)
                return true;
        return false;
    }

    // Returns true if num has been used in box
    private static boolean usedInBox(int[][] board, int boxStartRow, int boxStartCol, int num) {
        for (int row = BOARD_START_INDEX; row < SUBSECTION_SIZE; row++)
            for (int col = BOARD_START_INDEX; col < SUBSECTION_SIZE; col++)
                if (board[row + boxStartRow][col + boxStartCol] == num)
                    return true;
        return false;
    }

    // Return true if NOT usedInRow, usedInCol. usedInBox
    private static boolean isSafe(int[][] board, int row, int col, int num) {
        return !usedInRow(board, row, num) && !usedInCol(board, col, num) &&
                !usedInBox(board, row - row % 3, col - col % 3, num);
    }

    // Prints 2D array passed in
    private static void printBoard(int[][] board) {
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
                System.out.print(board[row][column] + " ");
            }
            System.out.println();
        }
    }
}
