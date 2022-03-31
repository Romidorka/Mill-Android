package com.example.mill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent play_intent = new Intent(this, NineMensMorrisActivity.class);
        Intent about_intent = new Intent(this, AboutActivity.class);

        Button play_btn = (Button) findViewById(R.id.play_btn);
        Button about_btn = (Button) findViewById(R.id.about_btn);
        Button exit_btn = (Button) findViewById(R.id.exit_btn);

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(play_intent);
            }
        });

        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(about_intent);
            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}