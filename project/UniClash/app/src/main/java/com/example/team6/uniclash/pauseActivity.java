package com.example.team6.uniclash;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class pauseActivity extends AppCompatActivity {
    Button restartButton;
    Button resumeButton;
    Switch sound2;
    Switch sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        restartButton = (Button) findViewById(R.id.restartButton);
        resumeButton = (Button) findViewById(R.id.resumeButton);
        sound2 = (Switch) findViewById(R.id.sound2);
        sound = (Switch) findViewById(R.id.sound);
       // sound2.setChecked();
        sound2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compound, boolean isChecked) {
                if (sound2.isChecked()) {
                    startService(new Intent(pauseActivity.this, Music.class));

                } else
                    stopService(new Intent(pauseActivity.this, Music.class));
            }

        });
    }

    public void pressRestart(View view) {
        Intent mainMenuIntent = new Intent(this, MainMenu.class);
        startActivity(mainMenuIntent);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    public void pressResume(View view) {
        onBackPressed();
    }
}

