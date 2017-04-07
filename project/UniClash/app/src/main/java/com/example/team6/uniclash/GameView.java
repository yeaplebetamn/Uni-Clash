package com.example.team6.uniclash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public  class GameView extends SurfaceView implements Runnable {

    private Paint paint;
    private Paint paint1;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Context context;

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    private ArrayList enemies = new ArrayList();

    //private Enemy[] enemies[];

    private Base base;

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.context=context;
        spawnBase(context, screenX, screenY);
        spawnDefaultEnemies(1, context, screenX, screenY);
        spawnFastEnemies(1, context, screenX, screenY);
        spawnTankEnemies(1, context, screenX, screenY);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        paint1=new Paint();
        paint1.setColor(Color.RED);

    }

    public void spawnDefaultEnemies(int numberEnemies, Context context, int screenX, int screenY){
        for(int i = 0; i < numberEnemies; i++){
            DefaultEnemy enemy = new DefaultEnemy(context, screenX, screenY);
            enemies.add(enemy);
        }
    }

    public void spawnTankEnemies(int numberEnemies, Context context, int screenX, int screenY){
        for(int i = 0; i < numberEnemies; i++){
            TankEnemy enemy = new TankEnemy(context, screenX, screenY);
            enemies.add(enemy);
        }
    }

    public void spawnFastEnemies(int numberEnemies, Context context, int screenX, int screenY){
        for(int i = 0; i < numberEnemies; i++){
            FastEnemy enemy = new FastEnemy(context, screenX, screenY);
            enemies.add(enemy);
        }
    }

    public void spawnBase(Context context, int screenX, int screenY){
        this.base = new Base(context, screenX, screenY);
    }

    @Override
    public void run() {
        while (true) {
            //to update the frame
            update();

            //to draw the frame
            draw();

            //to control
            control();
        }
    }


    private void update() {
        for(int i=0; i<3; i++){
            Enemy enemy = (Enemy) enemies.get(i);
            enemy.update();
        }
    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.WHITE);

            //Drawing the base
            canvas.drawBitmap(base.getBitmap(), base.getX(), base.getY(), paint);

            //shop button
            paint1.setTextSize(50);
            canvas.drawRect(20, 1000, 250, 900, paint);
            canvas.drawText("Shop",70,980,paint1);
            canvas.drawRect(1000,100,700,20,paint);
            canvas.drawText("Wave Info",720,85,paint1);
            canvas.drawRect(1000, 1000, 700, 920, paint);
            canvas.drawText("Start Wave",720,980,paint1);
            canvas.drawRect(1700, 100, 1400, 20, paint);
            canvas.drawText("Pause",1500,85,paint1);



            //Drawing the enemies
            for (int i = 0; i < 3; i++) {
                Enemy enemy = (Enemy) enemies.get(i);
                if (enemy.dead == false) {
                    canvas.drawBitmap(enemy.getBitmap(), enemy.getX(), enemy.getY(), paint
                    );
                }
            }
            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    //detects touches
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //on clicking shop button
        if (event.getX() > 19 && event.getX() < 251 && event.getY() > 901 && event.getY() < 999) {
            GameActivity.pressShopButton(this);
        }
        //on clicking wave info button
        if (event.getX() > 701 && event.getX() < 999 && event.getY() > 21 && event.getY() < 99) {
            GameActivity.pressWaveNumButton(this);
        }
        //on clicking start wave
        if (event.getX() > 701 && event.getX() < 999 && event.getY() > 921 && event.getY() < 1001) {


        }
        //on clicking pause
        if (event.getX() > 1401 && event.getX() < 1699 && event.getY() > 19 && event.getY() < 101) {

        }
        return false;
    }

}