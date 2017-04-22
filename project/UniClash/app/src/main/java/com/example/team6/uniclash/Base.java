package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Base{
    public Base(Context context, int pathCenterX, int pathCenterY, GameView gameview){
        bitmap = (BitmapFactory.decodeResource(context.getResources(), R.drawable.base));
        this.gameview = gameview;
        this.x = pathCenterX;
        this.y = pathCenterY;
    }

    public Bitmap bitmap;
    public int x, y;
    public GameView gameview;
    private int health = 100;

    public int getHealth() {
        return health;
    }

    public int getX(){return x;}

    public int getY(){return y;}

    public Bitmap getBitmap(){return bitmap;}

    public void setHealth(int health) {
        this.health = health;
    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
        if(getHealth() <= 0){
            x = -1000;
            y = -1000;
            gameview.setGameOver();
        }

    }

    public void heal(int amount) {
        if(this.getHealth()+amount>100){
            this.setHealth(100);
        }else {
            this.setHealth(
                    this.getHealth() + amount);
        }
    }
}
