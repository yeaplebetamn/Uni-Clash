package com.example.team6.uniclash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.io.FileNotFoundException;
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

        mainMenu = (Button) findViewById(R.id.mainMenuButton);

        //also radio buttons?
        ramsRB = (RadioButton) findViewById(R.id.ramsRB);
        hokiesRB = (RadioButton) findViewById(R.id.hokiesRB);
        spidersRB = (RadioButton) findViewById(R.id.spidersRB);
    }

    // creates or modifies a team.txt file to store the user's team name.
    public void setTeam(String teamName){
        try{
            PrintWriter writer = new PrintWriter("team.txt", "UTF-8");
            writer.println(teamName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
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
