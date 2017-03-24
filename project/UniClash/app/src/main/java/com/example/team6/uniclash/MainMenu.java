package com.example.team6.uniclash;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    Button playButton;
    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        playButton = (Button) findViewById(R.id.PlayButton);
        settingsButton = (Button) findViewById(R.id.SettingsButton);
    }

    //Methods for switching between menus using buttons
    //Methods based on: https://developer.android.com/training/basics/firstapp/starting-activity.html
    public void pressPlay(View view){
        Intent levelSelectMenuIntent = new Intent(this, LevelSelectMenu.class);
        startActivity(levelSelectMenuIntent);
    }
    public void pressSettings(View view){
        Intent settingsMenuIntent = new Intent(this, SettingsMenu.class);
        startActivity(settingsMenuIntent);
    }
   
}
