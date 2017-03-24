package com.example.team6.uniclash;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class SettingsMenu extends AppCompatActivity {
    Button mainMenu;

    //change to radio buttons?
    RadioButton ramsRB;
    RadioButton hokiesRB;
    RadioButton spidersRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mainMenu = (Button) findViewById(R.id.mainMenuButton);

        //also radio buttons?
        ramsRB = (RadioButton) findViewById(R.id.ramsRB);
        hokiesRB = (RadioButton) findViewById(R.id.hokiesRB);
        spidersRB = (RadioButton) findViewById(R.id.spidersRB);

        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    openFileInput("team")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString);
            }
            if (stringBuffer.toString().equals("Rams")) {
                ramsRB.setChecked(true);
            }
            else if (stringBuffer.toString().equals("Hokies")) {
                hokiesRB.setChecked(true);
            }
            else if (stringBuffer.toString().equals("Spiders")) {
                spidersRB.setChecked(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // modifies a team.txt file to store the user's team name.
    // code modified from https://developer.android.com/training/basics/data-storage/files.html
    public void setTeam(String teamName){
        String filename = "team";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(teamName.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Methods for switching between menus
    //Methods based on: https://developer.android.com/training/basics/firstapp/starting-activity.html
    public void pressMainMenu(View view){
        if(spidersRB.isChecked()){
            setTeam("Spiders");
        }
        else if(ramsRB.isChecked()){
            setTeam("Rams");
        }
        else{setTeam("Hokies");}

        Intent mainMenuIntent = new Intent(this, MainMenu.class);
        startActivity(mainMenuIntent);
    }
}
