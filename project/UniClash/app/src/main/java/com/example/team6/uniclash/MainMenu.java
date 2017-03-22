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

        //stack overflow's method of activity switching
        switch(item.getItemId()){
            case R.id.PlayButton:
                startActivity(new Intent(this, LevelSelectMenu.class));
                return true;
            case R.id.SettingsButton:
                startActivity(new Intent(this, SettingsMenu.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

        //Duke's method of activity switching
//        Intent levelSelectMenuIntent = new Intent(this, LevelSelectMenu.class);
//        startActivity(levelSelectMenuIntent);
//        return super.onOptionsItemSelected(item);
    }
}
