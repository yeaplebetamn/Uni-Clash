package com.example.team6.uniclash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

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
    private ArrayList towers = new ArrayList();

    private Base base;

    private boolean shopOpen;
    int selectedShopQuadrant; //quad 1 = upper left, 2 = upper right, 3 = lower left, 4 = lower right
    private boolean towerSpawned; //true if user has just selected tower

    private boolean gameOver = false;

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.context = context;
        spawnBase(context, screenX, screenY);
        spawnDefaultEnemies(5, context, screenX, screenY);
        spawnFastEnemies(15, context, screenX, screenY);
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

    public String getBaseHealthText(){
        int baseHealth = base.getHealth();

        if(baseHealth >= 96){
            return("A+ Health");
        }
        else if(baseHealth >= 90){
            return("A- Health");
        }
        else if(baseHealth >= 86){
            return("B+ Health");
        }
        else if(baseHealth >= 80){
            return("B- Health");
        }
        else if(baseHealth >= 76){
            return("C+ Health");
        }
        else if(baseHealth >= 70){
            return("C- Health");
        }
        else if(baseHealth >= 66){
            return("D+ Health");
        }
        else if(baseHealth >= 60){
            return("D- Health");
        }
        else if(baseHealth > 0) {
            return("F Health");
        }
        else{return("Withdraw");}

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

            //Drawing towers
                for (int i = 0; i < towers.size(); i++) {
                    Tower tower = (Tower) towers.get(i);
                    canvas.drawBitmap(
                            tower.getBitmap(),
                            tower.getX(),
                            tower.getY(),
                            paint
                    );
                }

            //Drawing health text
            paint.setTextSize(100);
            canvas.drawText(getBaseHealthText(), 2000, 1200, paint);


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

        if (event.getX() > 19 && event.getX() < 251 && event.getY() > 19 && event.getY() < 101) {   //if home button selected
            if (shopOpen == true) {
                shopOpen = false;
            } else {
                shopOpen = true;
            }
        }

        if(towerSpawned && !shopOpen){  //if player just bought a tower and is placing it
            int x = Math.round(event.getX());
            int y = Math.round(event.getY());


            switch(selectedShopQuadrant){
                case 1: //shop quadrant 1
                    towers.add(new GunTower(context, x, y));//TODO: bank deductions for each tower bought
                    break;
                case 2: //shop quadrant 2
                    towers.add(new SniperTower(context, x, y));//TODO: bank deduct
                    break;
                case 3: //shop quadrant 3
                    towers.add(new FreezeTower(context, x, y));//TODO: bank deduct
                    break;
                case 4: //shop quadrant 4
                    towers.add(new RocketTower(context, x, y));//TODO: bank deduct
                    break;
            }

            towerSpawned = false; //player is done selecting tower location
        }

        //Shop Menu is up
        if(shopOpen){
            //different tower selections based on x,y coordinates
            if (event.getX() > 299 && event.getX()  < 501 && event.getY() > 299 && event.getY() < 501) {   //upper left quadrant of shop
                selectedShopQuadrant = 1;

                shopOpen=false;
                towerSpawned = true;
            }
            if (event.getX()  > 499 && event.getX()  < 701 && event.getY() > 299 && event.getY() < 501) {   //upper right quadrant of shop
                selectedShopQuadrant = 2;

                shopOpen=false;
                towerSpawned = true;
            }
            if (event.getX()  > 299 && event.getX()  < 501 && event.getY() > 499 && event.getY() < 701) {   //lower left quadrant of shop
                selectedShopQuadrant = 3;

                shopOpen=false;
                towerSpawned = true;
            }
            if (event.getX()  > 499 && event.getX()  < 701 && event.getY() > 499 && event.getY() < 701) {   //lower right quadrant of shop
                selectedShopQuadrant = 4;

                shopOpen=false;
                towerSpawned = true;
            }
//            else{
//                shopOpen=false;
//                towerSpawned = false;
//            }

        }



        if (gameOver && event.getX() > 1400 && event.getX() < 1900 && event.getY() > 900 && event.getY() < 1100) {

            Intent in = new Intent(((GameActivity)getContext()),MainMenu.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ((GameActivity)getContext()).startActivity(in);
        }

        return false;
    }
}