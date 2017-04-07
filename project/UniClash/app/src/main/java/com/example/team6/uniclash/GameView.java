package com.example.team6.uniclash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Handler;

public class GameView extends SurfaceView implements Runnable {
    private Context context;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    private ArrayList enemies = new ArrayList();

    private Base base;

    private boolean shopOpen;

    private boolean gameOver = false;

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.context = context;
        spawnBase(context, screenX, screenY);
        spawnDefaultEnemies(5, context, screenX, screenY);
        spawnFastEnemies(50, context, screenX, screenY);
        spawnTankEnemies(2, context, screenX, screenY);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
    }

    public void setGameOver(){
        gameOver = true;
    }

    public void spawnDefaultEnemies(int numberEnemies, Context context, int screenX, int screenY){
        for(int i = 0; i < numberEnemies; i++){
            DefaultEnemy enemy = new DefaultEnemy(context, screenX, screenY, base);
            enemies.add(enemy);
        }
    }

    public void spawnTankEnemies(int numberEnemies, Context context, int screenX, int screenY){
        for(int i = 0; i < numberEnemies; i++){
            TankEnemy enemy = new TankEnemy(context, screenX, screenY, base);
            enemies.add(enemy);
        }
    }

    public void spawnFastEnemies(int numberEnemies, Context context, int screenX, int screenY){
        for(int i = 0; i < numberEnemies; i++){
            FastEnemy enemy = new FastEnemy(context, screenX, screenY, base);
            enemies.add(enemy);
        }
    }

    public void spawnBase(Context context, int screenX, int screenY){
        this.base = new Base(context, screenX, screenY, this);
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
        if(!gameOver){
            for(int i=0; i < enemies.size(); i++) {
               Enemy enemy = (Enemy) enemies.get(i);
                enemy.update();
            }
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
            canvas.drawBitmap(
                    base.getBitmap(),
                    base.getX(),
                    base.getY(),
                    paint);

            //shop button
            canvas.drawRect(
                    20,
                    20,
                    250,
                    100,
                    paint);

            //shop box
            if (shopOpen) {
                canvas.drawRect(
                        300,
                        300,
                        700,
                        700,
                        paint);
            }

            //Drawing the enemies
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = (Enemy) enemies.get(i);
                if (enemy.dead == false) {
                    canvas.drawBitmap(
                            enemy.getBitmap(),
                            enemy.getX(),
                            enemy.getY(),
                            paint
                    );
                }
            }

            if (gameOver){
                paint.setTextSize(150);
                canvas.drawText("GAME OVER", 850, 700, paint);

                // main menu button
                canvas.drawRect(
                        700,
                        900,
                        1200,
                        1100,
                        paint);
                paint.setColor(Color.YELLOW);
                canvas.drawRect(
                        710,
                        910,
                        1190,
                        1090,
                        paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(75);
                canvas.drawText("Main Menu", 765, 1025, paint);


                // restart button
                canvas.drawRect(
                        1400,
                        900,
                        1900,
                        1100,
                        paint);
                paint.setColor(Color.GREEN);
                canvas.drawRect(
                        1410,
                        910,
                        1890,
                        1090,
                        paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(75);
                canvas.drawText("Restart", 1520, 1025, paint);
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
        if (event.getX() > 19 && event.getX() < 251 && event.getY() > 19 && event.getY() < 101) {
            if (shopOpen == true) {
                shopOpen = false;
            } else {
                shopOpen = true;
            }
        }
        if (gameOver && event.getX() > 1400 && event.getX() < 1900 && event.getY() > 900 && event.getY() < 1100) {

            Intent in = new Intent(((GameActivity)getContext()),MainMenu.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ((GameActivity)getContext()).startActivity(in);
        }



        return false;
    }
}