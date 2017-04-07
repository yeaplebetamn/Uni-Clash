package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Base{
    public Base(Context context, int screenX, int screenY){
        bitmap = (BitmapFactory.decodeResource(context.getResources(), R.drawable.base)

        );
    }
    public Bitmap bitmap;
    public int x = 2200, y = 800;

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
        }

    }

    public void heal(int amount) {
        this.setHealth(this.getHealth() + amount);
    }
}
