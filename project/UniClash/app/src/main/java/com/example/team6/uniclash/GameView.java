package com.example.team6.uniclash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GameView extends AppCompatActivity {
    Button waveNumButton;
    Button shopButton;
    Button pauseButton;
    Button startWaveButton;

    TextView healthTextView;

    int health;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        waveNumButton = (Button) findViewById(R.id.waveButton);
        shopButton = (Button) findViewById(R.id.shopButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        startWaveButton = (Button) findViewById(R.id.startWaveButton);
        healthTextView = (TextView) findViewById(R.id.healthBar);


        health = 100;
    }

    //Methods for GameView buttons
    public void pressWaveNumButton(View view){

    }
    public void pressShopButton(View view){

    }
    public void pressPauseButton(View view){    //goes to SettingsMenu
        Intent settingsMenuIntent = new Intent(this, SettingsMenu.class);
        startActivity(settingsMenuIntent);
    }
    public void pressStartWaveButton(View view){

    }

    //methods for health bar display:

    void baseAttacked(int dmg){
        health -= dmg;

        
    }

}
