package edu.vcu.team6.uniclash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.vcu.team6.uniclash.R;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {
    private Paint paint;
    private Paint paint1;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Context context;
    public int credit = 100;
    private Music mServ;
    Wave currentWave;
    Wave wave[] = new Wave[20];
    public boolean[] spawnWave = new boolean[20];

    //boolean variable to track if the game is playing or not
    volatile boolean playing;
    private boolean win = false;
    private boolean canWriteToSave = true;

    //the game thread
    private Thread gameThread = null;

    private ArrayList<Enemy> enemies = new ArrayList<>();

    private Base base;

    private boolean gameOver = false;

    //start wave = 0, end wave = 19;
    private int waveNumber = 0;
    private boolean waveStarted = false;

    private int maxX;
    private int maxY;

    //tower variables
    private ArrayList<Tower> towers = new ArrayList<>();
    private GridTile[][] gridCoordinates = new GridTile[9][16]; //divides screen into rect
    private ArrayList<GridTile> path = new ArrayList<>();


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
    private Rect mainMenuBack;
    private Rect mainMenuButton;
    private Rect restartBack;
    private Rect restartButton;

    private int spawnCounter = 0;
    private int enemyType = 1;          //0-none(wait), 1-tank, 2-default, 3-fast
    private int fCounter = 4;
    private int dCounter = 3;
    private int tCounter = 1;

    private boolean[] level = new boolean[3];
    private int currentLevel;
    private int unlockedLevel;

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.context = context;
        maxX = screenX;
        maxY = screenY;

        loadLevel();

        //initialize waves array
        java.util.Arrays.fill(spawnWave, false);
        spawnWave[waveNumber] = true;
        initializeWaves();
        currentWave = wave[waveNumber];




        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        paint1 = new Paint();

        //todo: move buttons to their own constructor
        shopButton = new Rect(20, maxY - 120, 250, maxY - 20); //initialized as a new rect below as well
        upgradeButton = new Rect(maxX/4-150, maxY-120, maxX/4 +150,maxY-20);
        startWaveButton = new Rect(maxX / 2 - 150, maxY - 120, maxX / 2 + 150, maxY - 20);
        pauseButton = new Rect(maxX - 250, 20, maxX - 20, 120);
        waveInfoButton = new Rect(maxX / 2 - 165, 20, maxX / 2 + 235, 120);
        mainMenuBack = new Rect(maxX / 2 - 580, maxY / 2 + 180, maxX / 2 - 80,maxY / 2 + 380);
        mainMenuButton = new Rect(maxX / 2 - 570, maxY / 2 + 190, maxX / 2 - 90, maxY / 2 + 370);
        restartBack = new Rect(maxX / 2 + 120, maxY / 2 + 180, maxX / 2 + 620, maxY / 2 + 380);
        restartButton = new Rect(maxX / 2 + 130, maxY / 2 + 190, maxX / 2 + 610, maxY / 2 + 370);


        //initializing grid for map
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 16; x++) {
                gridCoordinates[y][x] = new GridTile(context, maxX,maxY,x,y);
            }
        }

        if(level[0]){
            setPath1();
        }
        if(level[1]) {
            setPath2();
        }
        if(level[2]){
            setPath3();
        }
        spawnBase(context);
    }

    public void loadLevel(){
        String levelString;
        int level;
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    context.openFileInput("level")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString);
            }
            levelString = stringBuffer.toString();
            level = Integer.parseInt(levelString);
            currentLevel = level;
            level--;
            this.level[level] = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeWaves(){

//        wave: 0   f: 4   d: 3   t: 1
//        wave: 1   f: 5   d: 3   t: 1
//        wave: 2   f: 5   d: 3   t: 1
//        wave: 3   f: 5   d: 3   t: 1
//        wave: 4   f: 5   d: 3   t: 2
//        wave: 5   f: 6   d: 4   t: 2
//        wave: 6   f: 6   d: 4   t: 2
//        wave: 7   f: 6   d: 4   t: 2
//        wave: 8   f: 6   d: 4   t: 2
//        wave: 9   f: 7   d: 4   t: 2
//        wave: 10   f: 7   d: 4   t: 2
//        wave: 11   f: 7   d: 5   t: 2
//        wave: 12   f: 7   d: 5   t: 2
//        wave: 13   f: 8   d: 5   t: 2
//        wave: 14   f: 8   d: 5   t: 3
//        wave: 15   f: 8   d: 5   t: 3
//        wave: 16   f: 8   d: 5   t: 3
//        wave: 17   f: 9   d: 6   t: 3
//        wave: 18   f: 9   d: 6   t: 3
//        wave: 19   f: 9   d: 6   t: 3


        wave[0] = new Wave(4, 3, 1);
        wave[1] = new Wave(5, 3, 2);
        wave[2] = new Wave(10, 3, 2);
        wave[3] = new Wave(10, 6, 3);
        wave[4] = new Wave(15, 8, 4);
        wave[5] = new Wave(18, 8, 4);
        wave[6] = new Wave(24, 12, 6);
        wave[7] = new Wave(24, 12, 6);
        wave[8] = new Wave(24, 16, 6);
        wave[9] = new Wave(28, 16, 8);
        wave[10] = new Wave(28, 20, 8);
        wave[11] = new Wave(35, 25, 8);
        wave[12] = new Wave(35, 25, 10);
        wave[13] = new Wave(40, 30, 12);
        wave[14] = new Wave(0, 0, 30);
        wave[15] = new Wave(40, 30, 15);
        wave[16] = new Wave(48, 35, 15);
        wave[17] = new Wave(54, 42, 18);
        wave[18] = new Wave(54, 42, 21);
        wave[19] = new Wave(63, 48, 24);
    }

    //finds what tile the given x,y coordinates are in, returns only Rect
    public Rect findTile(float x, float y){ //mainly used for ontouch
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 16; j++) {
                if(gridCoordinates[i][j].getGridTile().contains(Math.round(x),Math.round(y))){  //looking for which tile was touched
                    return gridCoordinates[i][j].getGridTile();
                }
            }
        }
        return null; //if tile not found
    }
    //actually returns gridtile
    public GridTile findGridTile(float x, float y){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 16; j++) {
                if(gridCoordinates[i][j].getGridTile().contains(Math.round(x),Math.round(y))){  //looking for which tile was touched
                    return gridCoordinates[i][j];
                }
            }
        }
        return null; //if gridTile not found
    }

    public void setPath1() {
        int pathX = 0;
        int pathY = 1;





        while(pathX < 5) {
            gridCoordinates[pathY][pathX].isPath = true;
            gridCoordinates[pathY][pathX].setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.larger_path_tile));
            path.add(gridCoordinates[pathY][pathX]);
            pathX++;
        }
        while (pathY < 5) {
            gridCoordinates[pathY][pathX].isPath = true;
            gridCoordinates[pathY][pathX].setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.larger_path_tile));
            path.add(gridCoordinates[pathY][pathX]);
            pathY++;
        }
        while(pathX < 16) {
            gridCoordinates[pathY][pathX].isPath = true;
            gridCoordinates[pathY][pathX].setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.larger_path_tile));
            path.add(gridCoordinates[pathY][pathX]);
            pathX++;
        }
    }

    public void setPath2() {
        int pathX = 0;
        int pathY = 4;

        while(pathX < 5) {
            gridCoordinates[pathY][pathX].isPath = true;
            gridCoordinates[pathY][pathX].setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.larger_path_tile));
            path.add(gridCoordinates[pathY][pathX]);
            pathX++;
        }
        while (pathY < 5) {
            gridCoordinates[pathY][pathX].isPath = true;
            gridCoordinates[pathY][pathX].setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.larger_path_tile));
            path.add(gridCoordinates[pathY][pathX]);
            pathY++;
        }
        while(pathX < 16) {
            gridCoordinates[pathY][pathX].isPath = true;
            gridCoordinates[pathY][pathX].setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.larger_path_tile));
            path.add(gridCoordinates[pathY][pathX]);
            pathX++;
        }
    }

    public void setPath3() {
        int pathX = 0;
        int pathY = 6;

        while(pathX < 5) {
            gridCoordinates[pathY][pathX].isPath = true;
            gridCoordinates[pathY][pathX].setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.larger_path_tile));
            path.add(gridCoordinates[pathY][pathX]);
            pathX++;
        }
        while (pathY > 5) {
            gridCoordinates[pathY][pathX].isPath = true;
            gridCoordinates[pathY][pathX].setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.larger_path_tile));
            path.add(gridCoordinates[pathY][pathX]);
            pathY--;
        }
        while(pathX < 16) {
            gridCoordinates[pathY][pathX].isPath = true;
            gridCoordinates[pathY][pathX].setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.larger_path_tile));
            path.add(gridCoordinates[pathY][pathX]);
            pathX++;
        }
    }

    public void setGameOver() {
        gameOver = true;

    }

    public void spawnTankEnemies(int numberEnemies, Context context, int screenX, int screenY) {
        for (int i = 0; i < numberEnemies; i++) {
            TankEnemy enemy = new TankEnemy(context, screenX, screenY, base, path, waveNumber);
            //enemy.setX(0 - (enemies.size() * enemy.getBitmap().getWidth()));
            enemies.add(enemy);
        }
    }

    public void spawnDefaultEnemies(int numberEnemies, Context context, int screenX, int screenY) {
        for (int i = 0; i < numberEnemies; i++) {
            DefaultEnemy enemy = new DefaultEnemy(context, screenX, screenY, base, path, waveNumber);
            //enemy.setX(0 - (enemies.size() * enemy.getBitmap().getWidth()));
            enemies.add(enemy);
        }
    }

    public void spawnFastEnemies(int numberEnemies, Context context, int screenX, int screenY) {
        for (int i = 0; i < numberEnemies; i++) {
            FastEnemy enemy = new FastEnemy(context, screenX, screenY, base, path, waveNumber);
            //enemy.setX(0 - (enemies.size() * enemy.getBitmap().getWidth()));
            enemies.add(enemy);
        }
    }

    public void spawnBase(Context context) {
        this.base = new Base(context, path.get(path.size()-1).getXCenter(),
                path.get(path.size()-1).getYCenter(), this);
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

    private void unlockNextLevel() {
        String unlockedLevelString = "";
        //load the unlocked level

        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    context.openFileInput("unlocked_levels")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString);
            }
            unlockedLevelString = stringBuffer.toString();
            if(!unlockedLevelString.isEmpty()){
                unlockedLevel = Integer.parseInt(unlockedLevelString);
                unlockedLevel++;
                unlockedLevelString = "";
                unlockedLevelString += unlockedLevel;
            }
            else{
                unlockedLevelString = "" + (currentLevel + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String filename = "unlocked_levels";
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(unlockedLevelString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int resetFCounter() {
        int fCounterBase = 3 + ((waveNumber+6) / 4);
        return fCounterBase;
    }

    private int resetDCounter() {
        int dCounterBase = 2 + ((waveNumber+6) / 6);
        return dCounterBase;
    }

    private int resetTCounter() {
        int tCounterBase = 1 + ((waveNumber+5) / 10);
        return tCounterBase;
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

    private void spawnEnemies(){
        switch (enemyType) {
            case(0): //wait
                //wavenum   1   2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18  19  20
                //timer     30  28  28  26  26  24  24  22  22  20  20  18  18  16  16  14  14  12  12  10

                if (spawnCounter < (30 - ((waveNumber/2)*2))) {

                    spawnCounter++;
                } else {
                    enemyType = 1;
                    spawnCounter = 0;
                    fCounter = resetFCounter();
                    dCounter = resetDCounter();
                    tCounter = resetTCounter();
                }
                break;
            case(1): //tanks
                if (spawnCounter < 15) {
                    spawnCounter++;
                } else {
                    if (tCounter > 0) {
                        for (Enemy enemy: enemies) {
                            if (enemy.type == 1 && enemy.waiting) {
                                enemy.waiting = false;
                                tCounter--;
                                break;
                            } else if (enemies.get(enemies.size()-1) == enemy){
                                tCounter = 0;
                                enemyType = 2;
                            }
                        }
                        spawnCounter = 0;
                    } else {
                        enemyType = 2;
                        spawnCounter = 0;
                    }
                }
                break;
            case(2): //defaults
                if (spawnCounter < 10) {
                    spawnCounter++;
                } else {
                    if (dCounter > 0) {
                        for (Enemy enemy: enemies) {
                            if (enemy.type == 2 && enemy.waiting) {
                                enemy.waiting = false;
                                dCounter--;
                                break;
                            } else if (enemies.get(enemies.size()-1) == enemy) {
                                dCounter = 0;
                                enemyType = 3;
                            }
                        }
                        spawnCounter = 0;
                    } else {
                        enemyType = 3;
                        spawnCounter = 0;
                    }
                }
                break;
            case(3): //fast bois
                if (spawnCounter < 5) {
                    spawnCounter++;
                } else {
                    if (fCounter > 0) {
                        for (Enemy enemy: enemies) {
                            if (enemy.type == 3 && enemy.waiting) {
                                enemy.waiting = false;
                                fCounter--;
                                break;
                            } else if (enemies.get(enemies.size()-1) == enemy) {
                                fCounter = 0;
                                enemyType = 0;
                            }
                        }
                        spawnCounter = 0;
                    } else {
                        enemyType = 0;
                        spawnCounter = 0;
                    }
                }
                break;
        }

    }

    private void update() {
        if (!gameOver && waveStarted && waveNumber < 20) {
            if(spawnWave[waveNumber]) {
                spawnFastEnemies(currentWave.getNumSpiders(), context, maxX, maxY);
                spawnDefaultEnemies(currentWave.getNumRams(), context, maxX, maxY);
                spawnTankEnemies(currentWave.getNumTurkeys(), context, maxX, maxY);
                spawnWave[waveNumber] = false;
                if(waveNumber + 1 <20){
                    currentWave = wave[waveNumber + 1];
                }
            }
            spawnEnemies();

            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = (Enemy) enemies.get(i);

                if (!enemy.dead && !enemy.waiting) {
                    enemy.update();
                }

                if (enemy.dead) {
                    if(enemy instanceof TankEnemy){
                        addCredits(5);
                    }
                    else if(enemy instanceof DefaultEnemy){
                        addCredits(3);
                    }
                    else{addCredits(1);}
                    enemies.remove(enemy);
                }
            }

            for (Tower tower: towers) {
                tower.update(enemies);
            }

            if(enemies.size() == 0){
                waveStarted = false;
                waveNumber++;
                if(waveNumber == 20){win = true;}
            }

        }

        if(win && canWriteToSave){
            gameOver = true;
            Log.i("GAME VIEW: ","Attempting to unlock next level");
            unlockNextLevel();
            canWriteToSave = false;
        }
    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.WHITE);

            //draw tiles
            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 16; x++) {
                    canvas.drawBitmap(
                            gridCoordinates[y][x].getBitmap(),
                            gridCoordinates[y][x].x,
                            gridCoordinates[y][x].y,
                            paint
                    );
                }
            }

            //Drawing the base
            canvas.drawBitmap(
                    base.getBitmap(),
                    base.getX() - (base.getBitmap().getWidth() / 2),
                    base.getY() - (base.getBitmap().getHeight() / 2),
                    paint);

            //DRAW BUTTONS

            //Button text paint
            paint1.setColor(Color.RED);
            paint1.setTextSize(50);

            //start wave button
            if(!waveStarted && !win) {
                //start wave button
                canvas.drawRect(startWaveButton, paint);
                canvas.drawText("Start Wave", startWaveButton.left + 20, startWaveButton.centerY() + 20, paint1);
            }

            //upgrade button
            if(!waveStarted) {
                canvas.drawRect(upgradeButton, paint);
                canvas.drawText("Upgrade", upgradeButton.left + 20, upgradeButton.centerY() + 20, paint1);
            }

            //shop button
            if(!waveStarted) {
                canvas.drawRect(shopButton, paint);
                canvas.drawText("Shop", shopButton.left + 20, shopButton.centerY() + 20, paint1);
            }

            //wave info button
            canvas.drawRect(waveInfoButton, paint);
            canvas.drawText("Wave " + (waveNumber+1) + "/20 Info", waveInfoButton.left + 20, waveInfoButton.centerY() + 20, paint1);

            //pause button
            canvas.drawRect(pauseButton, paint);
            canvas.drawText("Pause", pauseButton.left + 20, pauseButton.centerY() + 20, paint1);

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

            //Drawing the enemies
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = (Enemy) enemies.get(i);

//                ///////test hitbox
//                Paint newpaint2 = new Paint();
//                newpaint2.setColor(Color.BLUE);
//                canvas.drawRect(enemy.getCollisionDetector(),newpaint2);
//                //////

                if (!enemy.dead && !enemy.waiting) {
                    canvas.drawBitmap(
                            enemy.getBitmap(),
                            enemy.getX() - (enemy.getBitmap().getWidth()/2),
                            enemy.getY() - (enemy.getBitmap().getHeight()/2),
                            paint
                    );
                }
            }


            //Drawing health text
//            paint.setTextSize(50);
//            canvas.drawText(getBaseHealth(), maxX - 500, maxY - 200, paint);
            paint.setTextSize(100);
            canvas.drawText(getBaseHealthText(), maxX - 500, maxY - 100, paint);
            paint.setTextSize(50);
            shopButton = new Rect(20, maxY - 120, 250, maxY - 20); //second time Button is initialized
            canvas.drawText(toStringCredit(), maxX - 700, maxY - 100, paint);

            if (gameOver) {
                if(!win) {
                    paint.setTextSize(150);
                    canvas.drawText("GAME OVER", maxX / 2 - 420, maxY / 2, paint);
                }

                // main menu button
                canvas.drawRect(mainMenuBack, paint);
                paint.setColor(Color.YELLOW);
                canvas.drawRect(mainMenuButton, paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(75);
                canvas.drawText("Main Menu", maxX / 2 - 580 + 65, maxY / 2 + 300, paint);


                // restart button
                canvas.drawRect(restartBack, paint);
                paint.setColor(Color.GREEN);
                canvas.drawRect(restartButton, paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(75);
                canvas.drawText("Restart", maxX / 2 + 245, maxY / 2 + 300, paint);
            }

//            //draw level name
//            if(level[0]){
//                paint.setColor(Color.BLACK);
//                paint.setTextSize(50);
//                canvas.drawText("Level 1", maxX / 2 - 950, maxY / 4, paint);
//            }
//
//            else if(level[1]){
//                paint.setColor(Color.BLACK);
//                paint.setTextSize(50);
//                canvas.drawText("Level 2", maxX / 2 - 950, maxY / 4, paint);
//            }
//
//            else{
//                paint.setColor(Color.BLACK);
//                paint.setTextSize(50);
//                canvas.drawText("Level 3", maxX / 2 - 950, maxY / 4, paint);
//            }

            if(win){
                paint.setColor(Color.YELLOW);
                paint.setTextSize(200);
                canvas.drawText("CONGRATULATIONS!", maxX / 2 - 950, maxY / 2 - 100, paint);
                paint.setTextSize(150);
                canvas.drawText("You've Graduated!", maxX / 2 - 575, maxY / 2 + 100, paint);
                paint.setColor(Color.BLACK);
            }

            // drawing shop box
            if (shopOpen) {
                Bitmap shopBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shop_menu);
                Bitmap shopBitmapScaled = Bitmap.createScaledBitmap(shopBitmap, maxX/16*6, maxY/9*6, false);


                canvas.drawBitmap(
                        shopBitmapScaled,
                        gridCoordinates[0][5].getLeft(),
                        gridCoordinates[1][0].getYCenter(),
                        paint);
            }

            //temporary grid points for visualization - corners are black, center is gray
//            for (int y = 0; y < 9; y++) {
//                for (int x = 0; x < 16; x++) {
//                    Rect tile = gridCoordinates[y][x].getGridTile();
//
//                    canvas.drawCircle(tile.left, tile.top, 4, paint); //drawing pt at upper left corner of tile
//                    canvas.drawCircle(tile.left, tile.bottom, 4, paint); //drawing pt at bottom left corner of tile
//                    canvas.drawCircle(tile.right, tile.top, 4, paint); //drawing pt at upper right corner of tile
//                    canvas.drawCircle(tile.right, tile.bottom, 4, paint); //drawing pt at bottom right corner of tile
//                    paint.setColor(Color.LTGRAY);
//                    canvas.drawCircle(tile.centerX(), tile.centerY(), 4, paint); //drawing pt at center of tile
//                    paint.setColor(Color.BLACK);
//
//
////                    paint.setTextSize(15);
////                    canvas.drawText(
////                            "(" + (x*gridX) + " , " + (y*gridY) + ")",
////                            x*gridX,
////                            y*gridY,
////                            paint
////                            );
//
//                }
//            }


            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(9);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        //mServ.pauseMusic();
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

        if (shopButton.contains((int) event.getX(), (int) event.getY()) && !upgrading && !towerSpawned && !waveStarted) {   //if shop button selected
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
            //snapping to grid, x,y is center of tile
            final int x = findTile(event.getX(),event.getY()).centerX();
            final int y = findTile(event.getX(),event.getY()).centerY();

            for (int i = 0; i < towers.size(); i++) {
                if (towers.get(i).getX() == x && towers.get(i).getY() == y) {


                    for (Tower tower : towers) {
                        if (tower.getX() == x && tower.getY() == y) {
                            CharSequence text = tower.toString();
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
                //snapping to grid, x,y is center of tile
                final int x = findTile(event.getX(),event.getY()).centerX();
                final int y = findTile(event.getX(),event.getY()).centerY();
                final GridTile tilePressed = findGridTile(event.getX(),event.getY());


                //checking for invalid tower placement
                //checking for previously placed tower
                for(Tower tower : towers) {
                    if (tower.getX()==x && tower.getY()==y) {
                        CharSequence text = "There's already a tower here. Get your own spot!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        invalidTower = true;
                        towerSpawned = true;
                    }
                    //checking if tower placed on path
                    else if (tilePressed.isPath) {
                        CharSequence text = "This is the enemy's path. Don't be rude.";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        invalidTower = true;
                        towerSpawned = true;
                    } else {
                        invalidTower = false;
                    }
                }


                if (!invalidTower){
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
            }

            //Shop Menu is up
            if (shopOpen) {
                //shop drawn starting from shopLeft,shopTop
                int shopLeft = gridCoordinates[0][5].getXCenter();
                int shopTop = (int)(gridCoordinates[1][0].getYCenter());
                int shopRight = gridCoordinates[0][10].getXCenter();
                int shopBottom = (int) (gridCoordinates[5][0].getBottom());
                int shopXMiddle = gridCoordinates[0][7].getXCenter();

                //different tower selections based on x,y coordinates
                //Gun tower
                if (event.getX() > shopLeft && event.getX()<shopXMiddle && event.getY()>shopTop && event.getY()<gridCoordinates[3][0].getYCenter()) {   //upper left quadrant of shop
                    if(getCredits()<25){
                        ToastShop();
                        shopOpen=true;
                    }else {
                        selectedShopQuadrant = 1;

                        shopOpen = false;
                        towerSpawned = true;

                        CharSequence text = "Tap map to place Gun Tower";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
                //Sniper tower
                if (event.getX() > shopXMiddle && event.getX()<shopRight && event.getY() > shopTop && event.getY()<gridCoordinates[3][0].getYCenter()) {   //upper right quadrant of shop
                    if(getCredits()<30){
                        ToastShop();
                        shopOpen=true;
                    }else {
                        selectedShopQuadrant = 2;

                        shopOpen = false;
                        towerSpawned = true;

                        CharSequence text = "Tap map to place Sniper Tower";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
                //Freeze tower
                if (event.getX() > shopLeft && event.getX()< shopXMiddle && event.getY() > gridCoordinates[3][0].getYCenter() && event.getY()< gridCoordinates[5][0].getTop()) {   //lower left quadrant of shop
                    if(getCredits()<40){
                        ToastShop();
                        shopOpen=true;
                    }else {
                        selectedShopQuadrant = 3;

                        shopOpen = false;
                        towerSpawned = true;

                        CharSequence text = "Tap map to place Freeze Tower";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
                //Rocket tower
                if (event.getX() > shopXMiddle && event.getX()<shopRight  && event.getY() > gridCoordinates[3][0].getYCenter() && event.getY()< gridCoordinates[5][0].getTop()) {   //lower right quadrant of shop
                    if(getCredits()<50){
                        ToastShop();
                        shopOpen=true;
                    }else {
                        selectedShopQuadrant = 4;

                        shopOpen = false;
                        towerSpawned = true;

                        CharSequence text = "Tap map to place Rocket Tower";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
                //student loans pack
                if (event.getX() > shopLeft && event.getX()< shopRight && event.getY()> gridCoordinates[5][0].getTop() && event.getY()<shopBottom){  //bottom bar of shop
                    if(getCredits()>99){
                        android.support.v7.app.AlertDialog.Builder shopPopUp = new android.support.v7.app.AlertDialog.Builder(this.getContext());
                        shopPopUp.setMessage("Boost your grade 50 points?"); //shop menu dialogue
                        shopPopUp.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        credit -= 100;
                                        base.heal(50);
                                        shopOpen = true;

                                        CharSequence text = "Healing base 50 points.";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                    }
                                });
                        shopPopUp.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                towerSpawned = false;
                            }
                        });
                        android.support.v7.app.AlertDialog helpDialog = shopPopUp.create();
                        helpDialog.show();

                    }else{
                        ToastShop();
                        shopOpen = false;
                    }
                }


            }

            if(upgrading) {
                //snapping to grid, x,y is center of tile
                final int x = findTile(event.getX(),event.getY()).centerX();
                final int y = findTile(event.getX(),event.getY()).centerY();

                for (final Tower tower : towers) {
                    if (tower.getX() == x && tower.getY() == y) {

                        android.support.v7.app.AlertDialog.Builder shopPopUp = new android.support.v7.app.AlertDialog.Builder(this.getContext());
                        shopPopUp.setMessage("Are you sure you want to upgrade this tower one level?"); //shop menu dialogue
                        shopPopUp.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        addCredits(-10);//deducting money
                                        tower.increaseLevel(); //upgrading
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

            if(!upgrading && !waveStarted && !shopOpen && !towerSpawned) {
                final int x = findTile(event.getX(), event.getY()).centerX();
                final int y = findTile(event.getX(), event.getY()).centerY();
                for (final Tower tower : towers) {
                    if (tower.getX() == x && tower.getY() == y) {

                        android.support.v7.app.AlertDialog.Builder shopPopUp = new android.support.v7.app.AlertDialog.Builder(this.getContext());
                        shopPopUp.setMessage("Do you want to destroy your tower?"); //shop menu dialogue
                        shopPopUp.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        addCredits(10);//Adding partial credit
                                        towers.remove(tower); //destroys tower
                                    }
                                });
                        shopPopUp.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

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
                GameActivity.pressWaveNumButton(this, currentWave.getNumRams(), currentWave.getNumTurkeys(), currentWave.getNumSpiders());
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

        if (!waveStarted && (startWaveButton.contains((int) event.getX(), (int) event.getY()))) {
            waveStarted = true;
            spawnWave[waveNumber] = true;
        }

        //on clicking pause
        if (pauseButton.contains((int) event.getX(), (int) event.getY())) {
            if (playing) {
                pause();
                //crashes when switching activity
                android.support.v7.app.AlertDialog.Builder shopPopUp = new android.support.v7.app.AlertDialog.Builder(this.getContext());
                shopPopUp.setMessage("Do you want to go to settings menu?"); //shop menu dialogue
                shopPopUp.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                 Intent pauseMenu= new Intent(getContext(), pauseActivity.class);
                                getContext().startActivity(pauseMenu);
                                pauseMenu.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            }
                        });
                shopPopUp.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        resume();
                    }
                });
                android.support.v7.app.AlertDialog helpDialog = shopPopUp.create();
                helpDialog.show();
            }
            else
                resume();
        }

        if (gameOver) {
            if (mainMenuButton.contains((int) event.getX(), (int) event.getY())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent startMain = new Intent(context, MainMenu.class);
                                context.startActivity(startMain);
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


            if (restartBack.contains((int) event.getX(), (int) event.getY())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent startMain = new Intent(context, GameActivity.class);
                                context.startActivity(startMain);
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

        return false; //onTouch always returns false
    }
}