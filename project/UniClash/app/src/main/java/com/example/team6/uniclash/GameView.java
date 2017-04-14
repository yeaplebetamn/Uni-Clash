package com.example.team6.uniclash;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {
    private Paint paint;
    private Paint paint1;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Context context;
    public int credit = 100;
    private Music mServ;
    Wave wave1 = new Wave(1, 2, 3);
    Wave wave2 = new Wave(4, 5, 6);
    Wave wave3 = new Wave(7, 8, 9);
    public boolean spawnWave1 = true;
    public boolean spawnWave2 = false;
    public boolean spawnWave3 = false;

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    private ArrayList enemies = new ArrayList();

    private Base base;

    private boolean gameOver = false;

    private int waveNumber = 1;
    private boolean waveStarted = false;

    private int maxX;
    private int maxY;

    //tower variables
    private ArrayList<Tower> towers = new ArrayList<>();
    //private Tower[][] towersGrid = new Tower[5][10];   //2d array of all towers, array based on map grid system
    int gridX; //10 by 5 grid based off of maxX and maxY
    int gridY;
    //private Integer[][] gridCoordinates = new Integer[5][10];


    //shop variables
    private boolean shopOpen;
    private boolean upgrading;
    int selectedShopQuadrant; //quad 1 = upper left, 2 = upper right, 3 = lower left, 4 = lower right
    private boolean towerSpawned = false; //true if user has just selected tower
    private boolean invalidTower;

    private Rect shopButton;
    private Rect startWaveButton;
    private Rect pauseButton;
    private Rect waveInfoButton;
    private Rect upgradeButton;

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.context = context;
        maxX = screenX;
        maxY = screenY;
        //setting up grid
        gridX = maxX / 10;  //grid tile width
        gridY = maxY / 5;  //grid tile height

        spawnBase(context, screenX, screenY);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        paint1 = new Paint();

        shopButton = new Rect(20, maxY - 200, 250, maxY - 100);
        upgradeButton = new Rect(maxX/4-150, maxY-200, maxX/4 +150,maxY-100);
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

    public void addCredits(int credits) {
        credit += credits;
    }

    public int getCredits() {
        return credit;
    }

    public String toStringCredit() {
        return getCredits() + "";
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
            if(spawnWave1){
                spawnTankEnemies(wave1.getNumTurkeys(), context, maxX, maxY);
                spawnDefaultEnemies(wave1.getNumRams(), context, maxX, maxY);
                spawnFastEnemies(wave1.getNumSpiders(), context, maxX, maxY);
                spawnWave1 = false;
            }

            else if(spawnWave2){
                spawnTankEnemies(wave2.getNumTurkeys(), context, maxX, maxY);
                spawnDefaultEnemies(wave2.getNumRams(), context, maxX, maxY);
                spawnFastEnemies(wave2.getNumSpiders(), context, maxX, maxY);
                spawnWave2 = false;
            }

            else if(spawnWave3){
                spawnTankEnemies(wave3.getNumTurkeys(), context, maxX, maxY);
                spawnDefaultEnemies(wave3.getNumRams(), context, maxX, maxY);
                spawnFastEnemies(wave3.getNumSpiders(), context, maxX, maxY);
                spawnWave3 = false;
            }

            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = (Enemy) enemies.get(i);
                enemy.update();

                if (enemy.dead) {
                    enemies.remove(enemy);
                    addCredits(5);
                }
            }

            for (Tower tower: towers) {
                tower.update(enemies);
            }

            if(enemies.size() == 0){
                waveStarted = false;
                waveNumber++;
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

            //Drawing towers
            for (int i = 0; i < towers.size(); i++) {
                Tower tower = towers.get(i);

//                ///////test range
//                Paint newpaint = new Paint();
//                newpaint.setColor(Color.RED);
//                canvas.drawRect(tower.getRangeDetector(),newpaint);
//                //////

                canvas.drawBitmap(
                        tower.getBitmap(),
                        tower.getX()-(tower.getBitmap().getWidth()/2),
                        tower.getY()-(tower.getBitmap().getHeight()/2),
                        paint
                );
            }

            //shop button
            paint1.setColor(Color.RED);
            paint1.setTextSize(50);

            //upgrade button
            canvas.drawRect(upgradeButton, paint);
            canvas.drawText("Upgrade", upgradeButton.left + 20, upgradeButton.centerY() + 20, paint1);

            //canvas.drawRect(20, 1000, 250, 900, paint);
            canvas.drawRect(shopButton, paint);
            canvas.drawText("Shop", shopButton.left + 20, shopButton.centerY() + 20, paint1);

            //wave info button
            canvas.drawRect(waveInfoButton, paint);
            canvas.drawText("Wave " + waveNumber + " Info", waveInfoButton.left + 20, waveInfoButton.centerY() + 20, paint1);

            //start wave button
            canvas.drawRect(startWaveButton, paint);
            canvas.drawText("Start Wave", startWaveButton.left + 20, startWaveButton.centerY() + 20, paint1);

            //pause button
            canvas.drawRect(pauseButton, paint);
            canvas.drawText("Pause", pauseButton.left + 20, pauseButton.centerY() + 20, paint1);

            //drawing shop box
            if (shopOpen) {
                canvas.drawBitmap(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.shop_menu),
                        300,
                        300,
                        paint);
            }


            //Drawing the enemies
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = (Enemy) enemies.get(i);

//                ///////test hitbox
//                Paint newpaint2 = new Paint();
//                newpaint2.setColor(Color.BLUE);
//                canvas.drawRect(enemy.getCollisionDetector(),newpaint2);
//                //////

                if (!enemy.dead) {
                    canvas.drawBitmap(enemy.getBitmap(), enemy.getX(), enemy.getY(), paint
                    );
                }
            }


            //Drawing health text
            paint.setTextSize(50);
            canvas.drawText(getBaseHealth(), maxX - 500, maxY - 200, paint);
            paint.setTextSize(100);
            canvas.drawText(getBaseHealthText(), maxX - 500, maxY - 100, paint);
            paint.setTextSize(50);
            shopButton = new Rect(20, maxY - 200, 250, maxY - 100);
            canvas.drawText(toStringCredit(), maxX - 700, maxY - 100, paint);

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
                canvas.drawText("Main Menu", maxX >> 1 - 580 + 65, maxY >> 1 + 300, paint);


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

            //temporary grid points for visualization
            for (int x = 1; x <= 10; x++) {
                for (int y = 1; y <= 5; y++) {
                    canvas.drawCircle(x * gridX, y * gridY, 4, paint);

//                    paint.setTextSize(15);
//                    canvas.drawText(
//                            "(" + (x*gridX) + " , " + (y*gridY) + ")",
//                            x*gridX,
//                            y*gridY,
//                            paint
//                            );

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
        mServ.pauseMusic();
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

    //prints toast when is not enough points in bank to buy a tower
    public void ToastShop() {
        CharSequence text = "Not enough credits!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    //detects touches
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (shopButton.contains((int) event.getX(), (int) event.getY()) && !upgrading) {   //if shop button selected
            if (shopOpen) {
                shopOpen = false;
            } else if (!shopOpen && waveStarted) {
                shopOpen = false;
            } else {
                shopOpen = true;
            }
        }


        //tower press
        if (!shopOpen && !towerSpawned && !upgrading) {
            int x = ((int) event.getX() / gridX) * gridX;//snapping to grid
            int y = ((int) event.getY() / gridY) * gridY;

            for (int i = 0; i < towers.size(); i++) {
                if (towers.get(i).getX() == x && towers.get(i).getY() == y) {


                    for (Tower tower : towers) {
                        if (tower.getX() == x && tower.getY() == y) {
                            CharSequence text = "This is a tower";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                }
                }

            }
            //if player just bought a tower and is placing it
            if (towerSpawned && !shopOpen && !upgrading) {
                final int x = ((int) event.getX() / gridX) * gridX;//snapping to grid
                final int y = ((int) event.getY() / gridY) * gridY;


                //checking for invalid tower placement
                for (Tower tower : towers) {
                    if (tower.getX() == x && tower.getY() == y) {
                        CharSequence text = "There's already a tower here. Get your own spot!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        invalidTower = true;
                        towerSpawned = true;
                        break;
                    } else if (tower.getX() != x && tower.getY() != y) {

                        invalidTower = false;
                    }
                }
                if ((y < 70 && y < 80 && x < 0 && x >= 800) && (x < 790 && x >= 800 && y > 70 && y <= 800) && (x > 790 && y > 790 && y <= 800)) {//checking if tower placed on path
                    CharSequence text = "This is the enemy's path. Don't be rude.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    invalidTower = true;
                }


                if (!invalidTower&&getCredits()>25) {
                    switch (selectedShopQuadrant) {
                        case 1: //shop quadrant 1

                            android.support.v7.app.AlertDialog.Builder shopPopUp = new android.support.v7.app.AlertDialog.Builder(this.getContext());
                            shopPopUp.setMessage("Are you sure you want to place your tower here?"); //shop menu dialogue
                            shopPopUp.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            addCredits(-25);
                                            towers.add(new GunTower(context, x, y));
                                        }
                                    });
                            shopPopUp.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    towerSpawned = false;
                                }
                            });
                            android.support.v7.app.AlertDialog helpDialog = shopPopUp.create();
                            helpDialog.show();


                            towerSpawned = false;//player is done selecting tower location
                            break;
                        case 2: //shop quadrant 2

                            android.support.v7.app.AlertDialog.Builder shopPopUp1 = new android.support.v7.app.AlertDialog.Builder(this.getContext());
                            shopPopUp1.setMessage("Are you sure you want to place your tower here?"); //shop menu dialogue
                            shopPopUp1.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            addCredits(-30);
                                            towers.add(new SniperTower(context, x, y));
                                        }
                                    });
                            shopPopUp1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    towerSpawned = false;
                                }
                            });
                            android.support.v7.app.AlertDialog helpDialog1 = shopPopUp1.create();
                            helpDialog1.show();
                            towerSpawned = false;//player is done selecting tower location
                            break;
                        case 3: //shop quadrant 3

                            android.support.v7.app.AlertDialog.Builder shopPopUp2 = new android.support.v7.app.AlertDialog.Builder(this.getContext());
                            shopPopUp2.setMessage("Are you sure you want to place your tower here?"); //shop menu dialogue
                            shopPopUp2.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            addCredits(-40);
                                            towers.add(new FreezeTower(context, x, y));
                                        }
                                    });
                            shopPopUp2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    towerSpawned = false;
                                }
                            });
                            android.support.v7.app.AlertDialog helpDialog2 = shopPopUp2.create();
                            helpDialog2.show();
                            towerSpawned = false;//player is done selecting tower location
                            break;
                        case 4: //shop quadrant 4

                            android.support.v7.app.AlertDialog.Builder shopPopUp3 = new android.support.v7.app.AlertDialog.Builder(this.getContext());
                            shopPopUp3.setMessage("Are you sure you want to place your tower here?"); //shop menu dialogue
                            shopPopUp3.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            addCredits(-50);
                                            towers.add(new RocketTower(context, x, y));
                                        }
                                    });
                            shopPopUp3.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    towerSpawned = false;
                                }
                            });
                            android.support.v7.app.AlertDialog helpDialog3 = shopPopUp3.create();
                            helpDialog3.show();
                            towerSpawned = false;//player is done selecting tower location
                            break;
                    }
                }
                else
                    ToastShop();
            }

            //Shop Menu is up
            if (shopOpen) {
                //different tower selections based on x,y coordinates
                if (event.getX() > 299 && event.getX() < 600 && event.getY() > 299 && event.getY() < 600) {   //upper left quadrant of shop
                    selectedShopQuadrant = 1;

                    shopOpen = false;
                    towerSpawned = true;
                }
                if (event.getX() > 600 && event.getX() < 900 && event.getY() > 299 && event.getY() < 600) {   //upper right quadrant of shop
                    selectedShopQuadrant = 2;

                    shopOpen = false;
                    towerSpawned = true;
                }
                if (event.getX() > 299 && event.getX() < 600 && event.getY() > 600 && event.getY() < 900) {   //lower left quadrant of shop
                    selectedShopQuadrant = 3;

                    shopOpen = false;
                    towerSpawned = true;
                }
                if (event.getX() > 600 && event.getX() < 900 && event.getY() > 600 && event.getY() < 900) {   //lower right quadrant of shop
                    selectedShopQuadrant = 4;

                    shopOpen = false;
                    towerSpawned = true;
                }


            }

            if(upgrading) {
                final int x = ((int) event.getX() / gridX) * gridX;//snapping to grid
                final int y = ((int) event.getY() / gridY) * gridY;

                for (final Tower tower : towers) {
                    if (tower.getX() == x && tower.getY() == y) {

                        android.support.v7.app.AlertDialog.Builder shopPopUp = new android.support.v7.app.AlertDialog.Builder(this.getContext());
                        shopPopUp.setMessage("Are you sure you want to upgrade this tower one level?"); //shop menu dialogue
                        shopPopUp.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        addCredits(-10);//deducting money
                                        tower.setLevel(tower.getLevel() + 1); //upgrading
                                        upgrading = false;

                                        CharSequence text = "Upgrading tower to level " + tower.getLevel();
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                    }
                                });
                        shopPopUp.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                CharSequence text = "Upgrading canceled. Press Upgrade to try again";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();

                                upgrading = false;
                            }
                        });
                        android.support.v7.app.AlertDialog helpDialog = shopPopUp.create();
                        helpDialog.show();

                        break;
                    }
                }
            }



            //on clicking wave info button
            if (waveInfoButton.contains((int) event.getX(), (int) event.getY())) {
                if(waveNumber == 1){
                    GameActivity.pressWaveNumButton(this, wave1.getNumRams(), wave1.getNumTurkeys(), wave1.getNumSpiders());
                }
                if(waveNumber == 2){
                    GameActivity.pressWaveNumButton(this, wave2.getNumRams(), wave2.getNumTurkeys(), wave2.getNumSpiders());
                }
                if(waveNumber == 3){
                    GameActivity.pressWaveNumButton(this, wave3.getNumRams(), wave3.getNumTurkeys(), wave3.getNumSpiders());
                }
            }

            //on clicking upgrade button
            if(upgradeButton.contains((int) event.getX(), (int) event.getY()) && towers.size()>0){
                if(!upgrading&&getCredits()>10){
                    upgrading = true;

                    CharSequence text = "Press a tower to upgrade it";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else{
                    upgrading = false;
                    ToastShop();
                }
            }
            if(upgradeButton.contains((int) event.getX(), (int) event.getY()) && towers.size()==0){
                CharSequence text = "You don't have any towers to upgrade! Go to the shop";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        if (startWaveButton.contains((int) event.getX(), (int) event.getY())) {
            waveStarted = true;

            if(waveNumber == 1){
            spawnWave1 = true;
            }

            else if(waveNumber == 2){
                spawnWave2 = true;
            }

            else if(waveNumber == 2){
                spawnWave2 = true;
            }

            else if(waveNumber == 3){
                spawnWave3 = true;
            }
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