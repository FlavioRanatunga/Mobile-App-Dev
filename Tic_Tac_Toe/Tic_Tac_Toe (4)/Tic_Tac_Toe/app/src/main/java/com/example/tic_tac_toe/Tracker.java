package com.example.tic_tac_toe;

import android.os.CountDownTimer;

public class Tracker{
    private int totalMoves; //Keeps track of total moves
    private int movesLeftCount; //Tracks the number of moves left
    private boolean gameWon;    //Check winning condition
    private boolean gameLost;   //Check losing condition
    private boolean gameDraw;   //Check draw condition
    private boolean undoFlag;    //Checks if undo is available
    private int[][] prevBoard;  //Store the previous boar
    private CountDownTimer pTimer;  //Tracks time left for player's turn
    private long timerDuration; //CountDown timer duration
    private long timerLeftInMS; //CountDown timer duration left in milliseconds
    private OnTimerTickListener timerTickListener;





    //Constructor
    public Tracker()
    {
        this.totalMoves = 0;
        this.movesLeftCount = 0;
        this.gameWon = false;
        this.gameLost = false;
        this.gameDraw = false;
        this.undoFlag = false;
        this.prevBoard = null;
        this.pTimer = null;
        this.timerDuration = 0;
        this.timerLeftInMS = 0;
        this.timerTickListener = null;


    }

    public Tracker(int movesLeftCount, boolean gameWon, boolean gameLost, boolean gameDraw, boolean undoFlag, int[][] prevBoard,
                   long timerDuration){
        this.totalMoves = 0;
        this.movesLeftCount = movesLeftCount;
        this.gameWon = gameWon;
        this.gameLost = gameLost;
        this.gameDraw = gameDraw;
        this.undoFlag = undoFlag;
        this.prevBoard = prevBoard;
        this.pTimer = null;
        this.timerDuration = timerDuration;
        this.timerLeftInMS = 0;
        this.timerTickListener = null;
    }


    public int getTotalMoves(){
        return totalMoves;
    }

    public int getMovesLeftCount() {
        return movesLeftCount;
    }

    public boolean getGameWon(){
        return gameWon;
    }

    public boolean getGameLost(){
        return gameLost;
    }

    public boolean getGameDraw(){
        return gameDraw;
    }

    public boolean getUndoFlag(){
        return undoFlag;
    }

    public int[][] getPrevBoard() {
        return prevBoard;
    }

    public void setMovesLeftCount(int movesLeftCount){
        this.movesLeftCount = movesLeftCount;
    }

    public void setGameWon(boolean gameWon){
        this.gameWon = gameWon;
    }

    public void setGameLost(boolean gameLost){
        this.gameLost = gameLost;
    }

    public void setGameDraw(boolean gameDraw){
        this.gameDraw = gameDraw;
    }

    public void setUndoFlag(boolean undoFlag){
        this.undoFlag = undoFlag;
    }

    public void setPrevBoard(int[][] newPrevBoard) {
        prevBoard = newPrevBoard;
    }

    public void move()
    {
        totalMoves++;
        movesLeftCount--;
        undoFlag = true;

    }

    public void undoMove(){
        totalMoves--;
        movesLeftCount++;
        undoFlag = false;
    }

    public void reset(int movesLeftCount) {
        totalMoves = 0;
        this.movesLeftCount = movesLeftCount;
        gameWon = false;
        gameLost = false;
        gameDraw = false;
        undoFlag = false;
        prevBoard = null;
    }

    public void startPlayerTurnTimer(long durationMillis, final OnTimerTickListener listener) {
        if (pTimer != null) {
            pTimer.cancel();
        }
        timerDuration = durationMillis;
        timerTickListener = listener;

        pTimer = new CountDownTimer(timerDuration, 1000) {
            public void onTick(long millisUntilFinished) {
                timerLeftInMS = millisUntilFinished;
                if (timerTickListener != null) {
                    timerTickListener.onTick(timerLeftInMS);
                }
            }

            public void onFinish() {
                timerLeftInMS = 0;
                if (timerTickListener != null) {
                    timerTickListener.onFinish();
                }
            }
        }.start();
    }

    public void finishPlayerTurnTimer() {
        if (pTimer != null) {
            pTimer.cancel();
            pTimer = null;
            timerLeftInMS = 0;
            if (timerTickListener != null) {
                timerTickListener.onFinish();
            }
        }
    }

    public void cancelPlayerTimer() {
        if (pTimer != null) {
            pTimer.cancel();
            pTimer = null;
        }
    }

    public void pausePlayerTurnTimer() {
        if (pTimer != null) {
            pTimer.cancel();
        }
    }

    public void resumePlayerTurnTimer() {
        if (pTimer != null && timerLeftInMS > 0) {
            startPlayerTurnTimer(timerLeftInMS, timerTickListener);
        }
    }

    public interface OnTimerTickListener {
        void onTick(long millisUntilFinished);

        void onFinish();
    }
}









