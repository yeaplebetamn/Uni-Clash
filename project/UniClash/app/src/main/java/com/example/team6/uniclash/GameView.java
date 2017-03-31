package com.example.team6.uniclash;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
    
    private GameSurfaceView gameSurfaceView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        gameSurfaceView = new GameSurfaceView(this);
        
        setContentView(gameSurfaceView);
        
        waveNumButton = (Button) findViewById(R.id.waveButton);
        shopButton = (Button) findViewById(R.id.shopButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        startWaveButton = (Button) findViewById(R.id.startWaveButton);
        healthTextView = (TextView) findViewById(R.id.healthBar);


        health = 100;
    }

    //GameView Buttons
    public void pressWaveNumButton(View view){  //brings up popup with wave information
        AlertDialog.Builder waveInfoPopUp = new AlertDialog.Builder(this);
        waveInfoPopUp.setTitle("Study this for the incoming wave");
        waveInfoPopUp.setMessage("incoming wave information");        //this will be updated to reflect incoming wave's enemies
        waveInfoPopUp.setPositiveButton("Study sesh completed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //closes popup
                    }
                });
        AlertDialog helpDialog = waveInfoPopUp.create();
        helpDialog.show();
    }
    public void pressShopButton(View view){
            AlertDialog.Builder shopPopUp = new AlertDialog.Builder(this);
            shopPopUp.setTitle("Shop");   //Title of shop menu
            shopPopUp.setMessage(""); //shop menu dialogue
            shopPopUp.setPositiveButton("Back",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //closes popup
                        }
                    });
            AlertDialog helpDialog = shopPopUp.create();
            helpDialog.show();
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

        if(health >= 96){
            healthTextView.setText("A+ Health");
        }
        else if(health >= 90){
            healthTextView.setText("A- Health");
        }
        else if(health >= 86){
            healthTextView.setText("B+ Health");
        }
        else if(health >= 80){
            healthTextView.setText("B- Health");
        }
        else if(health >= 76){
            healthTextView.setText("C+ Health");
        }
        else if(health >= 70){
            healthTextView.setText("C- Health");
        }
        else if(health >= 66){
            healthTextView.setText("D+ Health");
        }
        else if(health >= 60){
            healthTextView.setText("D- Health");
        }
        else if(health > 0) {
            healthTextView.setText("F Health");
        }
        else{healthTextView.setText("Withdraw");
            // Add additional code here for GAME OVER.
        }
    }

    void baseHealed(int heal){
        if(health + heal > 100){
            health = 100; //prevents the user from going over 100% health.
            healthTextView.setText("A+ Health");
        }
        else if(health + heal >= 96){
            health += heal;
            healthTextView.setText("A+ Health");
        }
        else if(health + heal >= 90){
            health += heal;
            healthTextView.setText("A- Health");
        }
        else if(health + heal >= 86){
            health += heal;
            healthTextView.setText("B+ Health");
        }
        else if(health + heal >= 80){
            health += heal;
            healthTextView.setText("B- Health");
        }
        else if(health + heal >= 76){
            health += heal;
            healthTextView.setText("C+ Health");
        }
        else if(health + heal >= 70){
            health += heal;
            healthTextView.setText("C- Health");
        }
        else if(health + heal >= 66){
            health += heal;
            healthTextView.setText("D+ Health");
        }
        else if(health + heal >= 60){
            health += heal;
            healthTextView.setText("D- Health");
        }
        else if(health + heal > 0){
            health += heal;
            healthTextView.setText("F Health");
        }
        else{healthTextView.setText("Withdraw");}

    }

    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameSurfaceView.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameSurfaceView.resume();
    }

}
