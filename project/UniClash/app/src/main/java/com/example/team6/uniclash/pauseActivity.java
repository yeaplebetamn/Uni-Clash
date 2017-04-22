package com.example.team6.uniclash;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
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
      // sound2.setChecked(true);
        sound2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compound, boolean isChecked) {
                if (sound2.isChecked()) {
                    sound2.setChecked(true);
                    startService(new Intent(pauseActivity.this, Music.class));

                } else
                    stopService(new Intent(pauseActivity.this, Music.class));
            }

        });
    }

    public void pressRestart(View view) {
        Intent mainMenuIntent = new Intent(this, GameActivity.class);
        startActivity(mainMenuIntent);
    }
    public void pressMain(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("WARNING! You won't be able to resume your game. Please confirm.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent mainMenuIntent = new Intent(pauseActivity.this, MainMenu.class);
                        startActivity(mainMenuIntent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        sound2.setChecked(sound2.isChecked());
    }
    public void pressResume(View view) {
        onBackPressed();
    }
}

