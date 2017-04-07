package com.example.team6.uniclash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity {
    Button waveNumButton;
    Button shopButton;
    Button pauseButton;
    Button startWaveButton;

    TextView healthTextView;

    int health;
    
    private GameView gameView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);




    //Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution into point object
        Point size = new Point();
        display.getSize(size);

        //Initializing game view object
        //this time we are also passing the screen size to the GameView constructor
            gameView = new GameView(this, size.x, size.y);

        //adding it to contentview
        setContentView(gameView);

        waveNumButton = (Button) findViewById(R.id.waveButton);
        shopButton = (Button) findViewById(R.id.shopButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        startWaveButton = (Button) findViewById(R.id.startWaveButton);
        healthTextView = (TextView) findViewById(R.id.healthBar);


        health = 100;
    }

    //GameActivity Buttons
    public static void pressWaveNumButton(View view){  //brings up popup with wave information
        AlertDialog.Builder waveInfoPopUp = new AlertDialog.Builder(view.getContext());
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
    public static void pressShopButton(View view){
            AlertDialog.Builder shopPopUp = new AlertDialog.Builder(view.getContext());
            shopPopUp.setTitle("Shop");   //Title of shop menu
      //  shopPopUp.setView(im);
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
        gameView.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}
