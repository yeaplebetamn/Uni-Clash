package com.example.team6.uniclash;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity {
    Button waveNumButton;
    Button shopButton;
    Button pauseButton;
    Button startWaveButton;

    TextView healthTextView;

    int health;
    
    private GameView gameView;
    private boolean mIsBound = false;
    private Music mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((Music.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,Music.class),
                Scon,Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


//        Intent music = new Intent();
//        music.setClass(this,Music.class);
//        startService(music);

        //Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution into point object
        Point size = new Point();
        display.getSize(size);

        //Initializing game view object
        //this time we are also passing the screen size to the GameView constructor
        gameView = new GameView(this, size.x, size.y);

//        //hiding notifcation/status bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //adding it to contentview
        setContentView(gameView);

        waveNumButton = (Button) findViewById(R.id.waveButton);
        shopButton = (Button) findViewById(R.id.shopButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        startWaveButton = (Button) findViewById(R.id.startWaveButton);
        healthTextView = (TextView) findViewById(R.id.healthBar);


        health = 100;
    }

    //GameActivity Buttons
    public static void pressWaveNumButton(View view, int numRams, int numTurkeys, int numSpiders){  //brings up popup with wave information
        AlertDialog.Builder waveInfoPopUp = new AlertDialog.Builder(view.getContext());
        waveInfoPopUp.setTitle("Study this for the incoming wave");
        waveInfoPopUp.setMessage("Incoming wave information:" +
                "\nRams: " + numRams +
                "\nTurkeys: " + numTurkeys +
                "\nSpiders: " + numSpiders);        //this will be updated to reflect incoming wave's enemies
        waveInfoPopUp.setPositiveButton("Study sesh completed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //closes popup
                    }
                });
        AlertDialog helpDialog = waveInfoPopUp.create();
        helpDialog.show();
    }
    public static void pressShopButton(View view){
            AlertDialog.Builder shopPopUp = new AlertDialog.Builder(view.getContext());
          //  shopPopUp.setTitle("Shop");   //Title of shop menu
      //  shopPopUp.setView(im);
            shopPopUp.setMessage("Are you sure you want to place your tower here?"); //shop menu dialogue
            shopPopUp.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //closes popup
                        }
                    });
            AlertDialog helpDialog = shopPopUp.create();
            helpDialog.show();
        }

    public void pressPauseButton(View view){    //goes to SettingsMenu
        Intent settingsMenuIntent = new Intent(this, SettingsMenu.class);
        startActivity(settingsMenuIntent);
    }
    public void pressStartWaveButton(View view){

    }

    public void returnToMainMenu(){
        Intent levelSelectMenuIntent = new Intent(this, LevelSelectMenu.class);
        startActivity(levelSelectMenuIntent);
    }

    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();

    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }


    //causes crash :(
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent startMain = new Intent(GameActivity.this, MainMenu.class);
//                        startMain.addCategory(Intent.CATEGORY_HOME);
//                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(startMain);
                        finish();
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
}
