package com.example.team6.uniclash;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LevelSelectMenu extends AppCompatActivity {
    private Button mainMenuButton;
    private Button level1Button;
    private Button level2Button;
    private Button level3Button;

    public TextView teamNameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select_menu);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mainMenuButton = (Button) findViewById(R.id.mainMenuButton);
        level1Button = (Button) findViewById(R.id.level1Button);
        level2Button = (Button) findViewById(R.id.level2Button);
        level3Button = (Button) findViewById(R.id.level3Button);

        // Displays the team name
        teamNameView = (TextView) findViewById(R.id.teamNameTextView);
        String teamName = "";

        //this modified from http://chrisrisner.com/31-Days-of-Android-Day-23-Writing-and-Reading-Files
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    openFileInput("team")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString);
            }
            teamNameView.setText(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setLevel(String levelName){
        String filename = "level";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(levelName.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void pressMainMenu(View view){
        Intent mainMenuIntent = new Intent(this, MainMenu.class);
        startActivity(mainMenuIntent);

    }

    public void pressLevel1Button(View view){
        Intent level1SelectIntent = new Intent(this, GameActivity.class);
        startActivity(level1SelectIntent);
        setLevel("1");
    }

    public void pressLevel2Button(View view){
        Intent level2SelectIntent = new Intent(this, GameActivity.class);
        startActivity(level2SelectIntent);
        setLevel("2");
    }

    public void pressLevel3Button(View view){
        Intent level3SelectIntent = new Intent(this, GameActivity.class);
        startActivity(level3SelectIntent);
        setLevel("3");
    }
}
