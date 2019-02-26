package com.example.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button[][]  board;   // represents the tic tac toe board
    boolean     gameOver = false; // is true if the game is over

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        board = new Button[3][3];
        // put all buttons in the board
        for(int x = 0 ; x < board.length; x++){
            for(int y = 0; y < board[x].length ; y++){
                int id = getResources().getIdentifier("button" + x + "" + y, "id" , getPackageName());
                Button add = (Button) findViewById(id);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonLogic( v);
                    }
                });
                board[x][y] = add;
            }
        }

    }

    /**
     *
     * @param v
     */
    public void buttonLogic(View v) {
        if(v instanceof  Button){
            Button get = (Button) v;
            if(!gameOver) {
                // check for a legal move
                if (get.getText() == "") {
                    get.setText("X");
                    // if we win stop the game
                    // otherwise make move still start an infinite loop
                    if (checkVictory("X")) {
                        gameOver = true;
                        Toast.makeText(getApplicationContext(), "Congradulations! You win!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    // check for tie and reset the game if that happens
                    Log.d("Tic Tac Toe", "check for draw =  " + checkForDraw());
                    if(checkForDraw()){
                        gameOver = true;
                        Toast.makeText(getApplicationContext(), "Its a draw", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    nextMove();
                }
            }else{
                restartGame();
            }
        }
    }

    /**
     * sets all buttons to ""
     * also sets gameOver to false
     */
    public void restartGame(){
        gameOver = false;
        for (int x = 0; x < board.length; x++){
            for (int y = 0; y < board[x].length; y++){
                board[x][y].setText("");
            }
        }
    }
    /**
     * checks if there are 3 in a row on the board
     * @return
     */
    public boolean checkVictory(String player){
        // check columns and rows
        for(int i = 0; i < board.length; i++){
            if((board[i][1].getText() == board[i][0].getText() ) && ( board[i][0].getText() == board[i][2].getText())  && board[i][0].getText().equals(player)){
                return  true;
            }
            if((board[1][i].getText() == board[0][i].getText() ) && board[0][i].getText() == board[2][i].getText() && board[2][i].getText().equals(player)){
                return  true;
            }
        }
        // check diagonals
        if (board[1][1].getText().equals(board[0][0].getText() )   &&  board[1][1].getText().equals(board[2][2].getText() )  && board[0][0].getText().equals(player)){
            return true;
        }
        if (board[0][2].getText() == board[1][1].getText()  &&  board[1][1].getText() == board[2][0].getText() && board[1][1].getText().equals(player)){
            return  true;
        }
        return  false;
    }

    /**
     * the PC randomly picks the next move
     */
    private void nextMove(){
        boolean moveMade = false;
        // find a valid move
        do {
            int rndX = (int) (Math.random() * 3);
            int rndY = (int) (Math.random() * 3);

            if(!(board[rndX][rndY].getText() == "X") && !(board[rndX][rndY].getText() == "O")){
                board[rndX][rndY].setText("O");
                moveMade = true;
            }
        }while(!moveMade);
        // check for victory
        if(checkVictory("O")) {
            gameOver = true;
            Toast.makeText(getApplicationContext(), "Computer wins!", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private  boolean checkForDraw(){
        // try to find an empty space. return false if there is an empty space
        for(int x = 0; x < board.length ; x++){
            for (int y = 0 ; y < board[x].length ; y++){
                if(board[x][y].getText().equals("")){
                    Log.d("tic tac toe" , " empty space found at " + x  + " " + y);
                    return false;
                }
            }
        }

        return true;
    }
}
