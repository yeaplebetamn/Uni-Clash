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

    //GameView Buttons
    public void pressWaveNumButton(View view){  //brings up popup with wave information
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Study this for the incoming wave");
        helpBuilder.setMessage("incoming wave information");        //this will be updated to reflect incoming wave's enemies
        helpBuilder.setPositiveButton("Study sesh completed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //closes popup
                    }
                });
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }
    public void pressShopButton(View view){
         //brings up popup with wave information
            AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
            helpBuilder.setTitle("Shop Button");//this will be updated to reflect incoming wave's enemies
            helpBuilder.setPositiveButton("Back",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //only closes popup
                        }
                    });
            AlertDialog helpDialog = helpBuilder.create();
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
        else{healthTextView.setText("Withdraw");}
    }

    void baseHealed(int heal){
        if(health + heal > 100){
            health = 100;

        }
        else{health += heal;}

    }

}
