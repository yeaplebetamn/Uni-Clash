package com.example.team6.uniclash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent levelSelectMenuIntent = new Intent(this, LevelSelectMenu.class);
        startActivity(levelSelectMenuIntent);
        return super.onOptionsItemSelected(item);
    }
}
