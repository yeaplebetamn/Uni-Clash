package com.example.team6.uniclash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {
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
    private ArrayList towers = new ArrayList();

    private Base base;

    private boolean shopOpen;
    int selectedShopQuadrant; //quad 1 = upper left, 2 = upper right, 3 = lower left, 4 = lower right
    private boolean towerSpawned; //true if user has just selected tower

    private boolean gameOver = false;

    private int waveNumber;
    private boolean waveStarted = false;

    private int maxX;
    private int maxY;


    private Rect shopButton;
    private Rect startWaveButton;
    private Rect pauseButton;
    private Rect waveInfoButton;

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.context = context;
        maxX = screenX;
        maxY = screenY;

        spawnBase(context, screenX, screenY);
        spawnDefaultEnemies(5, context, screenX, screenY);
        spawnFastEnemies(50, context, screenX, screenY);
        spawnTankEnemies(6, context, screenX, screenY);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        paint1 = new Paint();

        shopButton = new Rect(20, maxY - 200, 250, maxY - 100);
        startWaveButton = new Rect(maxX / 2 - 150, maxY - 200, maxX / 2 + 150, maxY - 100);
        pauseButton = new Rect(maxX - 250, 20, maxX - 20, 120);
        waveInfoButton = new Rect(maxX / 2 - 150, 20, maxX / 2 + 150, 120);
    }

    public void setGameOver() {
        gameOver = true;

    }

    public void spawnTankEnemies(int numberEnemies, Context context, int screenX, int screenY) {
        for (int i = 0; i < numberEnemies; i++) {
            TankEnemy enemy = new TankEnemy(context, screenX, screenY, base);
            enemy.setX(0 - (enemies.size() * enemy.getBitmap().getWidth()));
            enemies.add(enemy);
        }
    }

    public void spawnDefaultEnemies(int numberEnemies, Context context, int screenX, int screenY) {
        for (int i = 0; i < numberEnemies; i++) {
            DefaultEnemy enemy = new DefaultEnemy(context, screenX, screenY, base);
            enemy.setX(0 - (enemies.size() * enemy.getBitmap().getWidth()));
            enemies.add(enemy);

        }
    }

    public void spawnFastEnemies(int numberEnemies, Context context, int screenX, int screenY) {
        for (int i = 0; i < numberEnemies; i++) {
            FastEnemy enemy = new FastEnemy(context, screenX, screenY, base);
            enemy.setX(0 - (enemies.size() * enemy.getBitmap().getWidth()));
            enemies.add(enemy);
        }
    }

    public void spawnBase(Context context, int screenX, int screenY) {
        this.base = new Base(context, screenX, screenY, this);
    }

    public String getBaseHealthText() {
        int baseHealth = base.getHealth();

        if (baseHealth >= 96) {
            return ("A+ Health");
        } else if (baseHealth >= 90) {
            return ("A- Health");
        } else if (baseHealth >= 86) {
            return ("B+ Health");
        } else if (baseHealth >= 80) {
            return ("B- Health");
        } else if (baseHealth >= 76) {
            return ("C+ Health");
        } else if (baseHealth >= 70) {
            return ("C- Health");
        } else if (baseHealth >= 66) {
            return ("D+ Health");
        } else if (baseHealth >= 60) {
            return ("D- Health");
        } else if (baseHealth > 0) {
            return ("F Health");
        } else {
            return ("Withdraw");
        }

    }

    public String getBaseHealth() {
        String baseHealth;
        baseHealth = "" + base.getHealth();
        return baseHealth;
    }

    @Override
    public void run() {
        while (playing) {
            //to update the frame
            update();

            //to draw the frame
            draw();

            //to control
            control();
        }
    }


    private void update() {
        if (!gameOver && waveStarted) {
            for (int i = 0; i < enemies.size(); i++) {
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
            //need to use a variable for the y instead of 800
            canvas.drawBitmap(base.getBitmap(), maxX - base.getBitmap().getWidth(), 800, paint);

            //shop button
            paint1.setColor(Color.RED);
            paint1.setTextSize(50);


            //canvas.drawRect(20, 1000, 250, 900, paint);
            canvas.drawRect(shopButton, paint);
            canvas.drawText("Shop", shopButton.left + 20, shopButton.centerY() + 20, paint1);

            //wave info button
            canvas.drawRect(waveInfoButton, paint);
            canvas.drawText("Wave Info", waveInfoButton.left + 20, waveInfoButton.centerY() + 20, paint1);

            //start wave button
            canvas.drawRect(startWaveButton, paint);
            canvas.drawText("Start Wave", startWaveButton.left + 20, startWaveButton.centerY() + 20, paint1);

            //pause button
            canvas.drawRect(pauseButton, paint);
            canvas.drawText("Pause", pauseButton.left + 20, pauseButton.centerY() + 20, paint1);

            //drawing shop box
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
                    canvas.drawBitmap(enemy.getBitmap(), enemy.getX(), enemy.getY(), paint
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
            paint.setTextSize(50);
            canvas.drawText(getBaseHealth(), maxX - 500, maxY - 200, paint);
            paint.setTextSize(100);
            canvas.drawText(getBaseHealthText(), maxX - 500, maxY - 100, paint);


            if (gameOver) {
                paint.setTextSize(150);
                canvas.drawText("GAME OVER", maxX / 2 - 420, maxY / 2, paint);

                // main menu button
                canvas.drawRect(
                        maxX / 2 - 580,
                        maxY / 2 + 180,
                        maxX / 2 - 80,
                        maxY / 2 + 380,
                        paint);
                paint.setColor(Color.YELLOW);
                canvas.drawRect(
                        maxX / 2 - 570,
                        maxY / 2 + 190,
                        maxX / 2 - 90,
                        maxY / 2 + 370,
                        paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(75);
                canvas.drawText("Main Menu", maxX / 2 - 580 + 65, maxY / 2 + 300, paint);


                // restart button
                canvas.drawRect(
                        maxX / 2 + 120,
                        maxY / 2 + 180,
                        maxX / 2 + 620,
                        maxY / 2 + 380,
                        paint);
                paint.setColor(Color.GREEN);
                canvas.drawRect(
                        maxX / 2 + 130,
                        maxY / 2 + 190,
                        maxX / 2 + 610,
                        maxY / 2 + 370,
                        paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(75);
                canvas.drawText("Restart", maxX / 2 + 245, maxY / 2 + 300, paint);
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

        if (shopButton.contains((int) event.getX(), (int) event.getY())) {   //if shop button selected
            if (shopOpen == true) {
                shopOpen = false;
            } else {
                shopOpen = true;
            }
        }

        if (towerSpawned && !shopOpen) {  //if player just bought a tower and is placing it
            int x = Math.round(event.getX());
            int y = Math.round(event.getY());


            switch (selectedShopQuadrant) {
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
        if (shopOpen) {
            //different tower selections based on x,y coordinates
            if (event.getX() > 299 && event.getX() < 501 && event.getY() > 299 && event.getY() < 501) {   //upper left quadrant of shop
                selectedShopQuadrant = 1;

                shopOpen = false;
                towerSpawned = true;
            }
            if (event.getX() > 499 && event.getX() < 701 && event.getY() > 299 && event.getY() < 501) {   //upper right quadrant of shop
                selectedShopQuadrant = 2;

                shopOpen = false;
                towerSpawned = true;
            }
            if (event.getX() > 299 && event.getX() < 501 && event.getY() > 499 && event.getY() < 701) {   //lower left quadrant of shop
                selectedShopQuadrant = 3;

                shopOpen = false;
                towerSpawned = true;
            }
            if (event.getX() > 499 && event.getX() < 701 && event.getY() > 499 && event.getY() < 701) {   //lower right quadrant of shop
                selectedShopQuadrant = 4;

                shopOpen = false;
                towerSpawned = true;
            }
//            else{
//                shopOpen=false;
//                towerSpawned = false;
//            }

        }

        //old shop
//        if (shopButton.contains((int) event.getX(), (int) event.getY())) {
//            GameActivity.pressShopButton(this);
//        }

        //on clicking wave info button
        if (waveInfoButton.contains((int) event.getX(), (int) event.getY())) {
            GameActivity.pressWaveNumButton(this);
        }


        if (startWaveButton.contains((int) event.getX(), (int) event.getY())) {
            waveStarted = true;
        }

        //on clicking pause
        if (pauseButton.contains((int) event.getX(), (int) event.getY())) {
            if (playing) {
                pause();
            } else {
                resume();
            }
        }

        return false;
    }
}