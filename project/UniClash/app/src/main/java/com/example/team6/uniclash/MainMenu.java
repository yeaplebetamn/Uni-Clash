package com.example.team6.uniclash;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainMenu extends AppCompatActivity {

    boolean firstTimePlay = true;
    Button playButton;
    Button settingsButton;

   // private boolean mIsBound = false;
    private boolean mIsBound = false;
    private Music mServ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        Intent music = new Intent();
//        music.setClass(this,Music.class);
//        startService(music);
       // doBindService();

        try {
            String temp;
            int m;
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    openFileInput("first_run")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString);
            }
            temp = stringBuffer.toString();
            m = Integer.parseInt(temp);
            if (m > 0){
                firstTimePlay = true;
            }
            else{firstTimePlay = false;}
        } catch (IOException e) {
            e.printStackTrace();
        }

        //sets up the unlocked_level save file
        if(firstTimePlay) {
            String filename = "unlocked_levels";
            String n = "1";
            FileOutputStream outputStream;

            try {
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(n.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


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
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        stopService(new Intent(MainMenu.this,Music.class));
    }
//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        startService(new Intent(MainMenu.this,Music.class));
//    }
}
