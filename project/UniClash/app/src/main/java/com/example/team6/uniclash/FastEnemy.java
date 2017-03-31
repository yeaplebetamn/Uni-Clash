package com.example.team6.uniclash;

import android.content.Context;
import android.graphics.BitmapFactory;

public class FastEnemy extends Enemy {
    public FastEnemy(Context context, int screenX, int screenY) {
        setHealth(50);
        setSpeed(3);
        setAttack(6);
        setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.richmond_spiders));
        setMaxX(screenX);
        setMaxY(screenY);
        setX(75);
        setY(150);
    }
}
