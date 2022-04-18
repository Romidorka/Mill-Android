package com.example.mill;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class NineMensMorrisActivity extends AppCompatActivity {

    Board9 board;
    Rules rules;
    Game game;
    MyCanvas myCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        board = new Board9(getResources(), this);
        rules = new NineMensMorris(board);
        game = new Game(board, rules);
        myCanvas = new MyCanvas(this, board, rules, game);
        board.setRestartButtonCords();
        setContentView(myCanvas);
    }
}