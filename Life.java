import javax.swing.*;

/**
 * The Life class simulates Conway's game of life.
 *
 * @author Franc
 * @version 2022-10-13
 */
public class Life {
    // TODO: check if there is a need to change the grid values
    public static final int ROWS = 20;
    public static final int COLS = 80;
    public static final int TIME_DELAY = 500; // ms

    public static void initializeBoard(Board board) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int randVal = (int) (Math.random() * 4); // 0, 1, 2 ose 3 (25% shanc)
                if (randVal == 0) {
                    board.set(r, c, 1);
                }
            }
        }
    }

    public static void displayBoard(Board board) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board.get(r, c) == 0) {
                    System.out.print(".");
                } else if (board.get(r, c) == 1) {
                    System.out.print("0");
                }
            }
            System.out.println();
        }
    }

    public static void calculateNextGeneration(Board b, Board nextB) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int neighboursCounted = countNeighbours(r, c, b);

                if (b.get(r, c) == 1 && neighboursCounted < 2) {
                    nextB.set(r, c, 0);
                } else if (b.get(r, c) == 1 && neighboursCounted < 4) {
                    nextB.set(r, c, 1);
                } else if (b.get(r, c) == 1 && neighboursCounted > 3) {
                    nextB.set(r, c, 0);
                } else if (b.get(r, c) == 0 && neighboursCounted == 3) {
                    nextB.set(r, c, 1);
                } else {
                    nextB.set(r, c, 0);
                }
            }
        }
    }

    public static int countNeighbours(int row, int col, Board b) {
        int count = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                // legal cell in the board
                if (r >= 0 && r < ROWS && c >= 0 && c < COLS && !(r == row && c == col) && b.get(r, c) == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void transferNextToCurrent( Board b, Board nextBoard) {
        for(int r = 0; r < ROWS; r++) {
            for(int c=0; c<COLS; c++) {
                b.set(r, c, nextBoard.get(r, c));

            }
        }
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void slow(int TIME_DELAY) {
        try {
            Thread.sleep(TIME_DELAY); // TIME_DELAY is an integer in milliseconds
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Board board = new Board(ROWS, COLS);
        Board nextBoard = new Board(ROWS, COLS);
        initializeBoard(board);
        for(int i=0; i < 100; i++) {
            clearConsole();
            displayBoard(board);
            slow(TIME_DELAY);
            calculateNextGeneration(board, nextBoard);
            transferNextToCurrent(board, nextBoard);
        }
    }
}
