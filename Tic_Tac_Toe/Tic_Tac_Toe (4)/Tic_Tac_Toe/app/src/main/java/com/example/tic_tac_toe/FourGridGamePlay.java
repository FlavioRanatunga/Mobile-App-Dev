package com.example.tic_tac_toe;

public class FourGridGamePlay {

    private static final int SIZE = 4; // Size of the game grid

    public static boolean checkThreeConsecutiveWin(int[][] board, int player) {
        // Check rows for a win
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE - 2; j++) {
                if (board[i][j] == player && board[i][j+1] == player && board[i][j+2] == player) {
                    return true; // Row win
                }
            }
        }
        // Check columns for a win
        for (int i = 0; i < SIZE - 2; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == player && board[i+1][j] == player && board[i+2][j] == player) {
                    return true; // Column win
                }
            }
        }
        // Check diagonals for a win
        for (int i = 0; i < SIZE - 2; i++) {
            for (int j = 0; j < SIZE - 2; j++) {
                if (board[i][j] == player && board[i+1][j+1] == player && board[i+2][j+2] == player) {
                    return true; // Diagonal win
                }
                if (board[i][j+2] == player && board[i+1][j+1] == player && board[i+2][j] == player) {
                    return true; // Diagonal win
                }
            }
        }
        return false; // No win
    }

    public static boolean checkFourConsecutiveWin(int[][] board, int player) {
        // Check rows for a win
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE - 3; j++) {
                if (board[i][j] == player && board[i][j+1] == player && board[i][j+2] == player && board[i][j+3] == player) {
                    return true; // Row win
                }
            }
        }
        // Check columns for a win
        for (int i = 0; i < SIZE - 3; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == player && board[i+1][j] == player && board[i+2][j] == player && board[i+3][j] == player) {
                    return true; // Column win
                }
            }
        }
        // Check diagonals for a win
        for (int i = 0; i < SIZE - 3; i++) {
            for (int j = 0; j < SIZE - 3; j++) {
                if (board[i][j] == player && board[i+1][j+1] == player && board[i+2][j+2] == player && board[i+3][j+3] == player) {
                    return true; // Diagonal win
                }
                if (board[i][j+3] == player && board[i+1][j+2] == player && board[i+2][j+1] == player && board[i+3][j] == player) {
                    return true; // Diagonal win
                }
            }
        }
        return false; // No win
    }

    public static boolean checkDraw(int[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 3) {
                    // If an empty cell is found, the game is not a draw yet
                    return false;
                }
            }
        }
        // If all cells are filled and no empty cells were found, the game is a draw
        return true;
    }
}


