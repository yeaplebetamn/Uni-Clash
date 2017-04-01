package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    private Enemy[] enemies;
    private Base base;


    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        spawnBase(context, screenX, screenY);
        spawnEnemies(context, screenX, screenY);
        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
    }

    public void spawnEnemies(Context context, int screenX, int screenY){
        enemies = new Enemy[3];

        enemies[0] = new DefaultEnemy(context, screenX, screenY);
        enemies[1] = new TankEnemy(context, screenX, screenY);
        enemies[2] = new FastEnemy(context, screenX, screenY);
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
            enemies[i].update();
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


            //Drawing the player
            for (int i = 0; i < 3; i++) {
                if (enemies[i].dead == false) {
                    canvas.drawBitmap(
                            enemies[i].getBitmap(),
                            enemies[i].getX(),
                            enemies[i].getY(),
                            paint
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
}