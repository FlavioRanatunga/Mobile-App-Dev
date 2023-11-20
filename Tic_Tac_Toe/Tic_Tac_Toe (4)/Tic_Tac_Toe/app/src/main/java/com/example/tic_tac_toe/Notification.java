package com.example.tic_tac_toe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

public class Notification extends AppCompatActivity {

 public static void showWin(Context context, int grid){
     AlertDialog.Builder builder = new AlertDialog.Builder(context);
     builder.setTitle("You Win!");
        builder.setMessage("Congratulations! You won the game!");
        builder.setPositiveButton("Play again", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){

                if(grid ==0)
                {
                    ThreeByThreeFragment.resetGameWithDelay();
                }else if(grid ==4)
                {
                    FourByFourFragment.resetGameWithDelay();
                }else if(grid ==5)
                {
                    FiveByFiveFragment.resetGameWithDelay();
                }

            }
        });
     AlertDialog alertDialog = builder.create();
     alertDialog.show();
 };

    public static void showWin1(Context context, int grid){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("You Win!");
        builder.setMessage("Congratulations Player1! You won the game!");
        builder.setPositiveButton("Play again", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                if(grid ==0)
                {
                    ThreeByThreeFragment.resetGameWithDelay();
                }else if(grid ==4)
                {
                    FourByFourFragment.resetGameWithDelay();
                }else if(grid ==5)
                {
                    FiveByFiveFragment.resetGameWithDelay();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    };

    public static void showWin2(Context context, int grid){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("You Win!");
        builder.setMessage("Congratulations Player2! You won the game!");
        builder.setPositiveButton("Play again", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                if(grid ==0)
                {
                    ThreeByThreeFragment.resetGameWithDelay();
                }else if(grid ==4)
                {
                    FourByFourFragment.resetGameWithDelay();
                }else if(grid ==5)
                {
                    FiveByFiveFragment.resetGameWithDelay();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    };

    public static void showLoss(Context context, int grid){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("YOU LOSS :( ");
        builder.setMessage("You lost :/ Better luck next time!");
        builder.setPositiveButton("Play again", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                if(grid ==0)
                {
                    ThreeByThreeFragment.resetGameWithDelay();
                }else if(grid ==4)
                {
                    FourByFourFragment.resetGameWithDelay();
                }else if(grid ==5)
                {
                    FiveByFiveFragment.resetGameWithDelay();
                }

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    };

    public static void showDraw(Context context, int grid){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("DRAW ");
        builder.setMessage("It's a draw! Better luck next time!");
        builder.setPositiveButton("Play again", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){

                if(grid ==0)
                {
                    ThreeByThreeFragment.resetGameWithDelay();
                }else if(grid ==4)
                {
                    FourByFourFragment.resetGameWithDelay();
                }else if(grid ==5)
                {
                    FiveByFiveFragment.resetGameWithDelay();
                }

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    };

    public static void showTimeUp(Context context, int grid) {
        if (context != null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Time's up!");
        builder.setMessage("Too Slow! Better luck next time!");
        builder.setPositiveButton("Play again", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (grid == 0) {
                    ThreeByThreeFragment.resetGameWithDelay();
                } else if (grid == 4) {
                    FourByFourFragment.resetGameWithDelay();
                } else if (grid == 5) {
                    FiveByFiveFragment.resetGameWithDelay();
                }

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    };
 
 
    }



