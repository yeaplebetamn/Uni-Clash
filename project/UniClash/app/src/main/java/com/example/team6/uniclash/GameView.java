package com.example.team6.uniclash;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class GameView extends AppCompatActivity {
    Button waveButton;
    Button shopButton;
    Button pauseButton;
    Button startWaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        waveButton = (Button) findViewById(R.id.waveButton);
        shopButton = (Button) findViewById(R.id.shopButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        startWaveButton = (Button) findViewById(R.id.startWaveButton);
    }

    //Methods for GameView buttons
    public void pressWaveButton(View view){

    }
    public void pressShopButton(View view){

    }
    public void pressPauseButton(View view){

    }
    public void pressStartWaveButton(View view){

    }


}
