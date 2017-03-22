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

public class LevelSelectMenu extends AppCompatActivity {
    private Button mainMenuButton;
    private Button level1Button;
    private Button level2Button;
    private Button level3Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select_menu);

        mainMenuButton = (Button) findViewById(R.id.mainMenuButton);
        level1Button = (Button) findViewById(R.id.level1Button);
        level2Button = (Button) findViewById(R.id.level2Button);
        level3Button = (Button) findViewById(R.id.level3Button);
    }

    public void pressMainMenu(View view){
        Intent mainMenuIntent = new Intent(this, MainMenu.class);
        startActivity(mainMenuIntent);
    }

    public void pressLevel1Button(View view){

    }

    public void pressLevel2Button(View view){

    }

    public void pressLevel3Button(View view){

    }
}
