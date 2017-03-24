package com.example.team6.uniclash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

        mainMenuButton = (Button) findViewById(R.id.mainMenuButton);
        level1Button = (Button) findViewById(R.id.level1Button);
        level2Button = (Button) findViewById(R.id.level2Button);
        level3Button = (Button) findViewById(R.id.level3Button);

        // Displays the team name
        teamNameView = (TextView) findViewById(R.id.teamNameTextView);
        String teamName = "";

        /*String yourFilePath = this.getFilesDir() + "/" + "team.txt";

        File yourFile = new File( yourFilePath );

        File teamFile = new File(yourFilePath);
        FileReader myFileReader = null;
        BufferedReader myBufferedReader = null;

        try {
            myFileReader = new FileReader("team.txt");
            myBufferedReader = new BufferedReader(myFileReader);

            teamName = myBufferedReader.readLine();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        teamNameView.setText("My Team: " + teamName);
        */


        //this modified from http://chrisrisner.com/31-Days-of-Android-Day-23-Writing-and-Reading-Files
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    openFileInput("team")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString + "\n");
            }
            teamNameView.setText(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void pressMainMenu(View view){
        Intent mainMenuIntent = new Intent(this, MainMenu.class);
        startActivity(mainMenuIntent);
    }

    public void pressLevel1Button(View view){
        Intent level1SelectIntent = new Intent(this, GameView.class);
        startActivity(level1SelectIntent);
    }

    public void pressLevel2Button(View view){
        Intent level2SelectIntent = new Intent(this, GameView.class);
        startActivity(level2SelectIntent);
    }

    public void pressLevel3Button(View view){
        Intent level3SelectIntent = new Intent(this, GameView.class);
        startActivity(level3SelectIntent);
    }
}
