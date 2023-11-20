package com.example.tic_tac_toe;

public class ThreeGridGamePlay {

    private static final int SIZE = 3; // Size of the game grid

    // Check for a win condition
    public static boolean checkWin(int[][] board, int player) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true; // Row win
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true; // Column win
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true; // Diagonal win
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true; // Diagonal win
        }
        return false; // No win
    }





    // Check for a draw condition
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